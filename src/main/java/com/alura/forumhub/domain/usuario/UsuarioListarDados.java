package com.alura.forumhub.domain.usuario;

public record UsuarioListarDados(Long id, String nome, String email) {

    public UsuarioListarDados(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }


}
