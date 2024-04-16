package arranchamento.dao;

import arranchamento.modelo.Refeicao;
import arranchamento.util.ConexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class RefeicaoDAO {

    public Refeicao buscarPorDataETipo(Date data, String tipo) {
        Refeicao refeicao = null;
        String sql = "SELECT * FROM uhhdxfqg.public.refeicoes WHERE data = ? AND tipo = ?";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setDate(1, data);
            pstmt.setString(2, tipo);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                refeicao = new Refeicao();
                refeicao.setId(rs.getInt("id"));
                refeicao.setTipo(rs.getString("tipo"));
                refeicao.setData(rs.getDate("data"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratamento de exceção ou log
        }
        return refeicao;
    }
    public boolean adicionarRefeicao(Refeicao refeicao) {
        String sql = "INSERT INTO uhhdxfqg.public.refeicoes (tipo, data) VALUES (?, ?)";
        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, refeicao.getTipo());
            pstmt.setDate(2, refeicao.getData());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
