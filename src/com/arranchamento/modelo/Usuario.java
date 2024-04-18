package arranchamento.modelo;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha; // Armazenar como hash na prática
    private String nomeDeGuerra;
    private int matricula;
    private int turma;
    private int pelotao;




    // Construtores, getters e setters

    public Usuario() {
    }

    public Usuario(int id, String nome, String email, String senha, String nomeDeGuerra, int matricula, int turma, int pelotao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.nomeDeGuerra = nomeDeGuerra;
        this.matricula = matricula;
        this.turma = turma;
        this.pelotao = pelotao;
    }

    // Getters e setters aqui

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeDeGuerra() {return nomeDeGuerra;}
    public void setNomeDeGuerra(String nomeDeGuerra) {
        this.nomeDeGuerra = nomeDeGuerra;
    }


    public int getMatricula() { return matricula; }
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }


    public int getTurma() { return turma; }
    public void setTurma(int turma) {
        this.turma = turma;
    }

    public int getPelotao() { return pelotao; }
    public void setPelotao(int pelotao) {
        this.pelotao = pelotao;
    }

}
