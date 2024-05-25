package arranchamento.service;

import arranchamento.dao.ArranchamentoDAO;
import arranchamento.dao.RefeicaoDAO;
import arranchamento.modelo.Arranchamento;
import arranchamento.modelo.Refeicao;
import javafx.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.SQLException;

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

    public void receberArranchamento(List<String> datas, List<Integer> indicesRefeicao, int usuarioId, Date lastDateDisplayed) {
        if (datas.size() != indicesRefeicao.size()) {
            // As listas devem ter o mesmo tamanho. Se não, há um erro.
            throw new IllegalArgumentException("As listas de datas e índices de refeição devem ter o mesmo tamanho.");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //System.out.println(lastDateDisplayed);

        // Deleta os arranchamentos do usuário até a maior data
        deletarArranchamentosAteData(usuarioId, lastDateDisplayed);

            try {
                List<Arranchamento> arranchamentos = new ArrayList<>();
                List<Refeicao> refeicoes = new ArrayList<>();
                List<Pair<java.sql.Date, String>> listaDataTipo = new ArrayList<>();
                for(int i=0; i<datas.size(); i++){
                    Date dataUtil = sdf.parse(datas.get(i)); // Converte a String para java.util.Date
                    java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime()); // Converte java.util.Date para java.sql.Date
                    Integer indiceRefeicao = indicesRefeicao.get(i);
                    String tipoRefeicao = tipoRefeicaoMap.get(indiceRefeicao);
                    Pair<java.sql.Date, String> novoPar = new Pair<>(dataSql, tipoRefeicao);
                    listaDataTipo.add(novoPar);
                }
                refeicoes = refeicaoDAO.buscarPorListaDataETipo(listaDataTipo);
                for(int i=0; i<refeicoes.size(); i++) {
                    Arranchamento arranchamento = new Arranchamento();
                    arranchamento.setRefeicaoId((refeicoes.get(i)).getId());
                    arranchamento.setUsuarioId(usuarioId);
                    arranchamentos.add(arranchamento);
                }
                arranchamentoDAO.adicionarArranchamentos(arranchamentos);

                //Refeicao refeicao = refeicaoDAO.buscarPorDataETipo(dataSql, tipoRefeicao);
                System.out.println("Arranchamentos adicionados");

            } catch (ParseException e) {
                e.printStackTrace();
            }
        //}
    }

    public boolean deletarArranchamentosAteData(int usuarioId, Date data) {
        return arranchamentoDAO.deletarArranchamentosAteDataMaisQuatorzeDias(usuarioId, (java.sql.Date) data);
    }
    public List<Arranchamento> buscarArranchamentosPorUsuario(int usuarioId) {
        return arranchamentoDAO.buscarArranchamentosPorUsuario(usuarioId);
    }
}
