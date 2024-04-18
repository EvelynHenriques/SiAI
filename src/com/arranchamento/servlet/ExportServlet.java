package arranchamento.servlet;

import arranchamento.modelo.Usuario;
import arranchamento.dao.UsuarioDAO;
import arranchamento.util.ExportadorXLSX;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ExportServlet", value = "/export")
public class ExportServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dataInicio = request.getParameter("dataInicio");
        String dataFim = request.getParameter("dataFim");
        int turma = Integer.parseInt(request.getParameter("turma"));
        int pelotao = Integer.parseInt(request.getParameter("pelotao"));

        if(ExportadorXLSX.exportarFormatado(System.getProperty("user.home") + "/Downloads/" + "arranchamento.xlsx", turma, pelotao, dataInicio, dataFim)){
            System.out.println("ARRANCHAMENTO EXPORTADO");
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Arquivo exportado com sucesso');");
            out.println("window.location = 'menu.jsp';"); // Altere 'menu.jsp' pelo caminho correto do menu principal
            out.println("</script>");

        } else {
            System.out.println("ERRO NO EXPORT");
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Erro ao exportar arquivo');");
            out.println("window.location = 'menu.jsp';"); // Altere 'menu.jsp' pelo caminho correto do menu principal
            out.println("</script>");
        }
    }
}
