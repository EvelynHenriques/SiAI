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

    MenuService menuService = new MenuService();  // Assuming MenuService is set to be used directly like this
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
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
    <a href="logout.jsp">Sair</a>
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
