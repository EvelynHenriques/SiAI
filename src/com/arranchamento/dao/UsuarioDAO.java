package arranchamento.dao;

import arranchamento.modelo.Usuario;
import arranchamento.util.ConexaoBanco;

import java.sql.*;
import java.util.*;

public class UsuarioDAO {

    public List<Usuario> buscarTodosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM uhhdxfqg.public.usuarios";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));  
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();  
        }
        return usuarios;
    }

    public Usuario buscarPorEmailESenha(String email, String senha) {
        Usuario usuario = null;
        String sql = "SELECT * FROM uhhdxfqg.public.usuarios WHERE email = ? AND senha = ?";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, senha);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public boolean buscarPorEmailEMatricula(String email, int matricula) {
        Usuario usuario = null;
        String sql = "SELECT * FROM uhhdxfqg.public.usuarios WHERE email = ? OR matricula = ?"; // Senha deve ser verificada com hash

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setInt(2, matricula);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                return false;
                // Não definir a senha por motivos de segurança
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean inserirUsuario(Usuario usuario){
        String sql = "INSERT INTO uhhdxfqg.public.usuarios (nome, email, senha, nome_de_guerra, matricula, turma, pelotao) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getSenha());
            pstmt.setString(4, usuario.getNomeDeGuerra());
            pstmt.setInt(5, usuario.getMatricula());
            pstmt.setInt(6, usuario.getTurma());
            pstmt.setInt(7, usuario.getPelotao());


            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // O arranchamento foi salvo com sucesso
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            // Log e tratamento de exceção adequado
            e.printStackTrace();
        }
        return false;
    }
    public Map<Integer, String> buscarNomesDeGuerraPorIds(List<Integer> usuarioIds) {
        Map<Integer, String> nomesDeGuerra = new HashMap<>();
        if (usuarioIds.isEmpty()) return nomesDeGuerra;

        // SQL statement preparation moved outside of the try block to highlight preparation logic
        String placeholders = String.join(",", Collections.nCopies(usuarioIds.size(), "?"));  // Create placeholders for IN clause
        String sql = "SELECT id, nome_de_guerra FROM uhhdxfqg.public.usuarios WHERE id IN (" + placeholders + ")";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            int index = 1;
            for (Integer id : usuarioIds) {
                pstmt.setInt(index++, id);
            }

            pstmt.setFetchSize(50);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    nomesDeGuerra.put(rs.getInt("id"), rs.getString("nome_de_guerra"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  
        }
        return nomesDeGuerra;
    }


}
