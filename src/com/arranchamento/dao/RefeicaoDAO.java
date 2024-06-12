package arranchamento.dao;

import arranchamento.modelo.Refeicao;
import arranchamento.util.ConexaoBanco;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public List<Integer> buscarPorListaData(String data) {
        List<Integer> refeicoesIds = new ArrayList<>();
        String sql = "SELECT id FROM postgres.public.refeicoes WHERE data = ?";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, data);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int refeicaoId = rs.getInt("id");
                refeicoesIds.add(refeicaoId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return refeicoesIds;
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

    public String[] buscarDataTipoPorIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new String[0];
        }

        // Obter a data de hoje
        String todayDate = getTodayDate();

        // Construindo a cláusula IN para a query SQL com a condição de data
        StringBuilder sql = new StringBuilder("SELECT data, tipo FROM postgres.public.refeicoes WHERE id IN (");
        for (int i = 0; i < ids.size(); i++) {
            sql.append("?");
            if (i < ids.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(") AND data < ?::date");

        List<String> dataTipos = new ArrayList<>();
        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql.toString())) {

            for (int i = 0; i < ids.size(); i++) {
                pstmt.setInt(i + 1, ids.get(i));
            }

            // Definir a data de hoje como parâmetro
            pstmt.setString(ids.size() + 1, todayDate);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String data = rs.getString("data");
                String tipo = rs.getString("tipo");
                String dataTipo = formatDataTipo(data, tipo);
                dataTipos.add(dataTipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataTipos.toArray(new String[0]);
    }

    private String formatDataTipo(String data, String tipo) {
        String formattedDate = formatDate(data);
        String formattedTipo = formatTipo(tipo);
        return formattedDate + "_" + formattedTipo;
    }

    private String formatDate(String date) {
        try {
            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return sdfOutput.format(sdfInput.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

    private String formatTipo(String tipo) {
        switch (tipo.toLowerCase()) {
            case "cafe":
                return "Café";
            case "almoco":
                return "Almoço";
            case "janta":
                return "Janta";
            case "ceia":
                return "Ceia";
            default:
                return tipo;
        }
    }

    private String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new java.util.Date());
    }
}
