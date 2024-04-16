package arranchamento.modelo;

import java.sql.Date;

public class Arranchamento {
    private int id;
    private int usuarioId;
    private int refeicaoId;

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getRefeicaoId() {
        return refeicaoId;
    }

    public void setRefeicaoId(int refeicaoId) {
        this.refeicaoId = refeicaoId;
    }

}
