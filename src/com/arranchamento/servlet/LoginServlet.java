package arranchamento.servlet;

import arranchamento.modelo.Usuario;
import arranchamento.service.AutenticacaoService;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean fromApp = request.getParameter("fromApp") != null && request.getParameter("fromApp").equals("true");

        AutenticacaoService autenticacaoService = new AutenticacaoService();
        Usuario usuario = autenticacaoService.autenticar(email, password);

        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuario.getId());

            if (fromApp) {
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("usuario", usuario);
                responseData.put("userId", usuario.getId());
                responseData.put("sessionId", session.getId());
                Gson gson = new Gson();

                String jsonResponse = gson.toJson(responseData);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print(jsonResponse);
                out.flush();
            } else {
                response.sendRedirect("menu.jsp");
            }
        } else {
            if (fromApp) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print("{\"erro\": \"Email ou senha inválidos.\"}");
                out.flush();
            } else {
                request.setAttribute("erroLogin", "Email ou senha inválidos.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
}
