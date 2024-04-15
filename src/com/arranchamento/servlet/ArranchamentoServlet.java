package arranchamento.servlet;

import arranchamento.service.ArrancharService;
import arranchamento.modelo.Usuario;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ArranchamentoServlet", urlPatterns = {"/arranchamento"})
public class ArranchamentoServlet extends HttpServlet {

    private ArrancharService arrancharService;

    @Override
    public void init() {
        arrancharService = new ArrancharService(); // Supõe-se que você tem essa classe de serviço
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // A implementação dependerá de como você deseja processar e exibir a funcionalidade de arranchamento.
        // Normalmente você recuperaria informações da sessão e mostraria a página ou formulário apropriado.

        // Por exemplo, mostrar um formulário para um novo arranchamento:
        request.getRequestDispatcher("/WEB-INF/views/formArranchar.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Aqui você trataria o envio do formulário de arranchamento.
        // Isto incluiria recuperar os dados do formulário, validar, e usar o ArrancharService para persistir os dados.
        System.out.println("AQUI");
        // Exemplo de como você poderia recuperar dados do formulário:
        String tipoRefeicao = request.getParameter("tipoRefeicao");
        // Mais lógica para processar e armazenar o arranchamento
        // Após processar o arranchamento, você pode redirecionar para uma página de confirmação ou novamente para o menu:
        response.sendRedirect("menu");
    }
}
