package br.com.victorabreu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.victorabreu.model.Pessoa;
import br.com.victorabreu.model.Projeto;
import br.com.victorabreu.repository.ProjetoRepository;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final PessoaService pessoaService;

    public ProjetoService(ProjetoRepository projetoRepository, PessoaService pessoaService) {
        this.projetoRepository = projetoRepository;
        this.pessoaService = pessoaService;
    }

    @Transactional
    public Projeto salvar(Projeto projeto) {
        if (projeto.getGerente() == null || projeto.getGerente().getId() == null) {
            throw new IllegalArgumentException("Gerente é obrigatório");
        }
        
        Pessoa gerente = pessoaService.buscarPorId(projeto.getGerente().getId());
        if (!gerente.isGerente()) {
            throw new IllegalArgumentException("A pessoa selecionada não é um gerente");
        }
        
        projeto.setGerente(gerente);
        projeto.calcularRisco();
        return projetoRepository.save(projeto);
    }

    @Transactional
    public void excluir(Long id) {
        Projeto projeto = buscarPorId(id);
        
        if (!projeto.podeSerExcluido()) {
            throw new IllegalStateException("Não é possível excluir projetos com status 'iniciado', 'em andamento' ou 'encerrado'");
        }
        
        projetoRepository.delete(projeto);
    }

    public List<Projeto> listarTodos() {
        return projetoRepository.findAll();
    }

    public Projeto buscarPorId(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado"));
    }

    @Transactional
    public void adicionarMembro(Long projetoId, Long pessoaId) {
        Projeto projeto = buscarPorId(projetoId);
        Pessoa pessoa = pessoaService.buscarPorId(pessoaId);
        
        if (!pessoa.isFuncionario()) {
            throw new IllegalArgumentException("A pessoa selecionada não é um funcionário");
        }
        
        projeto.getMembros().add(pessoa);
        projetoRepository.save(projeto);
    }

    @Transactional
    public void removerMembro(Long projetoId, Long pessoaId) {
        Projeto projeto = buscarPorId(projetoId);
        Pessoa pessoa = pessoaService.buscarPorId(pessoaId);
        
        projeto.getMembros().remove(pessoa);
        projetoRepository.save(projeto);
    }
}
