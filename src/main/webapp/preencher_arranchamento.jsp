<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            overflow: hidden;
            height: 100%;
        }
        header {
            background-color: #1e352e;
            color: #fff;
            padding: 10px 0;
            text-align: center;
        }
        nav {
            background-color: #488273;
            padding: 10px 0;
            text-align: center;
        }
        nav a {
            color: #fff;
            text-decoration: none;
            margin: 0 20px;
        }
        nav a:hover {
            text-decoration: underline;
            color: rgba(0, 0, 0, 0.5);
        }
        section {
            padding: 20px;
        }

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

        .day {
            margin-top: 30px;
            border: 2px solid #488273;
            border-radius: 5px;
            padding: 10px;
            display:flex
        }
        h2 {
            color: #488273;
        }
        .meals {
            margin-top: 10px;
        }
        .meals h3 {
            margin-bottom: 5px;
        }
        .meals button {
            display: block;
            margin-top: 5px;
            padding: 5px 10px;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            background-color: #488273;
            color: #fff;
        }
        .meals button:hover {
            background-color: #1e352e;
        }
    </style>
</head>
<body>
<header>
    <h1>Menu</h1>
</header>
<nav>
    <a href="preencher_arranchamento.jsp">Preencher Arranchamento</a>
    <a href="consultar_arranchamento.jsp">Consultar Arranchamento</a>
    <a href="extrair_arranchamento.jsp">Extrair Arranchamento</a>
</nav>
<section>
    <div class="imagem">
        <img src="e.jpg">
    </div>
    <!-- Repete este bloco para cada dia da semana -->
    <div class="day">
        <h2>Segunda-feira (15/04)</h2>
        <div class="meals">
            <h3>Café da Manhã</h3><input type="radio">
            <h3>Almoço</h3>
            <input type="radio">
            <h3>Jantar</h3>
            <input type="radio">
            <h3>Ceia</h3>
            <input type="radio">
        </div>
    </div>
    <!-- Repetir para os outros dias da semana -->
    <!-- Lembre-se de ajustar as datas e dias da semana conforme necessário -->
</section>
</body>
</html>
