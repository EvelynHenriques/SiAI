package arranchamento.dao;

import arranchamento.modelo.Refeicao;
import arranchamento.util.ConexaoBanco;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RefeicaoDAO {

    public Refeicao buscarPorDataETipo(java.sql.Date data, String tipo) {
        Refeicao refeicao = null;
        String sql = "SELECT * FROM postgres.public.refeicoes WHERE data = ? AND tipo = ?";

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

    public List<Refeicao> buscarPorListaDataETipo(List<Pair<Date, String>> listaDataTipo) {
        List<Refeicao> refeicoes = new ArrayList<>();
        String sql = "SELECT * FROM postgres.public.refeicoes WHERE data = ? AND tipo = ?";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            for (Pair<java.sql.Date, String> par : listaDataTipo) {
                pstmt.setDate(1, par.getKey());
                pstmt.setString(2, par.getValue());

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    Refeicao refeicao = new Refeicao();
                    refeicao.setId(rs.getInt("id"));
                    refeicao.setTipo(rs.getString("tipo"));
                    refeicao.setData(rs.getDate("data"));
                    refeicoes.add(refeicao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratamento de exceção ou log
        }
        return refeicoes;
    }

    public boolean adicionarRefeicao(Refeicao refeicao) {
        String sql = "INSERT INTO postgres.public.refeicoes (tipo, data) VALUES (?, ?)";
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
