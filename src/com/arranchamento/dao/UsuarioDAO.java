package arranchamento.dao;

import arranchamento.modelo.Usuario;
import arranchamento.util.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario buscarPorEmailESenha(String email, String senha) {
        Usuario usuario = null;
        String sql = "SELECT * FROM uhhdxfqg.public.usuarios WHERE email = ? AND senha = ?"; // Senha deve ser verificada com hash

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, senha); // Em produção, use hash aqui

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                // Não definir a senha por motivos de segurança
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratamento de exceção ou log
        }
        return usuario;
    }

    // Você pode adicionar aqui outros métodos, como criar, atualizar e deletar usuários
}
