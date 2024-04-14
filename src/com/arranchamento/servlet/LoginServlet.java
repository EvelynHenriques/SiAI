package arranchamento.servlet;

import arranchamento.modelo.Usuario;
import arranchamento.dao.UsuarioDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.buscarPorEmailESenha(email, password);

        if (usuario != null) {
            // Sucesso no login
            request.getSession().setAttribute("usuarioLogado", usuario);
            response.sendRedirect("paginaInterna.jsp"); // Redireciona para página interna
        } else {
            // Falha no login
            request.setAttribute("mensagemErro", "Credenciais inválidas!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
