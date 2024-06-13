package arranchamento.service;

import arranchamento.dao.ArranchamentoDAO;
import arranchamento.dao.UsuarioDAO;
import arranchamento.modelo.Arranchamento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class FaltasService {
    private UsuarioDAO usuarioDAO;
    private ArranchamentoDAO arranchamentoDAO;

    public FaltasService() {
        usuarioDAO = new UsuarioDAO();
        arranchamentoDAO = new ArranchamentoDAO();
    }

    public boolean checkAdmin(String userId) {
        System.out.println("checkadmin - userId: " + userId);
        try {
            int userIdInt = Integer.parseInt(userId);
            return usuarioDAO.isUserAdmin(userIdInt);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Arranchamento sendMealInfo(int userId, String mealType, String date) {
        // Converta a string date para java.sql.Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date sqlDate;
        try {
            java.util.Date utilDate = sdf.parse(date);
            sqlDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Retorne false se a data estiver no formato incorreto
        }

        // Chame o método buscarArranchamentosPorUsuarioTipoEData
        List<Arranchamento> arranchamentos = arranchamentoDAO.buscarArranchamentosPorUsuarioTipoEData(userId, mealType, sqlDate);

        // Lógica adicional para processar as informações de refeição, se necessário
        if (!arranchamentos.isEmpty()) {
            System.out.println("Received meal info: userId=" + userId + ", mealType=" + mealType + ", date=" + date);
            Arranchamento arranchamento = arranchamentos.get(0);
            if(!arranchamento.getPresenca()){
                boolean falta = arranchamentoDAO.atualizarPresencaArranchamento(arranchamento);
                if (falta) {
                    System.out.println("FALTA RETIRADA");
                } else {
                    System.out.println("Erro ao atualizar banco");
                }
            }
            return arranchamento;// Retorne true se a operação foi bem-sucedida
        } else {
            System.out.println("No meal info found for userId=" + userId + ", mealType=" + mealType + ", date=" + date);
            return null; // Retorne false se nenhuma informação de refeição for encontrada
        }
    }
}
