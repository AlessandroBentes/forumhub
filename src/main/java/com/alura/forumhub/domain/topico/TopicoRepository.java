package com.alura.forumhub.domain.topico;

import com.alura.forumhub.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long>
{
    Boolean existsByTituloAndMensagem(String titulo, String mensagem);
       // Top 10 ordenado por dataCriacao ascendente
    List<Topico> findTop10ByOrderByDataCriacaoAsc();

    @Query("SELECT t FROM Topico t " +
            "WHERE t.curso.nome = :nomeCurso " +
            "AND YEAR(t.dataCriacao) = :ano")
    List<Topico> findByCursoNomeAndAno(
            @Param("nomeCurso") String nomeCurso,
            @Param("ano") int ano);
}
