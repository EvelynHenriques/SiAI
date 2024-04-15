<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>SiAI</title>
</head>
<meta charset="UTF-8">
<title>Login</title>
<style>
    body {
        background-color: #1e352e; /* verde escuro */
        color: #fff; /* texto branco */
        font-family: Arial, sans-serif;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }

    form {
        background-color: #264d43; /* verde médio */
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        max-width: 300px;
        width: 100%;
    }

    input[type="text"],
    input[type="password"] {
        width: 100%;
        padding: 10px;
        margin: 8px 0;
        box-sizing: border-box;
        border: none;
        border-radius: 5px;
    }

    input[type="submit"] {
        background-color: #ffd700; /* amarelo */
        color: #1e352e; /* verde escuro */
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 16px;
    }

    input[type="submit"]:hover {
        background-color: #ffea00; /* amarelo claro */
    }
</style>
</head>
<body>

<form action="login" method="post">
    <h2>SiAI</h2>
    <label for="email">Email:</label><br>
    <input type="text" id="email" name="email"><br>
    <label for="password">Senha:</label><br>
    <input type="password" id="password" name="password"><br><br>
    <input type="submit" value="Entrar">
</form>
</body>
<br/>
</html>
