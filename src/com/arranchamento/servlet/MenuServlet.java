package arranchamento.web;

import arranchamento.service.MenuService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MenuService menuService = new MenuService();

        List<String> top10UsuariosNomes = menuService.obterTop10UsuariosNomes();
        List<Integer> top10UsuariosOcorrencias = menuService.obterTop10UsuariosOcorrencias();

        request.setAttribute("top10UsuariosNomes", top10UsuariosNomes);
        request.setAttribute("top10UsuariosOcorrencias", top10UsuariosOcorrencias);

        request.getRequestDispatcher("/menu.jsp").forward(request, response);
    }
}
