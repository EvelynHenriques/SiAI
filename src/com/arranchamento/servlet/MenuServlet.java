package arranchamento.servlet;

import arranchamento.service.MenuService;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "menuServlet", value = "/menuServlet")
public class MenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            if (request.getParameter("fromApp") != null && request.getParameter("fromApp").equals("true")) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print("{\"erro\": \"Usuário não autenticado.\"}");
                out.flush();
            } else {
                response.sendRedirect("login.jsp");
            }
            return;
        }

        boolean fromApp = request.getParameter("fromApp") != null && request.getParameter("fromApp").equals("true");

        MenuService menuService = new MenuService();
        List<String> topUsuariosNomes = menuService.obterTop10UsuariosNomes();
        List<Integer> topUsuariosOcorrencias = menuService.obterTop10UsuariosOcorrencias();

        if (fromApp) {
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("topUsuariosNomes", topUsuariosNomes);
            responseData.put("topUsuariosOcorrencias", topUsuariosOcorrencias);

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(responseData);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonResponse);
            out.flush();
        } else {
            request.setAttribute("topUsuariosNomes", topUsuariosNomes);
            request.setAttribute("topUsuariosOcorrencias", topUsuariosOcorrencias);
            RequestDispatcher dispatcher = request.getRequestDispatcher("menu.jsp");
            dispatcher.forward(request, response);
        }
    }
}
