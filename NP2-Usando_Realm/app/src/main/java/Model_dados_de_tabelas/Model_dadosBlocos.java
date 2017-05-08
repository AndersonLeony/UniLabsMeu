package Model_dados_de_tabelas;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Leony on 24/04/2017.
 */

public class Model_dadosBlocos extends RealmObject {


    private String idBloco;
    @PrimaryKey
    private String letraBloco;
    private int statusBloco;

    public Model_dadosBlocos(){

    }

    public Model_dadosBlocos(String letraBloco, int statusBloco) {
        this.letraBloco = letraBloco;
        this.statusBloco = statusBloco;
    }

    public String getLetraBloco() {
        return letraBloco;
    }

    public void setLetraBloco(String letraBloco) {
        this.letraBloco = letraBloco;
    }

    public int getStatusBloco() {
        return statusBloco;
    }

    public void setStatusBloco(int statusBloco) {
        this.statusBloco = statusBloco;
    }
}
