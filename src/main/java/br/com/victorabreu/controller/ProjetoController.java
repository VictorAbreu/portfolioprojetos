package br.com.victorabreu.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.victorabreu.DTO.ProjetoDataDTO;
import br.com.victorabreu.model.Projeto;
import br.com.victorabreu.service.PessoaService;
import br.com.victorabreu.service.ProjetoService;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;
    private final PessoaService pessoaService;

    public ProjetoController(ProjetoService projetoService, PessoaService pessoaService) {
        this.projetoService = projetoService;
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public String listarProjetos(Model model) {
        List<Projeto> projetos = projetoService.listarTodos();

        List<ProjetoDataDTO> projetosDataDTO = projetos.stream()
            .map(projeto -> {
                Date dataInicio = convertLocalDateToDate(projeto.getDataInicio());
                Date dataFim = convertLocalDateToDate(projeto.getDataFim());
                Date dataPrevisaoFim = convertLocalDateToDate(projeto.getDataPrevisaoFim());

                return new ProjetoDataDTO(dataInicio, dataFim, dataPrevisaoFim);
            })
            .collect(Collectors.toList());

        model.addAttribute("projetos", projetos);
        model.addAttribute("projetosDataDTO", projetosDataDTO);

        return "projetos/listar";
    }

    private Date convertLocalDateToDate(LocalDate localDate) {
        if (localDate != null) {
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovo(Model model) {
        model.addAttribute("projeto", new Projeto());
        model.addAttribute("gerentes", pessoaService.listarGerentes());
        return "projetos/formulario";
    }

    @PostMapping("/salvar")
    public String salvarProjeto(@ModelAttribute Projeto projeto) {
        projetoService.salvar(projeto);
        return "redirect:/projetos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("projeto", projetoService.buscarPorId(id));
        model.addAttribute("gerentes", pessoaService.listarGerentes());
        return "projetos/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluirProjeto(@PathVariable Long id) {
        projetoService.excluir(id);
        return "redirect:/projetos";
    }

    @GetMapping("/{id}/membros")
    public String gerenciarMembros(@PathVariable Long id, Model model) {
        model.addAttribute("projeto", projetoService.buscarPorId(id));
        model.addAttribute("funcionarios", pessoaService.listarFuncionarios());
        return "projetos/membros";
    }

    @PostMapping("/{id}/membros/adicionar")
    public String adicionarMembro(@PathVariable Long id, @RequestParam Long pessoaId) {
        projetoService.adicionarMembro(id, pessoaId);
        return "redirect:/projetos/" + id + "/membros";
    }

    @PostMapping("/{id}/membros/remover")
    public String removerMembro(@PathVariable Long id, @RequestParam Long pessoaId) {
        projetoService.removerMembro(id, pessoaId);
        return "redirect:/projetos/" + id + "/membros";
    }
}
