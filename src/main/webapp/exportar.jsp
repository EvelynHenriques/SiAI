<% // Verificando se o usuário está autenticado
session = request.getSession(false);
Integer usuarioId = (Integer) session.getAttribute("usuarioLogado");
if (usuarioId == null) {
// Usuário não autenticado, redireciona para a página de login
System.out.println("NAO LOGADO");
response.sendRedirect("login.jsp");
return;
}
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulário</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.css">
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
    <form method="post" action="export" onsubmit="showLoader()">
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
        <div id="loader"></div>
        <input type="submit" id="submitButton" value="Exportar">
    </form>
</section>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<script>
    $( function() {
        $( "#dataInicio" ).datepicker({
            dateFormat: 'dd/mm/yy'
        });
        $( "#dataFim" ).datepicker({
            dateFormat: 'dd/mm/yy'
        });
    } );

    document.body.onload = function() {
        document.getElementById("loader").style.display = "block";
    };

    window.onload = function() {
        document.getElementById("loader").style.display = "none";
    };

    function showLoader() {
        document.getElementById("loader").style.display = "block"; // Mostra o loader
        document.getElementById("loader").style.marginTop = "30px";
        document.getElementById("submitButton").style.marginTop = "30px";
        document.getElementById("submitButton").disabled = true; // Desabilita o botão de submit
        return true;
    }
</script>
</body>
</html>
