package com.alura.forumhub.domain.topico;

import com.alura.forumhub.domain.curso.Curso;
import com.alura.forumhub.domain.curso.CursoRepository;
import com.alura.forumhub.domain.usuario.Usuario;
import com.alura.forumhub.domain.usuario.UsuarioRepository;
import med.voll.api.domain.ValidacaoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public TopicoService(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository, CursoRepository cursoRepository)
    {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    /*public TopicoService(TopicoRepository topicoRepository)
    {
        this.topicoRepository = topicoRepository;
    }*/

    public List<Topico> buscarPorCursoEAno(String nomeCurso, int ano)
    {
        return topicoRepository.findByCursoNomeAndAno(nomeCurso, ano);
    }

    public List<Topico> listarTop10MaisAntigos()
    {
        return topicoRepository.findTop10ByOrderByDataCriacaoAsc();
    }

    public Page<TopicoDadosResponse> listarTodos(Pageable pageable)
    {
        return topicoRepository.findAll(pageable)
                .map(t -> new TopicoDadosResponse(
                        t.getId(),
                        t.getTitulo(),
                        t.getMensagem(),
                        t.getCurso().getNome(),
                        t.getDataCriacao()
                ));
    }

    public TopicoDetalheDadosResponse buscarPorId(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico com ID " + id + " não encontrado."));
        //System.out.println(topico);
       // if (topico == null) {
        //    throw new ValidacaoException("Tópico com ID " + id + " não encontrado.");
       // }


        return new TopicoDetalheDadosResponse(
                        topico.getId(),
                        topico.getTitulo(),
                        topico.getMensagem(),
                        topico.getDataCriacao(),
                        topico.getUsuario().getNome(),
                        topico.getCurso().getNome(),
                        topico.getStatus()

                );
    }

    public Optional<TopicoDetalheDadosResponse> atualizar(Long id, TopicoDadosAtualizacao dto) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isEmpty()) {
            throw new ValidacaoException("Topico não encontrado.");
        }

        if (topicoRepository.existsByTituloAndMensagem(dto.titulo(), dto.mensagem())) {
            throw new ValidacaoException("Já existe um tópico com esse título e mensagem!");
        }

        Topico topico = optionalTopico.get();
        topico.setTitulo(dto.titulo());
        topico.setMensagem(dto.mensagem());
        topico.setStatus(dto.status());

        Topico atualizado = topicoRepository.save(topico);

        return Optional.of(new TopicoDetalheDadosResponse(
                atualizado.getId(),
                atualizado.getTitulo(),
                atualizado.getMensagem(),
                atualizado.getDataCriacao(),
                atualizado.getUsuario().getNome(),
                atualizado.getCurso().getNome(),
                atualizado.getStatus()
        ));
    }

    public boolean excluir(Long id)
    {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {
            topicoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Topico salvar(TopicoDado dto) {
        // Verifica duplicidade
        if (topicoRepository.existsByTituloAndMensagem(dto.titulo(), dto.mensagem())) {
            throw new ValidacaoException("Já existe um tópico com esse título e mensagem!");
        }

        // Busca Usuario
        Optional<Usuario> usuario = usuarioRepository.findById(dto.usuario_id());
        if (!usuario.isPresent()) {
            throw new ValidacaoException("Usuário não encontrado."); }

        // Busca Curso
        Curso curso = cursoRepository.findByNome(dto.nomeCurso());
        if (curso == null) {
            throw new ValidacaoException("Curso não encontrado.");
        }

        // Monta Topico
        Topico topico = new Topico();
        topico.setTitulo(dto.titulo());
        topico.setMensagem(dto.mensagem());
        topico.setUsuario(usuario.get());
        topico.setCurso(curso);

        return topicoRepository.save(topico);
    }
}

