package livraria;

import com.exemplo.livraria.model.Livro;
import com.exemplo.livraria.service.LivroService;
import controller.LivroController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LivroControllerTest {

    @Mock
    private LivroService livroService;

    @InjectMocks
    private LivroController livroController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(livroController).build();
    }

    @Test
    public void testGetAllLivros() throws Exception {
        Livro livro = new Livro(1L, "Livro Teste", "Autor Teste");
        when(livroService.buscarTodosLivros()).thenReturn(Collections.singletonList(livro));

        mockMvc.perform(get("/livros")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titulo").value("Livro Teste"));
    }

    @Test
    public void testGetLivroById() throws Exception {
        Livro livro = new Livro(1L, "Livro Teste", "Autor Teste");
        when(livroService.buscarLivroPorId(1L)).thenReturn(livro);

        mockMvc.perform(get("/livros/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Livro Teste"));
    }

    @Test
    public void testCreateLivro() throws Exception {
        Livro livro = new Livro(1L, "Novo Livro", "Novo Autor");
        when(livroService.salvarLivro(any(Livro.class))).thenReturn(livro);

        String livroJson = "{\"titulo\":\"Novo Livro\",\"autor\":\"Novo Autor\"}";

        mockMvc.perform(post("/livros")
                .content(livroJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Novo Livro"))
                .andExpect(jsonPath("$.autor").value("Novo Autor"));
    }
}
