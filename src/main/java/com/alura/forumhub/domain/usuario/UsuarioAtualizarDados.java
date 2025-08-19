package com.alura.forumhub.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record UsuarioAtualizarDados(
        @NotNull
        Long id,
        String nome,
        String email,
        String senha) {
}
