package arranchamento.service;

import arranchamento.dao.ArranchamentoDAO;
import arranchamento.dao.UsuarioDAO;
import arranchamento.modelo.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuService {

    private UsuarioDAO usuarioDAO;
    private ArranchamentoDAO arranchamentoDAO;
    private Map<Integer, String> usuarioMap;

    public MenuService() {
        this.usuarioDAO = new UsuarioDAO();
        this.arranchamentoDAO = new ArranchamentoDAO();
        this.usuarioMap = new HashMap<>();
        initializeUsuarioMap();
    }

    private void initializeUsuarioMap() {
        List<Usuario> usuarios = usuarioDAO.buscarTodosUsuarios();
        for (Usuario usuario : usuarios) {
            usuarioMap.put(usuario.getId(), usuario.getNomeDeGuerra());
        }
    }

    public List<String> obterTop10UsuariosNomes() {
        List<Integer> top10UsuariosIds = arranchamentoDAO.top10UsuariosArranchadosIds();
        if (top10UsuariosIds.isEmpty()) return new ArrayList<>(); 

        Map<Integer, String> nomesDeGuerra = usuarioDAO.buscarNomesDeGuerraPorIds(top10UsuariosIds);
        List<String> top10UsuariosNomes = new ArrayList<>();
        for (int usuarioId : top10UsuariosIds) {
            String nomeDeGuerra = nomesDeGuerra.getOrDefault(usuarioId, "Desconhecido");
            top10UsuariosNomes.add(nomeDeGuerra);
        }

        System.out.println(top10UsuariosNomes); 
        return top10UsuariosNomes;
    }


    public List<Integer> obterTop10UsuariosOcorrencias() {
        return arranchamentoDAO.top10UsuariosArranchadosOcorrencias();
    }

    public void atualizarDadosUsuarios() {
        initializeUsuarioMap(); 
    }
}
