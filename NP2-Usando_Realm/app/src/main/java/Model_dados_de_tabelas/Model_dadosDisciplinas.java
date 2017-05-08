package Model_dados_de_tabelas;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Leony on 24/04/2017.
 */

public class Model_dadosDisciplinas extends RealmObject {

    @PrimaryKey
    private String idDisciplina;
    private int cod;
    @Index
    private String nomeDisciplina;
    private String nomeProfessor;

    public Model_dadosDisciplinas(){

    }

    public Model_dadosDisciplinas(int cod, String nomeDisciplina, String nomeProfessor) {
        this.cod = cod;
        this.nomeDisciplina = nomeDisciplina;
        this.nomeProfessor = nomeProfessor;
        this.idDisciplina = this.cod+this.nomeDisciplina+this.nomeProfessor;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }
}
