<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro</title>
    <style>
        <%@include file="/css/cadastro.css"%>

        /* Estilize a barra de força da senha */
        #strengthBar {
            width: 100%;
            height: 10px;
            background-color: lightgray;
            border-radius: 5px;
            margin-top: 5px;
            overflow: hidden;
        }

        #strengthBarFill {
            height: 100%;
            width: 0;
        }
    </style>
</head>
<body>
<script>
    function desabilitarBotao() {
        // Desabilita o botão de envio assim que ele for clicado
        document.getElementById("botaoEnviar").disabled = true;
        document.getElementById("botaoEnviar").value = "Cadastrando...";

        // Desabilita todos os campos do formulário para evitar envios duplicados
        var form = document.getElementById("meuFormulario");
        var inputs = form.getElementsByTagName("input");
        for (var i = 0; i < inputs.length; i++) {
            inputs[i].disabled = true;
        }

        // Permite que o formulário seja enviado
        return true;
    }

    function verificarForcaSenha(senha) {
        var strengthMeter = document.getElementById("strengthMeter");
        var strengthText = document.getElementById("strength");
        var strengthBarFill = document.getElementById("strengthBarFill");

        // Verifica a força da senha
        var forca = 0;
        if (senha.length >= 8) {
            forca += 1;
        }
        if (senha.match(/[a-z]+/)) {
            forca += 1;
        }
        if (senha.match(/[A-Z]+/)) {
            forca += 1;
        }
        if (senha.match(/[0-9]+/)) {
            forca += 1;
        }
        if (senha.match(/[$-/:-?{-~!"^_`\[\]]/)) { // Caracteres especiais
            forca += 1;
        }

        // Atualiza a exibição da força da senha
        switch (forca) {
            case 0:
            case 1:
                strengthText.textContent = "Muito Fraca";
                strengthBarFill.style.backgroundColor = "red";
                strengthBarFill.style.width = "20%";
                break;
            case 2:
                strengthText.textContent = "Fraca";
                strengthBarFill.style.backgroundColor = "orange";
                strengthBarFill.style.width = "40%";
                break;
            case 3:
                strengthText.textContent = "Média";
                strengthBarFill.style.backgroundColor = "yellow";
                strengthBarFill.style.width = "60%";
                break;
            case 4:
                strengthText.textContent = "Forte";
                strengthBarFill.style.backgroundColor = "green";
                strengthBarFill.style.width = "80%";
                break;
            case 5:
                strengthText.textContent = "Muito Forte";
                strengthBarFill.style.backgroundColor = "darkgreen";
                strengthBarFill.style.width = "100%";
                break;
        }

        // Mostra o medidor de força
        strengthMeter.style.display = "block";
    }
</script>
<div class="container">
    <h2>Cadastro</h2>
    <form action="cadastro" method="post" id="meuFormulario">
        <div class="form-group">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" placeholder="Matheus Vanzan Pimentel De Oliveira" required>
        </div>
        <div class="form-group">
            <label for="nome_guerra">Nome de Guerra:</label>
            <input type="text" id="nome_guerra" name="nome_guerra" placeholder="Vanzan" required>
        </div>
        <div class="form-group">
            <label for="matricula">Matrícula:</label>
            <input type="text" id="matricula" name="matricula" placeholder="12040" required>
        </div>
        <div class="form-group">
            <label for="turma">Turma:</label>
            <input type="number" id="turma" name="turma" placeholder="16" required>
        </div>
        <div class="form-group">
            <label for="pelotao">Pelotão:</label>
            <input type="number" id="pelotao" name="pelotao" placeholder="1" required>
        </div>
        <div class="form-group">
            <label for="email">E-mail:</label>
            <input type="email" id="email" name="email" placeholder="vanzan.matheus@ime.eb.br" required>
        </div>
        <div class="form-group">
            <label for="password">Senha:</label>
            <input type="password" id="password" name="password" placeholder="Digite sua senha" required oninput="verificarForcaSenha(this.value)">
            <!-- Adicione o medidor de força da senha e a barra de preenchimento -->
            <div id="strengthMeter">
                <div id="strengthText">Força da senha: <span id="strength">Fraca</span></div>
                <div id="strengthBar">
                    <div id="strengthBarFill"></div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <input type="submit" id="botaoEnviar" value="Cadastrar">
        </div>
    </form>
</div>

<%
    String erroCadastro = (String) request.getAttribute("erroCadastro");
    if (erroCadastro != null) {
%>
<script>
    alert("<%= erroCadastro %>");
</script>
<%
    }
%>
</div>
</body>
</html>
