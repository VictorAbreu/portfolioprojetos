<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${projeto.id == null ? 'Novo Projeto' : 'Editar Projeto'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>${projeto.id == null ? 'Novo Projeto' : 'Editar Projeto'}</h2>
    
    <form method="post" action="/projetos/salvar">
        <input type="hidden" name="id" value="${projeto.id}">
        
        <div class="mb-3">
            <label for="nome" class="form-label">Nome</label>
            <input type="text" class="form-control" id="nome" name="nome" value="${projeto.nome}" required>
        </div>
        
        <div class="mb-3">
            <label for="dataInicio" class="form-label">Data de Início</label>
            <input type="date" class="form-control" id="dataInicio" name="dataInicio" 
                   value="${projeto.dataInicio}">
        </div>
        
        <div class="mb-3">
            <label for="dataPrevisaoFim" class="form-label">Previsão de Término</label>
            <input type="date" class="form-control" id="dataPrevisaoFim" name="dataPrevisaoFim" 
                   value="${projeto.dataPrevisaoFim}">
        </div>
        
        <div class="mb-3">
            <label for="dataFim" class="form-label">Data Real de Término</label>
            <input type="date" class="form-control" id="dataFim" name="dataFim" 
                   value="${projeto.dataFim}">
        </div>
        
        <div class="mb-3">
            <label for="descricao" class="form-label">Descrição</label>
            <textarea class="form-control" id="descricao" name="descricao" rows="3">${projeto.descricao}</textarea>
        </div>
        
        <div class="mb-3">
            <label for="status" class="form-label">Status</label>
            <select class="form-select" id="status" name="status" required>
                <option value="">Selecione um status</option>
                <option value="em análise" ${projeto.status == 'em análise' ? 'selected' : ''}>Em Análise</option>
                <option value="análise realizada" ${projeto.status == 'análise realizada' ? 'selected' : ''}>Análise Realizada</option>
                <option value="análise aprovada" ${projeto.status == 'análise aprovada' ? 'selected' : ''}>Análise Aprovada</option>
                <option value="iniciado" ${projeto.status == 'iniciado' ? 'selected' : ''}>Iniciado</option>
                <option value="planejado" ${projeto.status == 'planejado' ? 'selected' : ''}>Planejado</option>
                <option value="em andamento" ${projeto.status == 'em andamento' ? 'selected' : ''}>Em Andamento</option>
                <option value="encerrado" ${projeto.status == 'encerrado' ? 'selected' : ''}>Encerrado</option>
                <option value="cancelado" ${projeto.status == 'cancelado' ? 'selected' : ''}>Cancelado</option>
            </select>
        </div>
        
        <div class="mb-3">
            <label for="orcamento" class="form-label">Orçamento</label>
            <input type="number" step="0.01" class="form-control" id="orcamento" name="orcamento" 
                   value="${projeto.orcamento}">
        </div>
        
        <div class="mb-3">
            <label for="gerente" class="form-label">Gerente Responsável</label>
            <select class="form-select" id="gerente" name="gerente.id" required>
                <option value="">Selecione um gerente</option>
                <c:forEach items="${gerentes}" var="gerente">
                    <option value="${gerente.id}" ${projeto.gerente != null && projeto.gerente.id == gerente.id ? 'selected' : ''}>
                        ${gerente.nome}
                    </option>
                </c:forEach>
            </select>
        </div>
        
        <button type="submit" class="btn btn-primary">Salvar</button>
        <a href="/projetos" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>