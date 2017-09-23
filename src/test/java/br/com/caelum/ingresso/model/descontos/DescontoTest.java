package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class DescontoTest {

	@Test
	public void deveConcederDescontoDeTrintaPorcentoParaOClienteDoBanco() {
		Sala sala = new Sala(" ", new BigDecimal("10"));
		Filme filme = new Filme("It: a coisa", Duration.ofMinutes(120), "Terror", new BigDecimal("10"));
		Sessao sessao = new Sessao(LocalTime.parse("10:00:00"), filme, sala);
		Ingresso ingresso = new Ingresso(sessao, new DescontoTrintaPorcentoParaBancos());

		BigDecimal precoEsperado = new BigDecimal("14.0");

		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}

	@Test
	public void deveConcederDescontoDe50PorcentoParaEstudantes() {

		Sala sala = new Sala(" ", new BigDecimal("10.00"));
		Filme filme = new Filme("It: a coisa", Duration.ofMinutes(120), "Terror", new BigDecimal("10.000"));
		Sessao sessao = new Sessao(LocalTime.parse("10:00:00"), filme, sala);
		Ingresso ingresso = new Ingresso(sessao, new DescontoEstudante());

		BigDecimal precoEsperado = new BigDecimal("10.00");

		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}

	@Test
	public void naoDeveConcederDescontoAlgum() {
		Sala sala = new Sala(" ", new BigDecimal("10.00"));
		Filme filme = new Filme("It: a coisa", Duration.ofMinutes(120), "Terror", new BigDecimal("10.00"));
		Sessao sessao = new Sessao(LocalTime.parse("10:00:00"), filme, sala);
		Ingresso ingresso = new Ingresso(sessao, new SemDesconto());

		BigDecimal precoEsperado = new BigDecimal("20.00");

		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
}
