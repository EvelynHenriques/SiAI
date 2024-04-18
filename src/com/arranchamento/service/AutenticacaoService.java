package arranchamento.service;

import arranchamento.dao.UsuarioDAO;
import arranchamento.modelo.Usuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AutenticacaoService {
    private UsuarioDAO usuarioDAO;

    public AutenticacaoService() {
        usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(String email, String senha) {
        // Hashing da senha fornecida
        String senhaHash = hashSenha(senha);
        System.out.println(senhaHash);
        // Busca do usuário pelo email e pela senha hasheada no banco de dados
        Usuario usuario = usuarioDAO.buscarPorEmailESenha(email, senhaHash);

        // Verifica se o usuário foi encontrado e se a senha está correta
        if (usuario != null && usuario.getEmail().equals(email) && usuario.getSenha().equals(senhaHash)) {
            // A senha fornecida corresponde à senha armazenada (hash).
            return usuario;
        } else {
            // Autenticação falhou.
            return null;
        }
    }

    private String hashSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
