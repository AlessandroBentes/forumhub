package com.alura.forumhub.domain.usuario;

public record UsuarioDadosDetalhamento(Long id, String nome, String email, String senha, Boolean ativo) {

    public UsuarioDadosDetalhamento(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getAtivo());
    }

}
