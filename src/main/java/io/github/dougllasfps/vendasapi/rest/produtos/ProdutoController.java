package io.github.dougllasfps.vendasapi.rest.produtos;

import io.github.dougllasfps.vendasapi.model.Produto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import io.github.dougllasfps.vendasapi.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/produtos")
@CrossOrigin("*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping
    public List<ProdutoFormRequest> getLista() {
    return repository.findAll().stream()
    .map(ProdutoFormRequest::fromModel)
    .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProdutoFormRequest> getById(@PathVariable Long id) {
    Optional<Produto> produtoExistente = repository.findById(id);
    if (produtoExistente.isEmpty()) {
    return ResponseEntity.notFound().build();
    }

    var produto = produtoExistente.map(ProdutoFormRequest::fromModel).get();
    return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ProdutoFormRequest salvar(@RequestBody ProdutoFormRequest produto) {
        Produto entidadeProduto = produto.toModel();
        repository.save(entidadeProduto);
        return ProdutoFormRequest.fromModel(entidadeProduto);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody ProdutoFormRequest produto) {
        Optional<Produto> produtoExistente = repository.findById(id);

        if (produtoExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Produto entidade = produto.toModel();
        entidade.setId(id);
        repository.save(entidade);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        Optional<Produto> prdoutoExistente = repository.findById(id);

        if(prdoutoExistente.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        repository.delete(prdoutoExistente.get());
        return ResponseEntity.ok().build();
    }
}