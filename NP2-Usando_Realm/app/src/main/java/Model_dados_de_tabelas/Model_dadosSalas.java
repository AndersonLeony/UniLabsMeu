package Model_dados_de_tabelas;

import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Model_dadosSalas extends RealmObject {

    @PrimaryKey
    private String idSala;
    private String Fk_letraBloco;
    private int numSala;
    private RealmList<Model_dadosPcs> nomesPcs;
    private int statusSala;
    private boolean reservada;
    private String statusAula;

    public Model_dadosSalas(){

    }

    public String getFk_letraBloco() {
        return Fk_letraBloco;
    }

    public void setFk_letraBloco(String fk_letraBloco) {
        this.Fk_letraBloco = fk_letraBloco;
    }

    public int getNumSala() {
        return numSala;
    }

    public void setNumSala(int numSala) {
        this.numSala = numSala;
    }

    public void setNomesPcs(String Fk_letraBloco, int numSala, int somatorioSalas, int statusPc) {
        RealmList<Model_dadosPcs> pc = new RealmList<Model_dadosPcs>();
        pc.add(new Model_dadosPcs(Fk_letraBloco + numSala +""+somatorioSalas, statusPc));
        this.nomesPcs = pc;
    }

    public RealmList<Model_dadosPcs> getNomesPcs() {
        return this.nomesPcs;
    }

    public int getStatusSala() {
        return statusSala;
    }

    public void setStatusSala(int statusSala) {
        this.statusSala = statusSala;
    }

    public String getIdSala() {
        return idSala;
    }

    public void setIdSala(String Fk_letraBloco, int numSala) {
        this.idSala = Fk_letraBloco + numSala;
    }

    public String getStatusAula() {
        return statusAula;
    }

    public void setStatusAula(String statusAula) {
        this.statusAula = statusAula;
    }

}
