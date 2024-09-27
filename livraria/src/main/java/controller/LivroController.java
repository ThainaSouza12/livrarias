package controller;

import com.exemplo.livraria.model.Livro;
import com.exemplo.livraria.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<Livro> getAllLivros() {
        return livroService.buscarTodosLivros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> getLivroById(@PathVariable Long id) {
        Livro livro = livroService.buscarLivroPorId(id);
        return livro != null ? ResponseEntity.ok(livro) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Livro createLivro(@RequestBody Livro livro) {
        return livroService.salvarLivro(livro);
    }
}
