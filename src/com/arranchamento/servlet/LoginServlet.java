package arranchamento.servlet;

import arranchamento.modelo.Usuario;
import arranchamento.service.AutenticacaoService;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        AutenticacaoService autenticacaoService = new AutenticacaoService();
        Usuario usuario = autenticacaoService.autenticar(email, password);
        if (usuario != null) {
            System.out.println("usuario doPost nao eh nulo");
            HttpSession session = request.getSession();
            System.out.println("Logado aqui");
            session.setAttribute("usuarioLogado", usuario.getId());

            // Converter objeto para JSON e logar
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(usuario);
            System.out.println("JSON Response: " + jsonResponse);

            // Enviar resposta JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonResponse);
            out.flush();
        } else {
            request.setAttribute("erroLogin", "Email ou senha inválidos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}