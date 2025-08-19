package com.alura.forumhub.controller;

import com.alura.forumhub.domain.topico.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/topicos")

public class TopicosController {

    private final TopicoService topicoService;


    public TopicosController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@Validated @RequestBody TopicoDado dto) {
        Topico salvo = topicoService.salvar(dto);
        TopicoDadosResponse response = new TopicoDadosResponse(salvo.getId(), salvo.getTitulo(), salvo.getMensagem(), salvo.getCurso().getNome(), salvo.getDataCriacao());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/primeiros10")
    public ResponseEntity<List<TopicoDadosResponse>> listarTop10()
    {
        List<Topico> topicos = topicoService.listarTop10MaisAntigos();

        List<TopicoDadosResponse> resposta = topicos.stream()
                .map(t -> new TopicoDadosResponse(
                        t.getId(),
                        t.getTitulo(),
                        t.getMensagem(),
                        t.getCurso().getNome(),
                        t.getDataCriacao()))
                .toList();

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/buscar")


    public ResponseEntity<List<TopicoDadosResponse>> buscarPorCursoEAno(
            @RequestParam String nomeCurso,
            @RequestParam int ano)
    {

        List<Topico> topicos = topicoService.buscarPorCursoEAno(nomeCurso, ano);

        List<TopicoDadosResponse> resposta = topicos.stream()
                .map(t -> new TopicoDadosResponse(
                        t.getId(),
                        t.getTitulo(),
                        t.getMensagem(),
                        t.getCurso().getNome(),
                        t.getDataCriacao()))
                .toList();

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/listartodos")
    public ResponseEntity<Page<TopicoDadosResponse>> listarTodos(
            @PageableDefault(size = 5, sort = "dataCriacao") Pageable pageable)
    {

        Page<TopicoDadosResponse> pagina = topicoService.listarTodos(pageable);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDetalheDadosResponse> buscarPorId(@PathVariable Long id)
    {
        TopicoDetalheDadosResponse topico = topicoService.buscarPorId(id);
        return ResponseEntity.ok(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDetalheDadosResponse> atualizar(
            @PathVariable Long id,
            @RequestBody TopicoDadosAtualizacao dto)
    {

        return topicoService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id)
    {
        boolean excluido = topicoService.excluir(id);

        if (excluido) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }

}