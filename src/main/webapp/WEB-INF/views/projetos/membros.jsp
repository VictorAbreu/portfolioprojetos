<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Membros do Projeto - ${projeto.nome}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Membros do Projeto - ${projeto.nome}</h2>
    
    <div class="row mt-4">
        <div class="col-md-6">
            <h4>Membros Atuais</h4>
            <table class="table">
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Ação</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${projeto.membros}" var="membro">
                        <tr>
                            <td>${membro.nome}</td>
                            <td>
                                <form action="/projetos/${projeto.id}/membros/remover" method="post" style="display: inline;">
                                    <input type="hidden" name="pessoaId" value="${membro.id}">
                                    <button type="submit" class="btn btn-sm btn-danger">Remover</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
        <div class="col-md-6">
            <h4>Adicionar Membro</h4>
            <form action="/projetos/${projeto.id}/membros/adicionar" method="post">
                <div class="mb-3">
                    <select name="pessoaId" class="form-select" required>
                        <option value="">Selecione um funcionário</option>
                        <c:forEach items="${funcionarios}" var="funcionario">
                            <option value="${funcionario.id}">${funcionario.nome}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Adicionar</button>
            </form>
        </div>
    </div>
    
    <a href="/projetos" class="btn btn-secondary mt-3">Voltar</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>