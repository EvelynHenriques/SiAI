<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulário</title>
    <!-- Adicione links para os arquivos CSS e JS necessários para o date picker -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.css">
    <!-- Importe os arquivos CSS -->
    <style>
        <%@include file="/css/header.css"%>
        <%@include file="/css/menu.css"%>
        <%@include file="/css/exportar.css"%>
    </style>
</head>
<body>
<header>
    <a class="menu" href="menu.jsp">Menu</a>
</header>
<nav>
    <a href="arranchamento.jsp">Preencher Arranchamento</a>
    <a href="exportar.jsp">Exportar Arranchamento</a>
    <a href="login.jsp">Sair</a>
</nav>
<section>
    <form method="post" action="processarFormulario.jsp">
        <label for="dataInicio">Data de Início:</label>
        <input type="text" id="dataInicio" name="dataInicio" required>
        <br><br>
        <label for="dataFim">Data Final:</label>
        <input type="text" id="dataFim" name="dataFim" required>
        <br><br>
        <label for="turma">Turma:</label>
        <input type="number" id="turma" name="turma" placeholder="25" required>
        <br><br>
        <label for="pelotao">Pelotão:</label>
        <input type="number" id="pelotao" name="pelotao" placeholder="1" required>
        <br><br>
        <input type="submit" value="Exportar">
    </form>
</section>
<!-- Importe as bibliotecas jQuery e jQuery UI -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<!-- Script para inicializar o date picker -->
<script>
    $( function() {
        $( "#dataInicio" ).datepicker();
        $( "#dataFim" ).datepicker();
    } );
</script>
</body>
</html>
