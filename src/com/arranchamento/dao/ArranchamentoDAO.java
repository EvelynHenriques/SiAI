package arranchamento.dao;

import arranchamento.modelo.Arranchamento;
import arranchamento.util.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ArranchamentoDAO {

    public boolean adicionarArranchamento(Arranchamento arranchamento) {
        String sql = "INSERT INTO uhhdxfqg.public.arranchamentos (usuario_id, refeicao_id, updated_at) VALUES (?, ?, NOW())";
        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, arranchamento.getUsuarioId());
            pstmt.setInt(2, arranchamento.getRefeicaoId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // O arranchamento foi salvo com sucesso
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        arranchamento.setId(generatedKeys.getInt(1));
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

    public boolean atualizarArranchamento(int arranchamentoId) {
        String sql = "UPDATE uhhdxfqg.public.arranchamentos SET updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, arranchamentoId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarArranchamento(int usuarioId, int refeicaoId) {
        String sql = "DELETE FROM uhhdxfqg.public.arranchamentos WHERE usuario_id = ? AND refeicao_id = ?";
        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            pstmt.setInt(2, refeicaoId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Arranchamento buscarArranchamentoPorUsuarioERefeicao(int usuarioId, int refeicaoId) {
        String sql = "SELECT * FROM uhhdxfqg.public.arranchamentos WHERE usuario_id = ? AND refeicao_id = ?";
        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            pstmt.setInt(2, refeicaoId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Arranchamento arranchamento = new Arranchamento();
                    arranchamento.setId(rs.getInt("id"));
                    arranchamento.setUsuarioId(rs.getInt("usuario_id"));
                    arranchamento.setRefeicaoId(rs.getInt("refeicao_id"));
                    return arranchamento;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se nenhum arranchamento for encontrado ou se ocorrer um erro
    }

    public List<Arranchamento> buscarArranchamentosPorUsuario(int usuarioId) {
        List<Arranchamento> arranchamentos = new ArrayList<>();
        String sql = "SELECT a.*, r.data, r.tipo FROM uhhdxfqg.public.arranchamentos a " +
                "JOIN uhhdxfqg.public.refeicoes r ON a.refeicao_id = r.id " +
                "WHERE a.usuario_id = ?";

        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Arranchamento arr = new Arranchamento();
                arr.setId(rs.getInt("id"));
                arr.setUsuarioId(rs.getInt("usuario_id"));
                arr.setRefeicaoId(rs.getInt("refeicao_id"));
                arr.setData(rs.getDate("data"));
                arr.setTipoRefeicao(rs.getString("tipo"));
                arranchamentos.add(arr);
                //System.out.println(arr.getTipoRefeicao() + arr.getData());
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Considerar uma abordagem melhor para tratamento de exceções
        }
        return arranchamentos;
    }

    public boolean atualizarArranchamento(Arranchamento arranchamento) {
        String sql = "UPDATE uhhdxfqg.public.arranchamentos SET updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, arranchamento.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deletarArranchamentosAteDataMaisQuatorzeDias(int usuarioId, Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.DATE, 14); // Adiciona 7 dias à data especificada

        String sql = "DELETE FROM uhhdxfqg.public.arranchamentos " +
                "WHERE usuario_id = ? " +
                "AND EXISTS (SELECT 1 FROM uhhdxfqg.public.refeicoes WHERE arranchamentos.refeicao_id = refeicoes.id AND refeicoes.data <= ?)";
        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            pstmt.setDate(2, new java.sql.Date(cal.getTimeInMillis())); // Usando a data atual + 7 dias

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Integer> top10UsuariosArranchadosIds() {
        List<Integer> top10UsuariosIds = new ArrayList<>();
        String sql = "SELECT usuario_id, COUNT(*) AS ocorrencias\n" +
                "FROM uhhdxfqg.public.arranchamentos\n" +
                "GROUP BY usuario_id\n" +
                "ORDER BY ocorrencias DESC\n" +
                "LIMIT 10;";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int usuarioId = rs.getInt("usuario_id");
                top10UsuariosIds.add(usuarioId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return top10UsuariosIds;
    }

    public List<Integer> top10UsuariosArranchadosOcorrencias() {
        List<Integer> top10UsuariosOcorrencias = new ArrayList<>();
        String sql = "SELECT usuario_id, COUNT(*) AS ocorrencias\n" +
                "FROM uhhdxfqg.public.arranchamentos\n" +
                "GROUP BY usuario_id\n" +
                "ORDER BY ocorrencias DESC\n" +
                "LIMIT 10;";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int ocorrencias = rs.getInt("ocorrencias");
                top10UsuariosOcorrencias.add(ocorrencias);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return top10UsuariosOcorrencias;
    }


}
