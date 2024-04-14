package arranchamento.dao;

import arranchamento.modelo.Arranchamento;
import arranchamento.util.ConexaoBanco;

import java.sql.*;

public class ArranchamentoDAO {

    public boolean salvar(Arranchamento arranchamento) {
        String sql = "INSERT INTO uhhdxfqg.public.arranchamentos (usuario_id, refeicao_id) VALUES (?, ?, ?)";
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

    // Outros métodos como buscar, atualizar, deletar arranchamentos, etc.
}
