package arranchamento.service;

import arranchamento.dao.UsuarioDAO;
import arranchamento.modelo.Usuario;

public class AutenticacaoService {
    private UsuarioDAO usuarioDAO;

    public AutenticacaoService() {
        usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(String email, String senha) {
        // A senha deve ser um hash. Aqui, estamos assumindo que senha já é o hash.
        // Em um sistema real, você aplicaria um algoritmo de hash à senha antes de verificar.
        Usuario usuario = usuarioDAO.buscarPorEmailESenha(email, senha);

        if (usuario != null) {
            // A senha fornecida corresponde à senha armazenada (hash).
            return usuario;
        } else {
            // Autenticação falhou.
            return null;
        }
    }

    // Adicione aqui qualquer outro serviço relacionado à autenticação, como redefinição de senha, etc.
}
