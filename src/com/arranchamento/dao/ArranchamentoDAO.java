package arranchamento.dao;

import arranchamento.modelo.Arranchamento;
import arranchamento.util.ConexaoBanco;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArranchamentoDAO {

    public boolean adicionarArranchamento(Arranchamento arranchamento) {
        String sql = "INSERT INTO postgres.public.arranchamentos (usuario_id, refeicao_id, updated_at) VALUES (?, ?, NOW())";
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

    public boolean adicionarArranchamentos(List<Arranchamento> arranchamentos) {
        String sql = "INSERT INTO postgres.public.arranchamentos (usuario_id, refeicao_id, updated_at) VALUES (?, ?, NOW())";
        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (Arranchamento arranchamento : arranchamentos) {
                pstmt.setInt(1, arranchamento.getUsuarioId());
                pstmt.setInt(2, arranchamento.getRefeicaoId());
                pstmt.addBatch(); // Adiciona a operação ao lote
            }

            int[] affectedRows = pstmt.executeBatch();
            for (int i = 0; i < affectedRows.length; i++) {
                if (affectedRows[i] > 0) {
                    return true;


                }
            }

        } catch (SQLException e) {
            // Log e tratamento de exceção adequado
            e.printStackTrace();
        }
        return false;
    }


    public boolean atualizarArranchamento(int arranchamentoId) {
        String sql = "UPDATE postgres.public.arranchamentos SET updated_at = CURRENT_TIMESTAMP WHERE id = ?";
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
        String sql = "DELETE FROM postgres.public.arranchamentos WHERE usuario_id = ? AND refeicao_id = ?";
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
        String sql = "SELECT * FROM postgres.public.arranchamentos WHERE usuario_id = ? AND refeicao_id = ?";
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
        String sql = "SELECT a.*, r.data, r.tipo FROM postgres.public.arranchamentos a " +
                "JOIN postgres.public.refeicoes r ON a.refeicao_id = r.id " +
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

    public List<Arranchamento> buscarArranchamentosPorUsuarioEData(int usuarioId, String data) {
        List<Arranchamento> arranchamentos = new ArrayList<>();
        String sql = "SELECT a.*, r.data, r.tipo FROM postgres.public.arranchamentos a " +
                "JOIN postgres.public.refeicoes r ON a.refeicao_id = r.id " +
                "WHERE a.usuario_id = ? AND r.data = ?";

        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);

            // Converta a String data para java.sql.Date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            java.util.Date utilDate = sdf.parse(data);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            pstmt.setDate(2, sqlDate);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Arranchamento arr = new Arranchamento();
                arr.setId(rs.getInt("id"));
                arr.setUsuarioId(rs.getInt("usuario_id"));
                arr.setRefeicaoId(rs.getInt("refeicao_id"));
                arr.setData(rs.getDate("data"));
                arr.setTipoRefeicao(rs.getString("tipo"));
                arranchamentos.add(arr);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();  // Considerar uma abordagem melhor para tratamento de exceções
        }
        return arranchamentos;
    }

    public List<Arranchamento> buscarArranchamentosPorUsuarioTipoEData(int usuarioId, String mealType, java.sql.Date date) {
        List<Arranchamento> arranchamentos = new ArrayList<>();
        String sql = "SELECT a.*, r.data, r.tipo FROM postgres.public.arranchamentos a " +
                "JOIN postgres.public.refeicoes r ON a.refeicao_id = r.id " +
                "WHERE a.usuario_id = ? AND r.tipo = ? AND r.data = ?";

        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            pstmt.setString(2, mealType);
            pstmt.setDate(3, date);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Arranchamento arr = new Arranchamento();
                arr.setId(rs.getInt("id"));
                arr.setUsuarioId(rs.getInt("usuario_id"));
                arr.setRefeicaoId(rs.getInt("refeicao_id"));
                arr.setData(rs.getDate("data"));
                arr.setTipoRefeicao(rs.getString("tipo"));
                arranchamentos.add(arr);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Considerar uma abordagem melhor para tratamento de exceções
        }
        return arranchamentos;
    }

    public boolean atualizarArranchamento(Arranchamento arranchamento) {
        String sql = "UPDATE postgres.public.arranchamentos SET updated_at = CURRENT_TIMESTAMP WHERE id = ?";
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
        Logger logger = Logger.getLogger(this.getClass().getName());

        // Calcular a data da próxima segunda-feira daqui a 2 semanas
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, 2); // Adiciona 2 semanas à data atual
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Define o dia da semana como segunda-feira
        Date proximaSegunda = new Date(cal.getTimeInMillis());
        logger.log(Level.INFO, "Próxima segunda daqui a 2 semanas: {0}", proximaSegunda);

        // Calcular a data fim
        Date dataFim;
        if (proximaSegunda.equals(data)) {
            Calendar calDataInicioMaisSeis = Calendar.getInstance();
            calDataInicioMaisSeis.setTime(proximaSegunda);
            calDataInicioMaisSeis.add(Calendar.DATE, 6);
            dataFim = new Date(calDataInicioMaisSeis.getTimeInMillis());
        } else {
            Calendar calDataRecebidaMaisSeis = Calendar.getInstance();
            calDataRecebidaMaisSeis.setTime(data);
            calDataRecebidaMaisSeis.add(Calendar.DATE, 6);
            dataFim = new Date(calDataRecebidaMaisSeis.getTimeInMillis());
        }
        logger.log(Level.INFO, "Data início: {0}", proximaSegunda);
        logger.log(Level.INFO, "Data fim: {0}", dataFim);

        String sql = "DELETE FROM postgres.public.arranchamentos " +
                "WHERE usuario_id = ? " +
                "AND EXISTS (SELECT 1 FROM postgres.public.refeicoes WHERE arranchamentos.refeicao_id = refeicoes.id AND refeicoes.data BETWEEN ? AND ?)";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            pstmt.setDate(2, new java.sql.Date(proximaSegunda.getTime())); // Data de início
            pstmt.setDate(3, new java.sql.Date(dataFim.getTime())); // Data de fim

            int affectedRows = pstmt.executeUpdate();
            logger.log(Level.INFO, "Número de linhas afetadas: {0}", affectedRows);
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao deletar arranchamentos", e);
            return false;
        }
    }


    public List<Integer> top10UsuariosArranchadosIds() {
        List<Integer> top10UsuariosIds = new ArrayList<>();
        String sql = "SELECT usuario_id, COUNT(*) AS ocorrencias\n" +
                "FROM postgres.public.arranchamentos\n" +
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
                "FROM postgres.public.arranchamentos\n" +
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
