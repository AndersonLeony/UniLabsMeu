package Model_dados_de_tabelas;

import io.realm.RealmObject;

/**
 * Created by Leony on 26/04/2017.
 */

public class Model_dadosPcs extends RealmObject {

    private String nomePc;
    private int statusPc;
    public Model_dadosPcs(){

    }
    public Model_dadosPcs(String nomePc, int statusPc) {
        this.nomePc = nomePc;
        this.statusPc = statusPc;
    }

    public String getNomePc() {
        return nomePc;
    }

    public void setNomePc(String nomePc) {
        this.nomePc = nomePc;
    }

    public int getStatusPc() {
        return statusPc;
    }

    public void setStatusPc(int statusPc) {
        this.statusPc = statusPc;
    }

}
