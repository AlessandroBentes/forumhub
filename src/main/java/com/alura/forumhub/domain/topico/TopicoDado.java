package com.alura.forumhub.domain.topico;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public record TopicoDado(Long id,
                         String titulo,
                         String mensagem,
                         String nomeCurso,
                         Long usuario_id
                         )
{

}

