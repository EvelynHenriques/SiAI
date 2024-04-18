package arranchamento.servlet;

import arranchamento.modelo.Usuario;
import arranchamento.dao.UsuarioDAO;
import arranchamento.service.AutenticacaoService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        AutenticacaoService autenticacaoService = new AutenticacaoService();
        Usuario usuario = autenticacaoService.autenticar(email, password);
        if (usuario != null) {
            System.out.println("usuario doPost nao eh nulo");
            // Se o usuário for encontrado e a senha estiver correta, crie uma sessão
            HttpSession session = request.getSession();
            System.out.println("Logado aqui");
            session.setAttribute("usuarioLogado", usuario.getId());
            response.sendRedirect("menu.jsp"); // Redirecionar para a página do menu
        } else {
            // Se a autenticação falhar, volte para a página de login com uma mensagem de erro
            request.setAttribute("erroLogin", "Email ou senha inválidos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
