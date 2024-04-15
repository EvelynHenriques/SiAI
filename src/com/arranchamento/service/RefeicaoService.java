package arranchamento.service;

import arranchamento.dao.RefeicaoDAO;

public class RefeicaoService {
    private RefeicaoDAO refeicaoDAO;

    public void RefeicaoService() {
        refeicaoDAO = new RefeicaoDAO();
        // O DAO deve ser instanciado aqui. Pode-se também usar injeção de dependência.
    }

    public Object getRefeicoes(String startDate, String endDate){
        //try {
            //aqui buscar o dado no banco
            refeicaoDAO.buscarPorId(1);
            String resultado = "resultado";
            return resultado;
        //} catch (String e) {}
    }
}
