package com.lucas.studies.minhasfinancas.model.repositorty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucas.studies.minhasfinancas.model.entity.Lancamento;
import com.lucas.studies.minhasfinancas.model.enums.StatusLancamento;
import com.lucas.studies.minhasfinancas.model.enums.TipoLancamento;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

	@Autowired
	LancamentoRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveSalvarUmLancamento() {
		Lancamento lancamento = criarLancamento();
		
		lancamento = repository.save(lancamento);
		Assertions.assertThat(lancamento.getId()).isNotNull();
	}

	@Test
	public void deveAtualizarLancamento() {
		Lancamento lancamento = criarEPersistirLancamento();
		lancamento.setAno(2018);
		lancamento.setDescricao("update");
		lancamento.setStatus(StatusLancamento.CANCELADO);
		repository.save(lancamento);
		Lancamento lancamentoAtualizado = entityManager.find(Lancamento.class, lancamento.getId());
		Assertions.assertThat(lancamentoAtualizado.getAno()).isEqualTo(2018);
		Assertions.assertThat(lancamentoAtualizado.getDescricao()).isEqualTo("update");
		Assertions.assertThat(lancamentoAtualizado.getStatus()).isEqualTo(StatusLancamento.CANCELADO);
	}
	
	public static Lancamento criarLancamento() {
		return Lancamento.builder()
									.ano(2019)
									.mes(1)
									.descricao("lancamento qualquer")
									.valor(BigDecimal.valueOf(10))
									.tipo(TipoLancamento.RECEITA)
									.status(StatusLancamento.PENDENTE)
									.dataCadastro(LocalDate.now())
									.build();
	}
	
	@Test
	public void deveDeletarUmLancamento() {
		Lancamento lancamento = criarEPersistirLancamento();
		lancamento = entityManager.find(Lancamento.class, lancamento.getId());
		
		repository.delete(lancamento);
		Lancamento lancamentoInexistente = entityManager.find(Lancamento.class, lancamento.getId());
		Assertions.assertThat(lancamentoInexistente).isNull();
	}
	
	public void deveBuscarUmLancamentoPorId() {
		Lancamento lancamento = criarEPersistirLancamento();
		Optional<Lancamento> lancamentoEncontrado = repository.findById(lancamento.getId());
		Assertions.assertThat(lancamentoEncontrado.isPresent()).isTrue();
	}

	private Lancamento criarEPersistirLancamento() {
		Lancamento lancamento = criarLancamento();
		entityManager.persist(lancamento);
		return lancamento;
	}
	
}
