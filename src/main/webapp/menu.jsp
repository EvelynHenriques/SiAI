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
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            overflow: hidden;
            height: 100%;
        }
        <%@include file="/css/header.css"%>

        .imagem {
            position: fixed; /* Define a posição fixa para a imagem */
            top: 0;
            left: 0;
            width: 100%; /* Define a largura da imagem como 100% da largura da janela de visualização */
            height: 100%; /* Define a altura da imagem como 100% da altura da janela de visualização */
            z-index: -1; /* Coloca a imagem atrás do conteúdo */
        }

        .imagem img {
            width: 100%;
            height: 100%;
            object-fit: cover; /* Garante que a imagem cubra toda a área da div */
        }
        .imagem::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5); /* cor da sombra com 50% de opacidade */
        }
        .menu{
            font-size: 30px;
            color:white;
            text-decoration: none;
            font-family: 'Arial', sans-serif;
        }

    </style>
</head>
<body>
<header>
    <a class="menu" href="menu.jsp">Menu</a>
</header>
<nav>
    <a href="arranchamento.jsp">Preencher Arranchamento</a>
    <a href="consultar_arranchamento.jsp">Consultar Arranchamento</a>
    <a href="exportar_arranchamento.jsp">Exportar Arranchamento</a>
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
