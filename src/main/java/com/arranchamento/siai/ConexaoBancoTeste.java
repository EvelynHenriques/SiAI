package com.arranchamento.siai;

import arranchamento.dao.UsuarioDAO;
import arranchamento.modelo.Usuario;
import java.sql.SQLException;

public class ConexaoBancoTeste {

    public void testarConexao() {
        Usuario usuario = null;
        usuario = new Usuario();
        UsuarioDAO usuarioDAO = new UsuarioDAO(); // Criar uma instância de UsuarioDAO

            usuario = usuarioDAO.buscarPorEmailESenha("caroline.gandolfi29@ime.eb.br", "1234");
            System.out.println(usuario.getNome());

    }

    public static void main(String[] args) { // Deve ser static
        ConexaoBancoTeste teste = new ConexaoBancoTeste(); // Criar uma instância de ConexaoBancoTeste
        teste.testarConexao(); // Chamar o método na instância criada
    }
}
