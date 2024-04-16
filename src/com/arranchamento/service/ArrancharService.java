package arranchamento.service;

import arranchamento.dao.ArranchamentoDAO;
import arranchamento.dao.RefeicaoDAO;
import arranchamento.modelo.Arranchamento;
import arranchamento.modelo.Refeicao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrancharService {

    private RefeicaoDAO refeicaoDAO;
    private ArranchamentoDAO arranchamentoDAO;
    private Map<Integer, String> tipoRefeicaoMap;

    public ArrancharService() {
        this.refeicaoDAO = new RefeicaoDAO();
        this.arranchamentoDAO = new ArranchamentoDAO();
        this.tipoRefeicaoMap = new HashMap<>();
        tipoRefeicaoMap.put(1, "cafe");
        tipoRefeicaoMap.put(2, "almoco");
        tipoRefeicaoMap.put(3, "janta");
        tipoRefeicaoMap.put(4, "ceia");
    }

    public void receberArranchamento(List<String> datas, List<Integer> indicesRefeicao, int usuarioId) {
        if (datas.size() != indicesRefeicao.size()) {
            // As listas devem ter o mesmo tamanho. Se não, há um erro.
            throw new IllegalArgumentException("As listas de datas e índices de refeição devem ter o mesmo tamanho.");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < datas.size(); i++) {
            String dataStr = datas.get(i);
            try {
                Date dataUtil = sdf.parse(dataStr); // Converte a String para java.util.Date
                java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime()); // Converte java.util.Date para java.sql.Date


                Integer indiceRefeicao = indicesRefeicao.get(i);
            String tipoRefeicao = tipoRefeicaoMap.get(indiceRefeicao);

            Refeicao refeicao = refeicaoDAO.buscarPorDataETipo(dataSql, tipoRefeicao);

            Arranchamento arranchamento = arranchamentoDAO.buscarArranchamentoPorUsuarioERefeicao(usuarioId,refeicao.getId());
                if (arranchamento != null) {
                    arranchamentoDAO.atualizarArranchamento(arranchamento.getId());
                    System.out.println("Refeição encontrada para a data " + dataSql + " e tipo " + tipoRefeicao);
                } else {
                    // Criar uma nova instância de Arranchamento antes de tentar definir valores
                    arranchamento = new Arranchamento();
                    arranchamento.setRefeicaoId(refeicao.getId());
                    arranchamento.setUsuarioId(usuarioId);
                    // Supondo que exista um método adicionarArranchamento que aceite um objeto Arranchamento
                    arranchamentoDAO.adicionarArranchamento(arranchamento);
                    System.out.println("Arranchamento adicionado para a data " + dataSql + " e tipo " + tipoRefeicao);
                }
        } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    // Resto da implementação da classe...
}
}
