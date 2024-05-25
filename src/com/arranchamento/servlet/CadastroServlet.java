package arranchamento.servlet;

import arranchamento.modelo.Usuario;
import arranchamento.dao.UsuarioDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "CadastroServlet", value = "/cadastro")
public class CadastroServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = hashSenhaDIFERENTE(request.getParameter("password"));
        String nome = request.getParameter("nome");
        String nomeDeGuerra = request.getParameter("nome_guerra");
        int matricula = Integer.parseInt(request.getParameter("matricula"));
        int pelotao = Integer.parseInt(request.getParameter("pelotao"));
        int turma = Integer.parseInt(request.getParameter("turma"));

        Usuario usuario = new Usuario(0, nome, email, password, nomeDeGuerra, matricula, turma, pelotao);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if(usuarioDAO.buscarPorEmailEMatricula(email, matricula) && usuarioDAO.inserirUsuario(usuario)){
            System.out.println("Usuario cadastrado");
            //HttpSession session = request.getSession();
            request.setAttribute("cadastroConcluido", "Cadastro concluído com sucesso. Prossiga para o login");
            //response.sendRedirect("login.jsp"); // Redirecionar para a página do menu
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } else {
            System.out.println("Erro no cadastro");
            request.setAttribute("erroCadastro", "Erro ao cadastrar usuário. Verifique se os dados estão corretos.");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
        }
    }

    private String hashSenhaDIFERENTE(String senha) {
        if(senha == null){
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
