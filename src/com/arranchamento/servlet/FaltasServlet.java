package arranchamento.servlet;

import arranchamento.service.FaltasService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "FaltasServlet", value = "/faltas")
public class FaltasServlet extends HttpServlet {

    private FaltasService faltasService;

    @Override
    public void init() throws ServletException {
        super.init();
        faltasService = new FaltasService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getParameter("sessionId");
        boolean isAdmin = faltasService.checkAdmin(sessionId);
        System.out.println(isAdmin);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("{\"isAdmin\":" + isAdmin + "}");
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Adicionando prints de depuração para verificar os parâmetros recebidos
        String userIdStr = req.getParameter("user_id");
        String mealType = req.getParameter("meal_type");
        String date = req.getParameter("date");

        System.out.println("Debug: user_id = " + userIdStr);
        System.out.println("Debug: meal_type = " + mealType);
        System.out.println("Debug: date = " + date);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        if (userIdStr == null || mealType == null || date == null) {
            System.out.println("Debug: Missing parameters");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Missing parameters\"}");
            out.flush();
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            System.out.println("Debug: user_id parsed successfully = " + userId);
            boolean result = faltasService.sendMealInfo(userId, mealType, date);
            System.out.println("Debug: sendMealInfo result = " + result);
            out.print("{\"success\":" + result + "}");
            out.flush();
        } catch (NumberFormatException e) {
            System.out.println("Debug: Invalid user_id - " + userIdStr);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Invalid user_id\"}");
            out.flush();
        }
    }

}
