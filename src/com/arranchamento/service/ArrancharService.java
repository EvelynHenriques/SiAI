package arranchamento.service;

import arranchamento.dao.ArranchamentoDAO;
import arranchamento.modelo.Arranchamento;
import arranchamento.modelo.Usuario;

public class ArrancharService {

    private ArranchamentoDAO arranchamentoDAO;

    public ArrancharService() {
        arranchamentoDAO = new ArranchamentoDAO();
        // O DAO deve ser instanciado aqui. Pode-se também usar injeção de dependência.
    }

    public boolean arranchar(int usuario_id, int refeicao_id) {
        // Validação dos dados de entrada
        if (dadosValidos(usuario_id, refeicao_id)) {
            // Criação de um novo objeto Arranchamento
            Arranchamento arranchamento = new Arranchamento();
            arranchamento.setUsuarioId(usuario_id);
            arranchamento.setRefeicaoId(refeicao_id);

            // Salvar no banco de dados usando o DAO
            arranchamentoDAO.salvar(arranchamento);

            // Retornar verdadeiro se o arranchamento foi bem sucedido
            return true;
        } else {
            // Se os dados não forem válidos, retornar falso
            return false;
        }
    }

    private boolean dadosValidos(int usuario_id, int refeicao_id) {
        // Implemente a lógica de validação aqui
        // Isso pode incluir verificar se a data está no formato correto,
        // se o tipo de refeição é um dos tipos aceitáveis, etc.
        return true;
    }

    // Adicione outros métodos conforme necessário, como cancelar arranchamento, atualizar, etc.
}
