package arranchamento.dao;

import arranchamento.modelo.Refeicao;
import arranchamento.util.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RefeicaoDAO {

    public Refeicao buscarPorId(int id) {
        Refeicao refeicao = null;
        String sql = "SELECT * FROM uhhdxfqg.public.refeicoes WHERE id = ?";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                refeicao = new Refeicao();
                refeicao.setId(rs.getInt("id"));
                refeicao.setTipo(rs.getString("tipo"));
                refeicao.setData(rs.getDate("data"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Implementar estratégia de tratamento de exceção
        }
        return refeicao;
    }

    public List<Refeicao> listarTodas() {
        List<Refeicao> refeicoes = new ArrayList<>();
        String sql = "SELECT * FROM uhhdxfqg.public.refeicoes";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Refeicao refeicao = new Refeicao();
                refeicao.setId(rs.getInt("id"));
                refeicao.setTipo(rs.getString("tipo"));
                refeicao.setData(rs.getDate("data"));
                refeicoes.add(refeicao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Implementar estratégia de tratamento de exceção
        }
        return refeicoes;
    }

    // Métodos adicionais para inserir, atualizar e deletar refeições poderiam ser implementados aqui

}
