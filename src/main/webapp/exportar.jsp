<%
    // Verificando se o usuário está autenticado
    session = request.getSession();
    if (session == null || session.getAttribute("usuarioLogado") == null) {
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
    <!-- Adicione links para os arquivos CSS e JS necessários para o date picker -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <!-- Script para inicializar o date picker -->
    <script>
        $( function() {
            $( "#dataInicio" ).datepicker({
                dateFormat: 'dd/mm/yy'
            });
            $( "#dataFim" ).datepicker({
                dateFormat: 'dd/mm/yy'
            });
        } );
    </script>
</head>
<body>
<h2>Formulário</h2>
<form method="post" action="export">
    <label for="dataInicio">Data de Início:</label>
    <input type="text" id="dataInicio" name="dataInicio" required>
    <br><br>
    <label for="dataFim">Data Final:</label>
    <input type="text" id="dataFim" name="dataFim" required>
    <br><br>
    <label for="turma">Turma:</label>
    <input type="text" id="turma" name="turma" required>
    <br><br>
    <label for="pelotao">Pelotão:</label>
    <input type="text" id="pelotao" name="pelotao" required>
    <br><br>
    <input type="submit" value="Enviar">
</form>
</body>
</html>
