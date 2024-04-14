package arranchamento.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

    // Substitua pela URL, usuário e senha fornecidos pelo ElephantSQL
    private static final String URL = "jdbc:postgresql://bubble.db.elephantsql.com:5432/uhhdxfqg";
    private static final String USER = "uhhdxfqg";
    private static final String PASSWORD = "TZ4DHsEuexg4mxI1HZF2UUtcO6fGswnd";

    static {
        try {
            // Carregar o driver JDBC do PostgreSQL
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possível encontrar o driver do PostgreSQL", e);
        }
    }

    public static Connection obterConexao() throws SQLException {
        // Tente obter a conexão
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
