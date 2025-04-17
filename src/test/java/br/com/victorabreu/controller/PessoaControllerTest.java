package br.com.victorabreu.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;

import br.com.victorabreu.model.Pessoa;
import br.com.victorabreu.service.PessoaService;

@SpringBootTest
@ActiveProfiles("test")
class PessoaControllerTest {

    @Mock
    private PessoaService pessoaService;

    @Mock
    private Model model;

    @InjectMocks
    private PessoaController pessoaController;

    @Test
    void testListarPessoas() {
        when(pessoaService.listarTodos()).thenReturn(Arrays.asList(new Pessoa(), new Pessoa()));
        
        String viewName = pessoaController.listarPessoas(model);
        
        assertEquals("pessoas/listar", viewName);
        verify(model, times(1)).addAttribute(eq("pessoas"), anyList());
        verify(pessoaService, times(1)).listarTodos();
    }

    @Test
    void testMostrarFormularioNovo() {
        String viewName = pessoaController.mostrarFormularioNovo(model);
        
        assertEquals("pessoas/formulario", viewName);
        verify(model, times(1)).addAttribute(eq("pessoa"), any(Pessoa.class));
    }

    @Test
    void testSalvarPessoa() {
        Pessoa pessoa = new Pessoa();
        
        String viewName = pessoaController.salvarPessoa(pessoa);
        
        assertEquals("redirect:/pessoas", viewName);
        verify(pessoaService, times(1)).salvar(pessoa);
    }

    @Test
    void testMostrarFormularioEdicao() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        
        when(pessoaService.buscarPorId(1L)).thenReturn(pessoa);
        
        String viewName = pessoaController.mostrarFormularioEdicao(1L, model);
        
        assertEquals("pessoas/formulario", viewName);
        verify(model, times(1)).addAttribute(eq("pessoa"), eq(pessoa));
    }

    @Test
    void testExcluirPessoa() {
        String viewName = pessoaController.excluirPessoa(1L);
        
        assertEquals("redirect:/pessoas", viewName);
        verify(pessoaService, times(1)).excluir(1L);
    }
}