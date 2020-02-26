package com.lucas.studies.minhasfinancas.service;

import java.util.Optional;

import com.lucas.studies.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {
	Usuario autenticar(String email, String senha);	
	Usuario salvarUsuario(Usuario usuario);	
	void validarEmail(String email);	
	Optional<Usuario> obterPorId(long id);	
}
