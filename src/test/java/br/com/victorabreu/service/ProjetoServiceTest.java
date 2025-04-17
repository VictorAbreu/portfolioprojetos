package br.com.victorabreu.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import br.com.victorabreu.model.Pessoa;
import br.com.victorabreu.model.Projeto;
import br.com.victorabreu.repository.ProjetoRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ProjetoServiceTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private ProjetoService projetoService;

    private Projeto projeto;
    private Pessoa gerente;

    @BeforeEach
    void setUp() {
        gerente = new Pessoa();
        gerente.setId(1L);
        gerente.setGerente(true);
        gerente.setNome("Gerente Teste");

        projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto Teste");
        projeto.setGerente(gerente);
        projeto.setStatus("em análise");
        projeto.setDataInicio(LocalDate.now());
        projeto.setDataPrevisaoFim(LocalDate.now().plusDays(30));
    }

    @Test
    void testSalvarProjetoComGerenteValido() {
        when(pessoaService.buscarPorId(1L)).thenReturn(gerente);
        when(projetoRepository.save(any(Projeto.class))).thenReturn(projeto);
        
        Projeto salvo = projetoService.salvar(projeto);
        
        assertNotNull(salvo);
        assertEquals("Projeto Teste", salvo.getNome());
        verify(projetoRepository, times(1)).save(projeto);
    }

    @Test
    void testSalvarProjetoComGerenteInvalido() {
        Pessoa naoGerente = new Pessoa();
        naoGerente.setId(2L);
        naoGerente.setGerente(false);
        
        projeto.setGerente(naoGerente);
        
        when(pessoaService.buscarPorId(2L)).thenReturn(naoGerente);
        
        assertThrows(IllegalArgumentException.class, () -> {
            projetoService.salvar(projeto);
        });
    }

    @Test
    void testExcluirProjetoQuandoPodeSerExcluido() {
        projeto.setStatus("em análise");
        
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        doNothing().when(projetoRepository).delete(projeto);
        
        projetoService.excluir(1L);
        
        verify(projetoRepository, times(1)).delete(projeto);
    }

    @Test
    void testExcluirProjetoQuandoNaoPodeSerExcluido() {
        projeto.setStatus("iniciado");
        
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        
        assertThrows(IllegalStateException.class, () -> {
            projetoService.excluir(1L);
        });
        verify(projetoRepository, never()).delete(any());
    }

    @Test
    void testAdicionarMembroValido() {
        Pessoa funcionario = new Pessoa();
        funcionario.setId(2L);
        funcionario.setFuncionario(true);
        
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        when(pessoaService.buscarPorId(2L)).thenReturn(funcionario);
        when(projetoRepository.save(any(Projeto.class))).thenReturn(projeto);
        
        projetoService.adicionarMembro(1L, 2L);
        
        assertTrue(projeto.getMembros().contains(funcionario));
        verify(projetoRepository, times(1)).save(projeto);
    }

    @Test
    void testAdicionarMembroNaoFuncionario() {
        Pessoa naoFuncionario = new Pessoa();
        naoFuncionario.setId(2L);
        naoFuncionario.setFuncionario(false);
        
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        when(pessoaService.buscarPorId(2L)).thenReturn(naoFuncionario);
        
        assertThrows(IllegalArgumentException.class, () -> {
            projetoService.adicionarMembro(1L, 2L);
        });
        verify(projetoRepository, never()).save(any());
    }

    @Test
    void testCalcularRiscoAlto() {
        projeto.setDataFim(projeto.getDataPrevisaoFim().plusDays(31));
        projeto.calcularRisco();
        
        assertEquals("alto", projeto.getRisco());
    }

    @Test
    void testCalcularRiscoMedio() {
        projeto.setDataFim(projeto.getDataPrevisaoFim().plusDays(20));
        projeto.calcularRisco();
        
        assertEquals("medio", projeto.getRisco());
    }

    @Test
    void testCalcularRiscoBaixo() {
        projeto.setDataFim(projeto.getDataPrevisaoFim().minusDays(1));
        projeto.calcularRisco();
        
        assertEquals("baixo", projeto.getRisco());
    }

    @Test
    void testListarTodosProjetos() {
        when(projetoRepository.findAll()).thenReturn(Collections.singletonList(projeto));
        
        assertEquals(1, projetoService.listarTodos().size());
        verify(projetoRepository, times(1)).findAll();
    }
}
