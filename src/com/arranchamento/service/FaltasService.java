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

    public boolean checkAdmin(String sessionId) {
        System.out.println("checkadmin - sessionId: " + sessionId);
        try {
            int userId = Integer.parseInt(sessionId);
            return usuarioDAO.isUserAdmin(userId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendMealInfo(int userId, String mealType, String date) {
        // Converta a string date para java.sql.Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date sqlDate;
        try {
            java.util.Date utilDate = sdf.parse(date);
            sqlDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Retorne false se a data estiver no formato incorreto
        }

        // Chame o método buscarArranchamentosPorUsuarioTipoEData
        List<Arranchamento> arranchamentos = arranchamentoDAO.buscarArranchamentosPorUsuarioTipoEData(userId, mealType, sqlDate);

        // Lógica adicional para processar as informações de refeição, se necessário
        if (!arranchamentos.isEmpty()) {
            System.out.println("Received meal info: userId=" + userId + ", mealType=" + mealType + ", date=" + date);
            return true; // Retorne true se a operação foi bem-sucedida
        } else {
            System.out.println("No meal info found for userId=" + userId + ", mealType=" + mealType + ", date=" + date);
            return false; // Retorne false se nenhuma informação de refeição for encontrada
        }
    }
}
