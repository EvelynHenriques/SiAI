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
            // Se o usuário for encontrado e a senha estiver correta, crie uma sessão
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuario);
            response.sendRedirect("menu.jsp"); // Redirecionar para a página do menu
        } else {
            // Se a autenticação falhar, volte para a página de login com uma mensagem de erro
            request.setAttribute("erroLogin", "Email ou senha inválidos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
