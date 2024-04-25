package arranchamento.modelo;

import java.sql.Date;

public class Arranchamento {
    private int id;
    private int usuarioId;
    private int refeicaoId;
    private Date data;
    private String tipoRefeicao;
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

    public Date getData() {return data;}

    public void setData(Date data) {this.data = data;}

    public String getTipoRefeicao() {return tipoRefeicao;}

    public void setTipoRefeicao(String tipoRefeicao) {this.tipoRefeicao = tipoRefeicao;}
}
