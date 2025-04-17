package br.com.victorabreu;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import br.com.victorabreu.model.Pessoa;
import br.com.victorabreu.model.Projeto;
import br.com.victorabreu.repository.ProjetoRepository;

@DataJpaTest
@ActiveProfiles("test")
public class ProjetoIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Test
    void testSalvarEBuscarProjeto() {
        Pessoa gerente = new Pessoa();
        gerente.setNome("Gerente Teste");
        gerente.setGerente(true);
        entityManager.persist(gerente);
        
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Teste");
        projeto.setGerente(gerente);
        projeto.setDataInicio(LocalDate.now());
        projeto.setDataPrevisaoFim(LocalDate.now().plusDays(30));
        projeto.setStatus("em análise");
        
        Projeto saved = projetoRepository.save(projeto);
        
        Projeto found = projetoRepository.findById(saved.getId()).orElse(null);
        
        assertThat(found).isNotNull();
        assertThat(found.getNome()).isEqualTo(projeto.getNome());
        assertThat(found.getGerente().getId()).isEqualTo(gerente.getId());
    }

    @Test
    void testCalcularRiscoAposPersistencia() {
        Pessoa gerente = new Pessoa();
        gerente.setNome("Gerente Teste");
        gerente.setGerente(true);
        entityManager.persist(gerente);
        
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto com Atraso Grave");
        projeto.setGerente(gerente);
        projeto.setDataInicio(LocalDate.now().minusDays(90));
        projeto.setDataPrevisaoFim(LocalDate.now().minusDays(60));
        projeto.setDataFim(LocalDate.now());
        projeto.setStatus("encerrado");
        
        projeto.calcularRisco();
        
        Projeto saved = projetoRepository.save(projeto);
        
        assertThat(saved.getRisco()).isEqualTo("alto");
        
        Projeto found = projetoRepository.findById(saved.getId()).orElseThrow();
        assertThat(found.getRisco()).isEqualTo("alto");
    }
}
