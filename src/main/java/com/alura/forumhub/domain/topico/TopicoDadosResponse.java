package com.alura.forumhub.domain.topico;

import java.time.LocalDateTime;

public class TopicoDadosResponse {
    private Long id;
    private String titulo;
    private String mensagem;
    private String nomeCurso;
    private LocalDateTime dataCriacao;

    public TopicoDadosResponse(Long id, String titulo, String mensagem, String nomeCurso, LocalDateTime dataCriacao) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.nomeCurso = nomeCurso;
        this.dataCriacao = dataCriacao;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getMensagem() { return mensagem; }
    public String getNomeCurso() { return nomeCurso; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
}
