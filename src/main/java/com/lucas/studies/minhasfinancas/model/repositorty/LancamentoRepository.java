package com.lucas.studies.minhasfinancas.model.repositorty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.studies.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
