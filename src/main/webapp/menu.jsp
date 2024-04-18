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

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <link href='https://fonts.googleapis.com/css?family=Allerta Stencil' rel='stylesheet'>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu</title>
    <style>
        <%@include file="/css/header.css"%>
        <%@include file="/css/menu.css"%>
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
    <div class="imagem">
        <img src="${pageContext.request.contextPath}/images/Captura%20de%20tela%202024-03-11%20083654.png"/>
    </div>

    <!-- Conteúdo da página vai aqui -->
</section>
</body>
</html>
