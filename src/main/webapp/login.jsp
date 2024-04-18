<%
    String cadastroConcluido = (String) request.getAttribute("cadastroConcluido");
    if (cadastroConcluido != null) {
%>
<script>
    alert("<%= cadastroConcluido %>");

</script>
<%
    }

    session.invalidate(); // Invalida a sessão, removendo todos os atributos associados a ela
%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head lang="pt-br">
    <title>SiAI</title>
<meta charset="UTF-8">
<title>Login</title>

<style>
    <%@include file="/css/login.css"%>
</style>
</head>
<body>

<form action="login" method="post" onsubmit="return showLoader()">
    <h2>SiAI</h2>
    <label for="email">Email:</label><br>
    <input type="text" id="email" name="email"><br>
    <label for="password">Senha:</label><br>
    <input type="password" id="password" name="password"><br><br>
    <div id="loader"></div>
    <input type="submit" id="submitButton" value="Entrar">
    <p>Ainda não possui um cadastro?</p><a href="cadastro.jsp">Cadastrar</a>
</form>
<script>
    // Função para mostrar o loader assim que a página é carregada
    document.body.onload = function() {
        document.getElementById("loader").style.display = "block";
    };

    // Função para ocultar o div de carregamento quando o carregamento da página estiver completo
    window.onload = function() {
        document.getElementById("loader").style.display = "none";
    };

    // Função para mostrar o loader e desabilitar o botão de submit durante o envio do formulário
    function showLoader() {
        document.getElementById("loader").style.display = "block"; // Mostra o loader
        document.getElementById("submitButton").style.marginTop = "20px";
        document.getElementById("submitButton").disabled = true; // Desabilita o botão de submit
        return true;
    }

</script>

<%
    String erroLogin = (String) request.getAttribute("erroLogin");
    if (erroLogin != null) {
%>
<script>
    alert("<%= erroLogin %>");
</script>
<%
    }
%>
</body>
<br/>

</html>
