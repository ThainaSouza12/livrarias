package livraria;

import com.exemplo.livraria.model.Livro;
import com.exemplo.livraria.repository.LivroRepository;
import com.exemplo.livraria.service.LivroService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService livroService;

    private Livro livro;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        livro = new Livro(1L, "Titulo Teste", "Autor Teste");
    }

    @Test
    public void testBuscarLivroPorId() {
        when(livroRepository.findById(anyLong())).thenReturn(Optional.of(livro));
        Livro resultado = livroService.buscarLivroPorId(1L);
        assertEquals("Titulo Teste", resultado.getTitulo());
        verify(livroRepository, times(1)).findById(1L);
    }

    @Test
    public void testSalvarLivro() {
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        Livro resultado = livroService.salvarLivro(new Livro(null, "Novo Livro", "Novo Autor"));
        Assertions.assertEquals("Novo Livro", resultado.getTitulo());
        verify(livroRepository, times(1)).save(any(Livro.class));
    }

    @Test
    public void testBuscarTodosLivros() {
        livroService.buscarTodosLivros();
        verify(livroRepository, times(1)).findAll();
    }
}
