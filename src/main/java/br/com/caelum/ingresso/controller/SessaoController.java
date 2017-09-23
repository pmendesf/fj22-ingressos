package br.com.caelum.ingresso.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.form.SessaoForm;
import br.com.caelum.ingresso.validacao.GerenciadorDeSessao;

@Controller
public class SessaoController {

	@Autowired
	private SessaoDao sessaoDao;

	@Autowired
	private SalaDao salaDao;

	@Autowired
	private FilmeDao filmeDao;

	@GetMapping("/admin/sessao")
	public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm form) {

		form.setSalaId(salaId);

		ModelAndView model = new ModelAndView("sessao/sessao");

		model.addObject("sala", salaDao.findOne(salaId));
		model.addObject("filmes", filmeDao.findAll());
		model.addObject("form", form);

		return model;

	}

	@PostMapping(value = "/admin/sessao")
	@Transactional
	public ModelAndView salva(@Valid SessaoForm form, BindingResult result) {
		if (result.hasErrors())
			return form(form.getSalaId(), form);

		ModelAndView modelAndView = new ModelAndView("redirect:/admin/sala/" + form.getSalaId() + "/sessoes");

		Sessao sessao = form.toSessao(salaDao, filmeDao);

		List<Sessao> sessoesDaSala = sessaoDao.buscaSessoesDaSala(sessao.getSala());

		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoesDaSala);

		if (gerenciador.cabe(sessao)) {
			sessaoDao.save(sessao);
			return modelAndView;
		}

		return form(form.getSalaId(), form);

	}

}