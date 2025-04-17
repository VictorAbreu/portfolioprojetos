package br.com.victorabreu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.victorabreu.model.Pessoa;
import br.com.victorabreu.repository.PessoaRepository;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public List<Pessoa> listarTodos() {
        return pessoaRepository.findAll();
    }

    public List<Pessoa> listarFuncionarios() {
        return pessoaRepository.findByFuncionarioTrue();
    }

    public List<Pessoa> listarGerentes() {
        return pessoaRepository.findByGerenteTrue();
    }

    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa buscarPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
    }

    public void excluir(Long id) {
        pessoaRepository.deleteById(id);
    }
}