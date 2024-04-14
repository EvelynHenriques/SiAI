package com.arranchamento.siai;

import arranchamento.util.ConexaoBanco;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexaoBancoTeste {

    public static void testarConexao() {
        Connection conexao = null;
        try {
            conexao = ConexaoBanco.obterConexao();
            System.out.println("Conexão obtida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao obter a conexão: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                    System.out.println("Conexão fechada com sucesso.");
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar a conexão: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        testarConexao();
    }
}
