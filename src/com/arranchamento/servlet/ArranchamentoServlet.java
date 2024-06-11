package arranchamento.servlet;

import arranchamento.modelo.Arranchamento;
import arranchamento.service.ArrancharService;
import arranchamento.util.DateUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ArranchamentoServlet", urlPatterns = {"/arranchamento"})
public class ArranchamentoServlet extends HttpServlet {

    private ArrancharService arrancharService;

    @Override
    public void init() {
        arrancharService = new ArrancharService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String fromApp = request.getParameter("fromApp");

        if (session != null && session.getAttribute("usuarioLogado") != null) {
            int usuarioId = (Integer) session.getAttribute("usuarioLogado");

            // Adicionando logs para depuração
            System.out.println("Usuário logado: " + usuarioId);

            try {
                // Lógica para buscar arranchamentos e retornar como JSON
                List<Arranchamento> arranchamentos = arrancharService.buscarArranchamentosPorUsuario(usuarioId);

                // Logando a quantidade de arranchamentos encontrados
                System.out.println("Arranchamentos encontrados: " + arranchamentos.size());

                String jsonResponse = arranchamentosToJson(arranchamentos);
                System.out.println("JSON de resposta: " + jsonResponse);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print(jsonResponse);
                out.flush();
            } catch (Exception e) {
                // Logando qualquer exceção que ocorra
                System.err.println("Erro ao buscar arranchamentos: " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar arranchamentos");
            }
        } else {
            if ("true".equals(fromApp)) {
                // Logando quando o usuário não está logado mas o fromApp está presente
                System.out.println("Requisição do app recebida. Parâmetro fromApp presente.");
                try {
                    // Lógica para buscar arranchamentos e retornar como JSON
                    List<Arranchamento> arranchamentos = new ArrayList<>(); // Considerar como vazio se não autenticado

                    String jsonResponse = arranchamentosToJson(arranchamentos);
                    System.out.println("JSON de resposta para app: " + jsonResponse);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print(jsonResponse);
                    out.flush();
                } catch (Exception e) {
                    // Logando qualquer exceção que ocorra
                    System.err.println("Erro ao buscar arranchamentos para app: " + e.getMessage());
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar arranchamentos");
                }
            } else {
                // Logando quando o usuário não está logado
                System.out.println("Usuário não logado, redirecionando para login.jsp");
                response.sendRedirect("login.jsp");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int usuarioId = (Integer) session.getAttribute("usuarioLogado");

        boolean fromApp = request.getParameter("fromApp") != null && request.getParameter("fromApp").equals("true");

        // Obtém todos os valores para o parâmetro "arranchamento"
        String[] arranchamentos = request.getParameterValues("arranchamento[]");
        String strDate = request.getParameter("lastDateDisplayed");  // Pega a string de data do formulário

        // Adiciona log para verificar a data recebida
        System.out.println("Data recebida: " + strDate);

        // Certifique-se de que o formato da data está correto
        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date sqlDate = null;
        try {
            java.util.Date utilDate = fromUser.parse(strDate);
            String formattedDate = myFormat.format(utilDate);
            sqlDate = java.sql.Date.valueOf(formattedDate);
        } catch (ParseException e) {
            System.err.println("Erro ao converter a data: " + e.getMessage());
        }

        // Verifica se a data foi convertida corretamente
        System.out.println("Data convertida para SQL: " + sqlDate);

        // Adiciona log para verificar os arranchamentos recebidos
        if (arranchamentos != null) {
            for (String arr : arranchamentos) {
                System.out.println("Arranchamento recebido: " + arr);
            }
        }

        // Listas para armazenar as datas e os índices das refeições
        List<String> datas = new ArrayList<>();
        List<Integer> indicesRefeicao = new ArrayList<>();

        // Processa cada valor de "arranchamento" para extrair as datas e os índices das refeições
        if (arranchamentos != null) {
            for (String arranchamento : arranchamentos) {
                // O formato esperado de cada arranchamento é "yyyy-MM-dd_i"
                String[] partes = arranchamento.split("_");
                if (partes.length == 2) {
                    datas.add(partes[0]);
                    try {
                        indicesRefeicao.add(Integer.parseInt(partes[1])); // Converte o índice para Integer
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter índice de refeição: " + partes[1]);
                    }
                }
            }
        }

        // Imprime os parâmetros do request
        System.out.println("Parâmetros recebidos:");
        request.getParameterMap().forEach((key, values) -> {
            System.out.println(key + " = " + String.join(", ", values));
        });

        ArrancharService arrancharService = new ArrancharService();
        arrancharService.receberArranchamento(datas, indicesRefeicao, usuarioId, sqlDate);

        if (fromApp) {
            // Configura a resposta JSON para o aplicativo
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("{\"status\":\"success\"}");
            out.flush();
        } else {
            // Configura a mensagem de sucesso e o redirecionamento para a web
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Arranchamento enviado com sucesso');");
            out.println("window.location = 'menu.jsp';");
            out.println("</script>");
        }
    }


    private String arranchamentosToJson(List<Arranchamento> arranchamentos) {
        StringBuilder json = new StringBuilder("[");
        for (Arranchamento arranchamento : arranchamentos) {
            json.append("{");
            json.append("\"data\":\"").append(arranchamento.getData().toString()).append("\",");
            json.append("\"refeicao\":").append(arranchamento.getTipoRefeicao());
            json.append("},");
        }
        if (!arranchamentos.isEmpty()) {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("]");
        return json.toString();
    }
}
