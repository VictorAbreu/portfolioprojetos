package br.com.victorabreu.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projeto")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_previsao_fim")
    private LocalDate dataPrevisaoFim;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(length = 5000)
    private String descricao;

    @Column(length = 45)
    private String status;

    private Float orcamento;

    @Column(length = 45)
    private String risco;

    @ManyToOne
    @JoinColumn(name = "idgerente", nullable = false)
    private Pessoa gerente;

    @ManyToMany
    @JoinTable(
        name = "membros",
        joinColumns = @JoinColumn(name = "idprojeto"),
        inverseJoinColumns = @JoinColumn(name = "idpessoa"))
    private Set<Pessoa> membros = new HashSet<>();

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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataPrevisaoFim() {
        return dataPrevisaoFim;
    }

    public void setDataPrevisaoFim(LocalDate dataPrevisaoFim) {
        this.dataPrevisaoFim = dataPrevisaoFim;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Float orcamento) {
        this.orcamento = orcamento;
    }

    public String getRisco() {
        return risco;
    }

    public void setRisco(String risco) {
        this.risco = risco;
    }

    public Pessoa getGerente() {
        return gerente;
    }

    public void setGerente(Pessoa gerente) {
        this.gerente = gerente;
    }

    public Set<Pessoa> getMembros() {
        return membros;
    }

    public void setMembros(Set<Pessoa> membros) {
        this.membros = membros;
    }

    // Métodos de negócio
    public boolean podeSerExcluido() {
        return !("iniciado".equals(status) || 
               "em andamento".equals(status) || 
               "encerrado".equals(status));
    }

    public void calcularRisco() {
        if (dataPrevisaoFim == null || dataInicio == null) {
            this.risco = "baixo";
            return;
        }

        LocalDate dataReferencia = (dataFim != null) ? dataFim : LocalDate.now();
        long diasAtraso = ChronoUnit.DAYS.between(dataPrevisaoFim, dataReferencia);

        if (diasAtraso > 30) {
            this.risco = "alto";
        } else if (diasAtraso > 15) {
            this.risco = "medio";
        } else {
            this.risco = "baixo";
        }
    }
}
