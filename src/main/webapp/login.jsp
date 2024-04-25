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
    document.body.onload = function() {
        document.getElementById("loader").style.display = "block";
    };

    window.onload = function() {
        document.getElementById("loader").style.display = "none";
    };

    function showLoader() {
        document.getElementById("loader").style.display = "block"; 
        document.getElementById("submitButton").style.marginTop = "20px";
        document.getElementById("submitButton").disabled = true; 
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
