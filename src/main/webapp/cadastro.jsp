<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br" >
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro</title>
    <style>
        <%@include file="/css/cadastro.css"%>
    </style>
</head>
<body>
<div class="container">
    <h2>Cadastro</h2>
    <form action="cadastro" method="post" >
        <div class="form-group">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" placeholder="Matheus Vanzan Pimentel De Oliveira" required>
        </div>
        <div class="form-group">
            <label for="nome_guerra">Nome de Guerra:</label>
            <input type="text" id="nome_guerra" name="nome_guerra" placeholder="Vanzan" required>
        </div>
        <div class="form-group">
            <label for="matricula">Matrícula:</label>
            <input type="text" id="matricula" name="matricula" placeholder="12040" required>
        </div>
        <div class="form-group">
            <label for="turma">Turma:</label>
            <input type="number" id="turma" name="turma" placeholder="16" required>
        </div>
        <div class="form-group">
            <label for="pelotao">Pelotão:</label>
            <input type="number" id="pelotao" name="pelotao" placeholder="1" required>
        </div>
        <div class="form-group">
            <label for="email">E-mail:</label>
            <input type="email" id="email" name="email" placeholder="vanzan.matheus@ime.eb.br" required>
        </div>
        <div class="form-group">
            <label for="senha">Senha:</label>
            <input type="password" id="senha" name="senha" placeholder="Digite sua senha" required>
        </div>
        <div class="form-group">
            <input type="submit" value="Cadastrar">
        </div>
    </form>
</div>

    <%
        String erroCadastro = (String) request.getAttribute("erroCadastro");
        if (erroCadastro != null) {
    %>
    <script>
        alert("<%= erroCadastro %>");
    </script>
    <%
        }
    %>
</div>
</body>
</html>
