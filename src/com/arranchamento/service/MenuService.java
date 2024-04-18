package arranchamento.service;

import arranchamento.dao.ArranchamentoDAO;
import arranchamento.dao.UsuarioDAO;


import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair; // Importe a classe Pair correta, dependendo do seu ambiente

public class MenuService {

    public List<String> obterTop10UsuariosNomes() {
        ArranchamentoDAO arranchamentoDAO = new ArranchamentoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        List<Integer> top10UsuariosIds = arranchamentoDAO.top10UsuariosArranchadosIds();
        List<String> top10UsuariosNomes = new ArrayList<>();
        for (int usuarioId : top10UsuariosIds) {
            String nomeDeGuerra = usuarioDAO.buscarNomeDeGuerraPorId(usuarioId);
            top10UsuariosNomes.add(nomeDeGuerra);
        }

        return top10UsuariosNomes;
    }

    public List<Integer> obterTop10UsuariosOcorrencias() {
        ArranchamentoDAO arranchamentoDAO = new ArranchamentoDAO();

        return arranchamentoDAO.top10UsuariosArranchadosOcorrencias();
    }



}

