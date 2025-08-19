package com.alura.forumhub.domain.topico;

import com.alura.forumhub.domain.curso.Curso;
import com.alura.forumhub.domain.resposta.RespostaDados;
import com.alura.forumhub.domain.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public record TopicoDetalheDadosResponse(
                                     Long id,
                                     String titulo,
                                     String mensagem,
                                     LocalDateTime dataCriacao,
                                     String usuario,
                                     String curso,
                                     TopicoStatus status

)
{

}