package com.alura.forumhub.domain.topico;

import com.alura.forumhub.domain.curso.Curso;
import com.alura.forumhub.domain.resposta.Resposta;
import com.alura.forumhub.domain.resposta.RespostaDados;
import com.alura.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topicos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private TopicoStatus status = TopicoStatus.UNANSWERED;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;
    @OneToMany(mappedBy = "topico")
    private List<Resposta> respostas = new ArrayList<>();

    public Topico (String titulo, String mensagem, Curso curso, Usuario usuario){
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.curso = curso;
        this.usuario = usuario;
    }


    public Long getId() {
        return this.id;
    }

    public String getTitulo() {
        return this.titulo;
    }


    public String getMensagem() {
        return this.mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public TopicoStatus getStatus() {
        return this.status;
    }

    public List<Resposta> getRespostas() {
        return this.respostas;
    }
}
