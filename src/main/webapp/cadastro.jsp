<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br" >
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #1e352e;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color:white;
        }
        .container {
            background-color: #264d43;
            padding: 20px;
            width:20%;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .container h2 {
            text-align: center;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            font-weight: bold;
        }
        .form-group input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-group input[type="submit"] {
            background-color: #ffd700; /* amarelo */
            color: #1e352e;
            cursor: pointer;
            font-weight: bold;
            border-color: #ffd700;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Cadastro</h2>
    <form action="cadastro" method="post">
        <div class="form-group">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" required>
        </div>
        <div class="form-group">
            <label for="nome_guerra">Nome de Guerra:</label>
            <input type="text" id="nome_guerra" name="nome_guerra" required>
        </div>
        <div class="form-group">
            <label for="matricula">Matrícula:</label>
            <input type="text" id="matricula" name="matricula" required>
        </div>
        <div class="form-group">
            <label for="turma">Turma:</label>
            <input type="number" id="turma" name="turma" required>
        </div>
        <div class="form-group">
            <label for="pelotao">Pelotão:</label>
            <input type="number" id="pelotao" name="pelotao" required>
        </div>
        <div class="form-group">
            <label for="email">E-mail:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Senha:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <input type="submit" value="Cadastrar">
        </div>
    </form>
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
