package arranchamento.servlet;

import arranchamento.modelo.Usuario;
import arranchamento.dao.UsuarioDAO;
import arranchamento.util.ExportadorXLSX;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.net.http.HttpHeaders;

@WebServlet(name = "ExportServlet", value = "/export")
public class ExportServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dataInicio = request.getParameter("dataInicio");
        String dataFim = request.getParameter("dataFim");
        int turma = Integer.parseInt(request.getParameter("turma"));
        int pelotao = Integer.parseInt(request.getParameter("pelotao"));

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=arranchamento.xlsx");

        try (OutputStream out = response.getOutputStream()) {
            if (ExportadorXLSX.exportarFormatado(out, turma, pelotao, dataInicio, dataFim)) {
                System.out.println("ARRANCHAMENTO EXPORTADO");
            } else {
                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                writer.println("<script type=\"text/javascript\">");
                writer.println("alert('Erro ao exportar arquivo');");
                writer.println("window.location = 'menu.jsp';");
                writer.println("</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();
            writer.println("<script type=\"text/javascript\">");
            writer.println("alert('Erro ao exportar arquivo');");
            writer.println("window.location = 'menu.jsp';");
            writer.println("</script>");
        }

//        if(ExportadorXLSX.exportarFormatado(System.getProperty("user.home") + "/Downloads/" + "arranchamento", turma, pelotao, dataInicio, dataFim)){
//            System.out.println("ARRANCHAMENTO EXPORTADO");
//            response.setContentType("text/html");
//            PrintWriter out = response.getWriter();
//            out.println("<script type=\"text/javascript\">");
//            out.println("alert('Arquivo exportado com sucesso');");
//            out.println("window.location = 'menu.jsp';");
//            out.println("</script>");
//
//        } else {
//            System.out.println("ERRO NO EXPORT");
//            response.setContentType("text/html");
//            PrintWriter out = response.getWriter();
//            out.println("<script type=\"text/javascript\">");
//            out.println("alert('Erro ao exportar arquivo');");
//            out.println("window.location = 'menu.jsp';");
//            out.println("</script>");
//        }
    }
}
