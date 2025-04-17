package br.com.victorabreu.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import br.com.victorabreu.model.Pessoa;
import br.com.victorabreu.repository.PessoaRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @Test
    void testListarTodos() {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setId(1L);
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setId(2L);
        
        when(pessoaRepository.findAll()).thenReturn(Arrays.asList(pessoa1, pessoa2));
        
        List<Pessoa> pessoas = pessoaService.listarTodos();
        
        assertEquals(2, pessoas.size());
        verify(pessoaRepository, times(1)).findAll();
    }

    @Test
    void testListarFuncionarios() {
        Pessoa funcionario = new Pessoa();
        funcionario.setId(1L);
        funcionario.setFuncionario(true);
        
        when(pessoaRepository.findByFuncionarioTrue()).thenReturn(Arrays.asList(funcionario));
        
        List<Pessoa> funcionarios = pessoaService.listarFuncionarios();
        
        assertEquals(1, funcionarios.size());
        assertTrue(funcionarios.get(0).isFuncionario());
        verify(pessoaRepository, times(1)).findByFuncionarioTrue();
    }

    @Test
    void testSalvarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João Silva");
        pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));
        pessoa.setFuncionario(true);
        
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);
        
        Pessoa salva = pessoaService.salvar(pessoa);
        
        assertNotNull(salva);
        assertEquals("João Silva", salva.getNome());
        verify(pessoaRepository, times(1)).save(pessoa);
    }

    @Test
    void testBuscarPorIdExistente() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        
        Pessoa encontrada = pessoaService.buscarPorId(1L);
        
        assertNotNull(encontrada);
        assertEquals(1L, encontrada.getId());
        verify(pessoaRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorIdNaoExistente() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.buscarPorId(1L);
        });
        verify(pessoaRepository, times(1)).findById(1L);
    }

    @Test
    void testExcluirPessoa() {
        doNothing().when(pessoaRepository).deleteById(1L);
        
        pessoaService.excluir(1L);
        
        verify(pessoaRepository, times(1)).deleteById(1L);
    }
}
