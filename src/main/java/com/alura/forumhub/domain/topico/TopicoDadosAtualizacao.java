package com.alura.forumhub.domain.topico;

public record TopicoDadosAtualizacao(String titulo,
                                     String mensagem,
                                     TopicoStatus status) {
}
