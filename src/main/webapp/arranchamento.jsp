<%--
  Created by IntelliJ IDEA.
  User: carol
  Date: 14/04/2024
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Agenda de Refeições</title>
    <style>
        <%@include file="/css/header.css"%>
        html {
            height: 100vh;
        }
        body {
            height: 100%;
            margin: 0;
        }
        .card {
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 10px;
            margin: 10px;
            float: left;
        }
        .day {
            font-weight: bold;
            margin-bottom: 5px;
        }
        .meal {
            margin-bottom: 3px;
        }
        .cards {
            display: grid;
            grid-template-columns: repeat(7, 1fr);
            justify-content: center;
            align-items: center;
        }
    </style>
</head>
<body>
<header>
    <h1>Menu</h1>
</header>
<nav>
    <a href="arranchamento.jsp">Preencher Arranchamento</a>
    <a href="consultar_arranchamento.jsp">Consultar Arranchamento</a>
    <a href="extrair_arranchamento.jsp">Extrair Arranchamento</a>
</nav>
<%
    String[] daysOfWeek = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};
    String[] meals = {"Café", "Almoço", "Janta", "Ceia"};
%>
<form class="cards" action="arranchamento" method="post">
<% for (int i = 0; i < 7; i++) { %>
<div class="card">
    <div class="day"><%= daysOfWeek[i] %></div>
    <% for (int j = 0; j < meals.length; j++) { %>
    <div class="meal">
        <input type="checkbox" id="<%= daysOfWeek[i] + meals[j] %>" name="<%= daysOfWeek[i] + meals[j] %>">
        <label for="<%= daysOfWeek[i] + meals[j] %>"><%= meals[j] %></label>
    </div>
    <% } %>
</div>
<% } %>

    <button type="submit">Enviar</button>
</form>

</body>
</html>
