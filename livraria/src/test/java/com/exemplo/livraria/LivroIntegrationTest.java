package com.exemplo.livraria;

import com.exemplo.livraria.model.Livro;
import com.exemplo.livraria.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
public class LivroIntegrationTest {

    @Autowired
    private LivroRepository livroRepository;

    private MockMvc mockMvc;

    @Autowired
    public void setUp(LivroRepository livroRepository) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new Object[]{livroRepository}).build();
    }

    @Test
    public void testIntegrationCreateAndRetrieveLivro() throws Exception {
        Livro livro = new Livro(null, "Livro Integração", "Autor Integração");
        livroRepository.save(livro);

        mockMvc.perform(get("/livros")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Livro Integração"));
    }
}
