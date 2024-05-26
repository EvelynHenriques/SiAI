package arranchamento.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

    private static final String URL = "jdbc:postgresql://heartlessly-revived-coatimundi.data-1.use1.tembo.io:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "XkL4OZtXH8oarag9";

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
