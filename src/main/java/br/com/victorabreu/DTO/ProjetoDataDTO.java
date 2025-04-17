package br.com.victorabreu.DTO;

import java.util.Date;

public class ProjetoDataDTO {
    private Date dataInicio;
    private Date dataFim;
    private Date dataPrevisaoFim;

    public ProjetoDataDTO(Date dataInicio, Date dataFim, Date dataPrevisaoFim) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataPrevisaoFim = dataPrevisaoFim;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getDataPrevisaoFim() {
        return dataPrevisaoFim;
    }

    public void setDataPrevisaoFim(Date dataPrevisaoFim) {
        this.dataPrevisaoFim = dataPrevisaoFim;
    }
}
