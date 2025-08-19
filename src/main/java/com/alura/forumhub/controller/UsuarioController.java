package com.alura.forumhub.controller;

import com.alura.forumhub.domain.usuario.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuariorepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid UsuarioDados dados, UriComponentsBuilder uriBuilder) {
        var usuario = new Usuario(dados);
        usuariorepository.save(usuario);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioDadosDetalhamento(usuario));
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioListarDados>> listarUsuarios(@PageableDefault(size=10, sort = {"nome"}) Pageable paginacao)
    {
        var page = usuariorepository.findAllByAtivoTrue(paginacao).map(UsuarioListarDados::new);
        return  ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarUsuario(@RequestBody @Valid UsuarioAtualizarDados dados){
        //System.out.println(dados);
        var usuario = usuariorepository.getReferenceById(dados.id());
        usuario.atualizarDadoNovo(dados);
        return ResponseEntity.ok(new UsuarioDadosDetalhamento(usuario));
    }

//    @DeleteMapping("/{id}")
//    @Transactional
//    public void deletarUsuario(@PathVariable Long id){
//        usuariorepository.deleteById(id);
//    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarUsuario(@PathVariable Long id){
        var usuario = usuariorepository.getReferenceById(id);
        usuario.desativar();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharUsuario(@PathVariable Long id){
        var usuario = usuariorepository.getReferenceById(id);
        return ResponseEntity.ok(new UsuarioDadosDetalhamento(usuario));
    }

}
