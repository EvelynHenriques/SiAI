package arranchamento.servlet;

import arranchamento.dao.ArranchamentoDAO;
import arranchamento.modelo.Arranchamento;
import arranchamento.service.ArrancharService;
import arranchamento.util.DateUtil;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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
        HttpSession session = request.getSession(false); // Recupera a sessão existente; não cria uma nova
        if (session != null && session.getAttribute("usuarioId") != null) {
            int usuarioId = (Integer) session.getAttribute("usuarioId");

            int refeicaoId = Integer.parseInt(request.getParameter("refeicaoId")); // Supõe que você passa o ID da refeição como parâmetro
            ArranchamentoDAO arranchamentoDAO = new ArranchamentoDAO();
            Arranchamento arranchamento = arranchamentoDAO.buscarArranchamentoPorUsuarioERefeicao(usuarioId, refeicaoId);

            // Continue com a lógica de processamento usando 'arranchamento'
        } else {
            request.getRequestDispatcher("arranchamento").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Recupera a sessão existente; não cria uma nova
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp"); // Redireciona para a página de login se não houver sessão ou se o usuárioId não estiver na sessão
            return;
        }

        int usuarioId = (Integer) session.getAttribute("usuarioLogado"); // Agora seguro para acessar


        System.out.println("AQUI");

        // Obtém todos os valores para o parâmetro "arranchamento"
        String[] arranchamentos = request.getParameterValues("arranchamento");
        String strDate = request.getParameter("lastDateDisplayed");  // Pega a string de data do formulário
        java.sql.Date sqlDate = DateUtil.convertStringToSqlDate(strDate);  // Converte para java.sql.Date

        // Listas para armazenar as datas e os índices das refeições
        List<String> datas = new ArrayList<>();
        List<Integer> indicesRefeicao = new ArrayList<>();

        // Processa cada valor de "arranchamento" para extrair as datas e os índices das refeições
        if (arranchamentos != null) {
            for (String arranchamento : arranchamentos) {
                // O formato esperado de cada arranchamento é "dd/MM/yyyy_i"
                String[] partes = arranchamento.split("_");
                if (partes.length == 2) {
                    datas.add(partes[0]);
                    indicesRefeicao.add(Integer.parseInt(partes[1])); // Converte o índice para Integer
                }
            }
        }
        ArrancharService arrancharService = new ArrancharService();
        arrancharService.receberArranchamento(datas, indicesRefeicao, usuarioId, sqlDate);

        // Configura a mensagem de sucesso e o redirecionamento
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\">");
        out.println("alert('Arranchamento enviado com sucesso');");
        out.println("window.location = 'menu.jsp';"); // Altere 'menu.jsp' pelo caminho correto do menu principal
        out.println("</script>");
    }

}
