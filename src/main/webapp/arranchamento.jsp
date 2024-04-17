<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>

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
            font-family: 'Arial', sans-serif;
        }
        .menu{
            font-size: 30px;
            color:white;
            text-decoration: none;
            font-family: 'Arial', sans-serif;
        }
        .card {
            border: 1px solid #ccc;
            border-radius: 5px;

            margin: 10px;
            float: left;
        }
        .day {
            font-weight: bold;
            margin-bottom: 5px;
            text-align: center; /* Alinha o texto ao centro */
            background-color: #3d7272; /* Define a cor de fundo */
            padding: 5px;
            word-wrap: break-word;
        }
        .meal {
            margin-bottom: 5px;

        }
        .cards {
            display: grid;
            grid-template-columns: repeat(7, 1fr);
            justify-content: center;
            align-items: center;
        }
        input[type="checkbox"] {
            /* Tornar a checkbox invisível */
            opacity: 0;
            position: absolute;
            vertical-align: middle;
        }

        input[type="checkbox"] + label {
            /* Estilizar o rótulo que acompanha a checkbox */
            display: inline-block;
            vertical-align: middle; /* Alinhar verticalmente */
            cursor: pointer; /* Mudar cursor para indicar clicabilidade */
            position: relative; /* Permitir posicionamento absoluto do pseudo-elemento */
            font-size: 18px;
            padding-left: 10px;
        }

        input[type="checkbox"] + label::before {
            /* Estilizar a caixa de seleção */
            content: '';
            display: inline-block;
            width: 18px;
            height: 18px;
            background-color: transparent;
            border: 1px solid #ccc; /* Manter a borda cinza */
            border-radius: 5px; /* Arredondar as bordas */
            margin-right: 5px; /* Adicionar espaçamento à direita */
        }

        input[type="checkbox"]:checked + label::before {
            /* Alterar a cor de fundo quando selecionada */
            background-color: #264d43; /* Verde quando selecionada */
        }
    </style>
    <script>
        var nextMonday = new Date(); // Pega a data atual
        nextMonday.setDate(nextMonday.getDate() + ((1 + 7 - nextMonday.getDay()) % 7 || 7)); // Próxima segunda-feira
        nextMonday.setDate(nextMonday.getDate() + 21); // Adiciona 7 dias para iniciar exatamente duas semanas à frente


        function addDays(date, days) {
            var result = new Date(date);
            result.setDate(result.getDate() + days);
            return result;
        }

        function formatDate(date) {
            var d = new Date(date),
                month = '' + (d.getMonth() + 1),
                day = '' + d.getDate(),
                year = d.getFullYear();

            if (month.length < 2) month = '0' + month;
            if (day.length < 2) day = '0' + day;

            return [day, month, year].join('/'); // Alterado para o formato brasileiro
        }

        function loadMoreWeeks() {
            var daysOfWeek = ["Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"];
            var meals = ["Café", "Almoço", "Janta", "Ceia"];
            var cardsDiv = document.querySelector('.cards');
            var newContent = '';

            if (new Date().getDay() !== 1) { // Se hoje não é segunda-feira
                nextMonday.setDate(nextMonday.getDate() - (nextMonday.getDay() - 1)); // Ajustar para a última segunda-feira
            }

            for (let i = 0; i < 7; i++) { // Sempre começar na segunda-feira
                var currentDate = addDays(nextMonday, i);
                var formattedDate = formatDate(currentDate); // Formatar a data
                newContent += '<div class="card">' +
                    '<div class="day">' + daysOfWeek[i] + '<br>(' + formattedDate + ')</div>';
                for (let j = 0; j < meals.length; j++) {
                    newContent += '<div class="meal">' +
                        '<input type="checkbox" id="' + formattedDate + '_' + (j+1) + '" name="arranchamento" value="' + formattedDate + '_' + (j+1) + '">' +
                        '<label for="' + formattedDate + '_' + (j+1) + '">' + meals[j] + '</label>' +
                        '</div>';
                }
                newContent += '</div>';
            }

            cardsDiv.insertAdjacentHTML('beforeend', newContent);
            nextMonday = addDays(nextMonday, 7); // Preparar para a próxima semana
        }
    </script>
</head>
<body>
<header>
    <a class="menu" href="menu.jsp">Menu</a>
</header>
<nav>
    <a href="arranchamento.jsp">Preencher Arranchamento</a>
    <a href="consultar_arranchamento.jsp">Consultar Arranchamento</a>
    <a href="extrair_arranchamento.jsp">Extrair Arranchamento</a>
</nav>
<form class="cards" action="arranchamento" method="post" id="arranchamentoForm">
    <%
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Formato brasileiro

        // Ajustar para a segunda-feira de duas semanas à frente
        cal.add(Calendar.WEEK_OF_YEAR, 2);
        int currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int daysUntilNextMonday = Calendar.MONDAY - currentDayOfWeek;
        if (daysUntilNextMonday <= 0) { // Se já é segunda-feira ou domingo
            daysUntilNextMonday += 7; // Adicionar uma semana
        }
        cal.add(Calendar.DAY_OF_MONTH, daysUntilNextMonday);

        String[] daysOfWeek = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};
        String[] meals = {"Café", "Almoço", "Janta", "Ceia"};
    %>
    <% for (int i = 0; i < 7; i++) {
        String formattedDate = sdf.format(cal.getTime()); // Formatar data para padrão brasileiro
    %>
    <div class="card">
        <div class="day"><%= daysOfWeek[i] %><br>(<%= formattedDate %>)</div>
        <% for (int j = 0; j < meals.length; j++) { %>
        <div class="tipo">
            <input type="checkbox" id="day<%= i %>meal<%= j %>" name="arranchamento" value="<%= formattedDate %>_<%= j+1 %>">
            <label for="day<%= i %>meal<%= j %>"><%= meals[j] %></label>
        </div>
        <% } %>
    </div>
    <% cal.add(Calendar.DATE, 1); %>
    <% } %>
</form>
<div class="button-container">
    <button type="button" onclick="loadMoreWeeks()">Exibir mais</button>
    <button type="submit" form="arranchamentoForm">Enviar</button>
</div>
</body>
</html>
