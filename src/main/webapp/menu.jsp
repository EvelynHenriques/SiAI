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
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <link href='https://fonts.googleapis.com/css?family=Allerta Stencil' rel='stylesheet'>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu</title>
    <style>
        <%@include file="/css/header.css"%>
        <%@include file="/css/menu.css"%>
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
    <div class="imagem">
        <img src="${pageContext.request.contextPath}/images/Captura%20de%20tela%202024-03-11%20083654.png"/>
    </div>

<%--    <h2>Top 10 Usuários com Mais Arranchamentos</h2>--%>
<%--    <c:if test="${not empty top10UsuariosNomes}">--%>
<%--        <p>Tamanho da lista de Usuários: ${fn:length(top10UsuariosNomes)}</p>--%>
<%--        <table>--%>
<%--            <thead>--%>
<%--            <tr>--%>
<%--                <th>Nome de Guerra</th>--%>
<%--                <th>Número de Ocorrências</th>--%>
<%--            </tr>--%>
<%--            </thead>--%>
<%--            <tbody>--%>
<%--            <c:forEach items="${top10UsuariosNomes}" var="nome" varStatus="i">--%>
<%--                <tr>--%>
<%--                    <td>${nome}</td>--%>
<%--                    <td>${top10UsuariosOcorrencias[i.index]}</td>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
<%--            </tbody>--%>
<%--        </table>--%>
<%--    </c:if>--%>
<%--    <c:if test="${empty top10UsuariosNomes}">--%>
<%--        <p>Nenhum usuário encontrado.</p>--%>
<%--    </c:if>--%>
</section>
</body>
</html>
