package br.com.victorabreu.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "datanascimento")
    private LocalDate dataNascimento;

    @Column(length = 14)
    private String cpf;

    private boolean funcionario;
    private boolean gerente;

    @OneToMany(mappedBy = "gerente")
    private Set<Projeto> projetosComoGerente = new HashSet<>();

    @ManyToMany(mappedBy = "membros")
    private Set<Projeto> projetosComoMembro = new HashSet<>();

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean isFuncionario() {
        return funcionario;
    }

    public void setFuncionario(boolean funcionario) {
        this.funcionario = funcionario;
    }

    public boolean isGerente() {
        return gerente;
    }

    public void setGerente(boolean gerente) {
        this.gerente = gerente;
    }

    public Set<Projeto> getProjetosComoGerente() {
        return projetosComoGerente;
    }

    public void setProjetosComoGerente(Set<Projeto> projetosComoGerente) {
        this.projetosComoGerente = projetosComoGerente;
    }

    public Set<Projeto> getProjetosComoMembro() {
        return projetosComoMembro;
    }

    public void setProjetosComoMembro(Set<Projeto> projetosComoMembro) {
        this.projetosComoMembro = projetosComoMembro;
    }
}
