<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="arranchamento.service.ArrancharService" %>
<%@ page import="arranchamento.modelo.Arranchamento" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.sql.Date" %>

<%

    // Verificando se o usuário está autenticado
    session = request.getSession(false);
    Integer usuarioId = (Integer) session.getAttribute("usuarioLogado");
    if (usuarioId == null) {
        // Usuário não autenticado, redireciona para a página de login
        System.out.println("NAO LOGADO");
        response.sendRedirect("login.jsp");
        return;
    }

    ArrancharService arrancharService = new ArrancharService();
    List<Arranchamento> arranchamentos = arrancharService.buscarArranchamentosPorUsuario(usuarioId);
    Map<String, Boolean> arranchadosMap = new HashMap<>();
    System.out.println("Arranchamentos encontrados: " + arranchamentos.size());

    SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy"); // Formato de saída

    for (Arranchamento arr : arranchamentos) {
        Date date = arr.getData();
        String formattedDate = sdfOutput.format(date);

        // Obtendo o ID do tipo de refeição usando o método que adicionamos ao serviço
        int tipoRefeicaoId = arrancharService.getRefeicaoIntTipo(arr.getTipoRefeicao());

        // Usando o ID do tipo de refeição no lugar da string
        String key = formattedDate + "_" + tipoRefeicaoId;

        arranchadosMap.put(key, true);
        System.out.println("Key added: " + key); // Debug #2
    }
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Agenda de Refeições</title>
        <style>
            <%@include file="/css/header.css"%>
            <%@include file="/css/arranchamento.css"%>
        </style>
    <script>
        var nextMonday = new Date(); // Pega a data atual
        nextMonday.setDate(nextMonday.getDate() + ((1 + 7 - nextMonday.getDay()) % 7 || 7)); // Ajusta para a próxima segunda-feira
        nextMonday.setDate(nextMonday.getDate() + 21); // Começa daqui a duas semanas

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

            if (month.length < 2)
                month = '0' + month;
            if (day.length < 2)
                day = '0' + day;

            return [day, month, year].join('/'); // Formato brasileiro dd/mm/yyyy
        }
        var checkboxStates = {}; // Armazenar os estados das checkboxes

        function saveCheckboxStates() {
            var checkboxes = document.querySelectorAll('input[type="checkbox"]');
            checkboxes.forEach(function(checkbox) {
                checkboxStates[checkbox.id] = checkbox.checked;
            });
        }

        function restoreCheckboxStates() {
            var checkboxes = document.querySelectorAll('input[type="checkbox"]');
            checkboxes.forEach(function(checkbox) {
                if (checkbox.id in checkboxStates) {
                    checkbox.checked = checkboxStates[checkbox.id];
                }
            });
        }

        function loadMoreWeeks(arranchadosMap) {
            saveCheckboxStates();
            var daysOfWeek = ["Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"];
            var meals = ["Café", "Almoço", "Janta", "Ceia"];
            var cardsDiv = document.querySelector('.cards');
            var newContent = '';
            var lastDateFormatted = '';
            var weekCount = document.querySelectorAll('.card').length / 7; // Calculate how many weeks have already been loaded
            var delay = 100; // Initial delay for the first card
            for (let i = 0; i < 7; i++) {
                var currentDate = addDays(nextMonday, i);
                var formattedDate = formatDate(currentDate);
                var cardId = "card" + (weekCount * 7 + i);  // Generate a unique ID for each new card
                if (i == 6) { lastDateFormatted = formattedDate; }
                newContent += '<div class="card" id="' + cardId + '" style="opacity: 0; transition-delay: ' + delay + 'ms;">'; // Set initial opacity to 0 and transition delay
                newContent += '<div class="day">' + daysOfWeek[currentDate.getDay() === 0 ? 6 : currentDate.getDay() - 1] + ' (' + formattedDate + ')</div>';
                for (let j = 1; j <= meals.length; j++) {
                    var checkboxValue = formattedDate + "_" + j;
                    var isChecked = arranchadosMap.hasOwnProperty(checkboxValue) ? arranchadosMap[checkboxValue] : false;
                    newContent += '<div class="meal">';
                    newContent += '<input type="checkbox" id="' + formattedDate.replace(/\//g, '_') + '_' + j + '" name="arranchamento" value="' + formattedDate + '_' + j + '"' + (isChecked ? ' checked' : '') + '>';
                    newContent += '<label for="' + formattedDate.replace(/\//g, '_') + '_' + j + '">' + meals[j-1] + '</label>';
                    newContent += '</div>';
                }
                newContent += '</div>';
                delay += 100; // Increment the delay for the next card
            }
            cardsDiv.innerHTML += newContent;
            nextMonday = addDays(nextMonday, 7); // Prepares for the loading of the next week

            setTimeout(function() {
                document.querySelectorAll('.card').forEach(function(card) {
                    card.style.opacity = 1; // Change opacity to 1 after a short delay
                });
            }, 50); // Wait for a short delay before changing opacity
            document.getElementById('lastDateDisplayed').value = lastDateFormatted;
            restoreCheckboxStates();
        }



        function extendAll() {
            console.log('Iniciando a extensão do arranchamento...');

            // Obtemos todos os cards.
            var cards = document.querySelectorAll('.card');

            // Assumimos que os primeiros 7 cards representam a primeira semana.
            var firstWeekCheckboxes = [];
            for (let i = 0; i < 7; i++) {
                firstWeekCheckboxes.push(...cards[i].querySelectorAll('input[type="checkbox"]'));
            }

            console.log('Checkboxes da primeira semana coletadas:', firstWeekCheckboxes);

            // Obtemos os estados das checkboxes da primeira semana.
            var baseStates = firstWeekCheckboxes.map(checkbox => checkbox.checked);

            // Iteramos sobre as semanas subsequentes.
            for (let i = 7; i < cards.length; i++) {
                let cardCheckboxes = cards[i].querySelectorAll('input[type="checkbox"]');
                // Iteramos sobre as checkboxes de cada card e aplicamos os estados da primeira semana.
                cardCheckboxes.forEach((checkbox, index) => {
                    checkbox.checked = baseStates[index % 4]; // Agora utilizamos 4, pois há 4 refeições em cada dia.
                    console.log(`Estado da checkbox ${checkbox.id} ajustado para: ${baseStates[index % 4]}`);
                });
            }
        }



        // Função auxiliar para formatar datas em DD/MM/YYYY
        function formatDate(date) {
            var d = new Date(date),
                day = ('0' + d.getDate()).slice(-2),
                month = ('0' + (d.getMonth() + 1)).slice(-2),
                year = d.getFullYear();
            return [day, month, year].join('/');
        }

    </script>
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
<form class="cards" action="arranchamento" method="post" id="arranchamentoForm" onsubmit="return showLoader();">
    <input type="hidden" id="lastDateDisplayed" name="lastDateDisplayed" value="" />
    <%
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 14); // Move calendar to two weeks from now
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Set to start of the next coming Monday

        String[] daysOfWeek = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};
        String[] meals = {"Café", "Almoço", "Janta", "Ceia"};
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Use the output format

        for (int i = 0; i < 7; i++) {
            String formattedDate = sdf.format(cal.getTime());
    %>
    <div class="card loaded" id="card<%= i %>"> <!-- Adicionando a classe 'loaded' aqui -->
        <div class="day"><%= daysOfWeek[i] %> (<%= formattedDate %>)</div>
        <% for (int j = 1; j <= meals.length; j++) { %>
        <div class="meal">
            <input type="checkbox" id="<%= formattedDate %>_<%= j %>" name="arranchamento" value="<%= formattedDate %>_<%= j %>"
                <%= arranchadosMap.containsKey(formattedDate + "_" + j) && arranchadosMap.get(formattedDate + "_" + j) ? "checked" : "" %>>
            <label for="<%= formattedDate %>_<%= j %>"><%= meals[j-1] %></label>
        </div>
        <% } %>
    </div>
    <% cal.add(Calendar.DATE, 1); %>
    <% } %>
