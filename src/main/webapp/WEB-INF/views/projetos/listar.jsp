<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Projetos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Lista de Projetos</h2>
    <a href="/projetos/novo" class="btn btn-primary mb-3">Novo Projeto</a>
    
	<table class="table table-striped">
	    <thead>
	        <tr>
	            <th>Nome</th>
	            <th>Status</th>
	            <th>Início</th>
	            <th>Previsão Término</th>
	            <th>Risco</th>
	            <th>Gerente</th>
	            <th>Ações</th>
	        </tr>
	    </thead>
	    <tbody>
	        <c:forEach items="${projetos}" var="projeto" varStatus="status">
	            <tr>
	                <td>${projeto.nome}</td>
	                <td>${projeto.status}</td>
	                <td>
	                    <fmt:formatDate value="${projetosDataDTO[status.index].dataInicio}" pattern="dd/MM/yyyy"/>
	                </td>
	                <td>
	                    <fmt:formatDate value="${projetosDataDTO[status.index].dataPrevisaoFim}" pattern="dd/MM/yyyy"/>
	                </td>
	                <td>
	                    <span class="badge ${projeto.risco == 'alto' ? 'bg-danger' : 
	                                       projeto.risco == 'medio' ? 'bg-warning' : 'bg-success'}">
	                        ${projeto.risco}
	                    </span>
	                </td>
	                <td>${projeto.gerente.nome}</td>
	                <td>
	                    <a href="/projetos/editar/${projeto.id}" class="btn btn-sm btn-warning">Editar</a>
	                    <a href="/projetos/${projeto.id}/membros" class="btn btn-sm btn-info">Membros</a>
	                    <a href="/projetos/excluir/${projeto.id}" class="btn btn-sm btn-danger" 
	                       onclick="return confirm('Tem certeza que deseja excluir?')">Excluir</a>
	                </td>
	            </tr>
	        </c:forEach>
	    </tbody>
	</table>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>