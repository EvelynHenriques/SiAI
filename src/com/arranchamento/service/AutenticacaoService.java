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

        // A senha fornecida corresponde à senha armazenada (hash).
        // Autenticação falhou.
        return usuarioDAO.buscarPorEmailESenha(email, senha);
    }

    // Adicione aqui qualquer outro serviço relacionado à autenticação, como redefinição de senha, etc.
}