</form>




<script>
    var arranchadosMap = <%= convertMapToJson(arranchadosMap) %>; // Convert Java map to JavaScript object

    var daysOfWeek = ["Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"];
    var meals = ["Café", "Almoço", "Janta", "Ceia"];

    for (var i = 0; i < 7; i++) {
        var currentDate = new Date();
        currentDate.setDate(currentDate.getDate() + ((1 + 7 - currentDate.getDay()) % 7 || 7)); // Adjust to next Monday
        currentDate.setDate(currentDate.getDate() + 14 + i); // Start from two weeks from now

        var formattedDate = (currentDate.getDate() < 10 ? '0' : '') + currentDate.getDate() + "/" + (currentDate.getMonth() < 9 ? '0' : '') + (currentDate.getMonth() + 1) + "/" + currentDate.getFullYear();

        var card = document.getElementById('card' + i);
        var cardContent = '<div class="day">' + daysOfWeek[currentDate.getDay() === 0 ? 6 : currentDate.getDay() - 1] + ' (' + formattedDate + ')</div>';

        for (var j = 1; j <= meals.length; j++) {
            var checkboxValue = formattedDate + "_" + j;
            var isChecked = arranchadosMap.hasOwnProperty(checkboxValue) && arranchadosMap[checkboxValue];

            cardContent += '<div class="meal">' +
                '<input type="checkbox" id="day' + i + 'meal' + j + '" name="arranchamento" value="' + checkboxValue + '" ' + (isChecked ? 'checked' : '') + '>' +
                '<label for="day' + i + 'meal' + j + '">' + meals[j - 1] + '</label>' +
                '</div>';
        }

        card.innerHTML = cardContent;
        var lastCard = document.getElementById('card6');
        var lastDate = lastCard.querySelector('.day').textContent.match(/\((.*?)\)/)[1];
        document.getElementById('lastDateDisplayed').value = lastDate; // Define a última data exibida no campo oculto
        function showLoader() {
            document.getElementById("loader").style.display = "block"; // Mostra o loader
            document.getElementById("white-ball").style.display = "block"; // Mostra a bola branca
            document.getElementById("submitButton").disabled = true; // Desabilita o botão de submit
            return true;
        }

        window.onload = function() {
            document.getElementById("loader").style.display = "none";
            document.getElementById("white-ball").style.display = "none"; // Esconde a bola branca
        };

        function hideLoader() {
            document.getElementById("loader").style.display = "none"; // Oculta o loader
            document.getElementById("white-ball").style.display = "none"; // Oculta o loader
            document.getElementById("submitButton").disabled = false; // Habilita o botão de submit
            return true;
        }
    }
</script>

<div class="button-container">
    <button type="button" id="exibirButton" onclick="loadMoreWeeks(arranchadosMap);">Exibir mais</button>
    <button type="submit" id="submitButton" form="arranchamentoForm" >Enviar</button>
</div>
<div id="loader-container">
    <div id="loader"></div>
    <div id="white-ball"></div>
</div>
</body>
</html>
<%!
    private String convertMapToJson(Map<String, Boolean> arranchadosMap) {
        StringBuilder json = new StringBuilder("{");
        for (Map.Entry<String, Boolean> entry : arranchadosMap.entrySet()) {
            String key = entry.getKey();
            boolean value = entry.getValue();
            json.append("\"").append(key).append("\":").append(value).append(",");
        }
        if (!arranchadosMap.isEmpty()) {
            json.deleteCharAt(json.length() - 1); // Remove a vírgula extra no final
        }
        json.append("}");
        return json.toString();
    }
%>
