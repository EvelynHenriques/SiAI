package arranchamento.modelo;

import java.sql.Date;

public class Refeicao {
    private int id;
    private String tipo;
    private Date data; // A data em que a refeição será servida

    // Construtores, getters e setters
    public Refeicao() {
    }

    public Refeicao(int id, String tipo, Date data) {
        this.id = id;
        this.tipo = tipo;
        this.data = data;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    // Método toString para facilitar a depuração e logs
    @Override
    public String toString() {
        return "Refeicao{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", data=" + data +
                '}';
    }
}
