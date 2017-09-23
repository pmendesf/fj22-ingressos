package br.com.caelum.ingresso.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

public class SessaoTest {

	@Test
	public void oPrecoDaSessaoDeveSerASomaDoPrecoDaSalaMaisOPrecoDoFilme() {
		Sala sala = new Sala(" ", new BigDecimal("12.0"));
		Filme filme = new Filme("It: a coisa", Duration.ofMinutes(120), "Terror", new BigDecimal("12.0"));

		BigDecimal somaDosPrecosDaSalaEFilme = sala.getPreco().add(filme.getPreco());

		Sessao sessao = new Sessao(LocalTime.parse("10:00:00"), filme, sala);

		Assert.assertEquals(somaDosPrecosDaSalaEFilme, sessao.getPreco());
	}
}
