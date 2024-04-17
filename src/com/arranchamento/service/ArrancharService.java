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
    private Map<String, Integer> tipoRefeicaoToIntMap;

    public ArrancharService() {
        this.refeicaoDAO = new RefeicaoDAO();
        this.arranchamentoDAO = new ArranchamentoDAO();
        this.tipoRefeicaoMap = new HashMap<>();
        tipoRefeicaoMap.put(1, "cafe");
        tipoRefeicaoMap.put(2, "almoco");
        tipoRefeicaoMap.put(3, "janta");
        tipoRefeicaoMap.put(4, "ceia");
        getTipoRefeicaoToIntMap();
    }

    public static Map<String, Integer> getTipoRefeicaoToIntMap() {
        Map<String, Integer> refeicaoToInt = new HashMap<>();
        refeicaoToInt.put("cafe", 1);
        refeicaoToInt.put("almoco", 2);
        refeicaoToInt.put("janta", 3);
        refeicaoToInt.put("ceia", 4);
        return refeicaoToInt;
    }
    public int getRefeicaoIntTipo(String tipoRefeicao) {
        Map<String, Integer> tipoRefeicaoToInt = getTipoRefeicaoToIntMap();
        Integer tipoRefeicaoId = tipoRefeicaoToInt.get(tipoRefeicao.toLowerCase());
        if (tipoRefeicaoId == null) {
            throw new IllegalArgumentException("Tipo de refeição desconhecido: " + tipoRefeicao);
        }
        return tipoRefeicaoId;
    }

    public void receberArranchamento(List<String> datas, List<Integer> indicesRefeicao, int usuarioId) {
        if (datas.size() != indicesRefeicao.size()) {
            // As listas devem ter o mesmo tamanho. Se não, há um erro.
            throw new IllegalArgumentException("As listas de datas e índices de refeição devem ter o mesmo tamanho.");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date maiorData = null;

        // Encontra a maior data entre as datas recebidas
        for (String dataStr : datas) {
            try {
                Date dataUtil = sdf.parse(dataStr);
                if (maiorData == null || dataUtil.after(maiorData)) {
                    maiorData = dataUtil;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Deleta os arranchamentos do usuário até a maior data
        deletarArranchamentosAteData(usuarioId, maiorData);

        // Insere os novos arranchamentos recebidos
        for (int i = 0; i < datas.size(); i++) {
            String dataStr = datas.get(i);
            try {
                Date dataUtil = sdf.parse(dataStr); // Converte a String para java.util.Date
                java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime()); // Converte java.util.Date para java.sql.Date

                Integer indiceRefeicao = indicesRefeicao.get(i);
                String tipoRefeicao = tipoRefeicaoMap.get(indiceRefeicao);

                Refeicao refeicao = refeicaoDAO.buscarPorDataETipo(dataSql, tipoRefeicao);

                Arranchamento arranchamento = arranchamentoDAO.buscarArranchamentoPorUsuarioERefeicao(usuarioId, refeicao.getId());
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
    }

    public boolean deletarArranchamentosAteData(int usuarioId, Date data) {
        return arranchamentoDAO.deletarArranchamentosAteDataMaisQuatorzeDias(usuarioId, (java.sql.Date) data);
    }
    public List<Arranchamento> buscarArranchamentosPorUsuario(int usuarioId) {
        return arranchamentoDAO.buscarArranchamentosPorUsuario(usuarioId);
    }
}
