package com.alura.forumhub.domain.resposta;

import com.alura.forumhub.domain.topico.Topico;
import com.alura.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "respostas")
@Entity(name = "Resposta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private String mensagem;
    private String solucao;
    private LocalDateTime dataCriacao = LocalDateTime.now();

    public long getId() {
        return this.id;
    }


    public String getMensagem() {
        return this.mensagem;
    }


    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
