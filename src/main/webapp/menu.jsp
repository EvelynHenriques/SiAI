<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="arranchamento.service.MenuService" %>
<%@ page import="java.util.List" %>

<%
    // Verifying user authentication
    session = request.getSession();
    if (session == null || session.getAttribute("usuarioLogado") == null) {
        System.out.println("NOT LOGGED IN");
        response.sendRedirect("login.jsp");
        return;
    }

    MenuService menuService = new MenuService(); 
    List<String> topUsuariosNomes = menuService.obterTop10UsuariosNomes();
    List<Integer> topUsuariosOcorrencias = menuService.obterTop10UsuariosOcorrencias();
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <link href='https://fonts.googleapis.com/css?family=Allerta Stencil' rel='stylesheet'>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu de Classificação</title>
    <style>
        <%@include file="/css/header.css"%>
        <%@include file="/css/menu.css"%>
        section {
            text-align: center;
            max-width: 30%;
            margin: auto;
            margin-top: 40px;
            padding: 20px;
            background-color: #1e352e;
            border-radius: 10px;
            animation: blink 0.5s infinite;

        }

        section h1 {
            color: #f3ecd6;
            font-family: 'Arial', sans-serif;
        }

        @keyframes blink {
            0% {
                box-shadow: 0 0 15px rgba(255, 255, 255, 0.8);
            }
            50% {
                box-shadow: 0 0 25px rgba(255, 255, 255, 1);
            }
            100% {
                box-shadow: 0 0 15px rgba(255, 255, 255, 0.8);
            }
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 5px;
            text-align: left;
        }
        td{
            font-family: "Times New Roman",sans-serif;;
        }
        th {
            background-color:#488273;
        }
        #shadow-host-companion {
            display: none;
        }
        tbody tr td:nth-child(2) {
            width: 50px;
        }

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
    <h1>Guerreiros Mais Arranchados</h1>
    <table>
        <thead>
        <tr>
            <th>Nome de Guerra</th>
            <th>Número de Arranchamentos</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int i = 0; i < topUsuariosNomes.size(); i++) {
        %>
        <tr>
            <td><%= topUsuariosNomes.get(i) %></td>
            <td><%= topUsuariosOcorrencias.get(i) %></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</section>
</body>
</html>
