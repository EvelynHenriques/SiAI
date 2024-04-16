<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Agenda de Refeições</title>
    <style>
        <%@include file="/css/header.css"%>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }
        .card {
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 10px;
            margin: 10px;
            float: left;
            width: calc(100% / 7 - 22px); /* Adjusting margins and padding */
        }
        .day {
            font-weight: bold;
            margin-bottom: 5px;
        }
        .meal {
            margin-bottom: 3px;
        }
        .cards {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            align-items: center;
        }
        #moreBtn, #submitBtn {
            display: block;
            width: 100%;
            padding: 10px 0;
            text-align: center;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<header>
    <h1>Menu de Arranchamento</h1>
</header>
<nav>
    <a href="arranchamento.jsp">Preencher Arranchamento</a>
    <a href="consultar_arranchamento.jsp">Consultar Arranchamento</a>
    <a href="extrair_arranchamento.jsp">Extrair Arranchamento</a>
</nav>
<%
    session = request.getSession();
    if (session == null || session.getAttribute("usuarioLogado") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    // Ajusta para daqui a duas semanas, menos os dias necessários para voltar para a segunda-feira
    cal.add(Calendar.DAY_OF_MONTH, 14 - (cal.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY));
    // Se hoje é segunda-feira, não subtrair nada.
    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
        cal.add(Calendar.DAY_OF_MONTH, -6);
    }
    String[] daysOfWeek = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};
    String[] meals = {"Café", "Almoço", "Janta", "Ceia"};
%>
<form class="cards" action="submitArranchamento" method="post" id="arranchamentoForm">
    <% for (int week = 0; week < 1; week++) { %>
    <% for (int i = 0; i < 7; i++) { %>
    Date date = cal.getTime();
    <div class="card">
        <div class="day"><%= daysOfWeek[i] %> (<fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/>)</div>
        <% for (int j = 0; j < meals.length; j++) { %>
        <div class="meal">
            <input type="checkbox" id="week<%= week %>day<%= i %>meal<%= j %>" name="arranchamento" value="<%= sdf.format(cal.getTime()) %>_<%= j+1 %>">
            <label for="week<%= week %>day<%= i %>meal<%= j %>"><%= meals[j] %></label>
        </div>
        <% } %>
        cal.add(Calendar.DATE, 1);
    </div>
    <% } %>
    cal.add(Calendar.DATE, -7); // Reset the week for next loop
    <% } %>
    <button type="button" id="moreBtn" onclick="loadMoreWeeks()">Exibir mais</button>
    <button type="submit" id="submitBtn">Enviar</button>
</form>

<script>
    var currentWeek = 1;
    function loadMoreWeeks() {
        var cardsDiv = document.querySelector('.cards');
        var newContent = '';
        for (let i = 0; i < 7; i++) {
            newContent += '<div class="card">' +
                '<div class="day">' + daysOfWeek[i] + '</div>' +
                meals.map((meal, j) =>
                    `<div class="meal">
                          <input type="checkbox" id="week${currentWeek}day${i}meal${j}" name="arranchamento" value="${i * meals.length + j + 1}">
                          <label for="week${currentWeek}day${i}meal${j}">${meal}</label>
                         </div>`
                ).join('') +
                '</div>';
        }
        cardsDiv.innerHTML += newContent;
        currentWeek++;
    }
</script>

</body>
</html>
