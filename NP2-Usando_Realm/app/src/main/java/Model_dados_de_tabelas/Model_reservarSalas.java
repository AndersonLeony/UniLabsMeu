package Model_dados_de_tabelas;

import Frag_adm_menu_res_aula.Converter;
import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by Leony on 24/04/2017.
 */

public class Model_reservarSalas extends RealmObject {

    private String idReservarSala;
    private int diaSort;
    private int mesSort;
    private int anoSort;
    private int hrIni;
    private int hrFim;
    private String FkSalas_letraBloco;
    private int FkSala_numSala;
    private String nomeProfessor;
    private String nomeDisciplina;
    private String data;
    private String turno;//Ex.: AB - Manhã
    private String horarioInicio;//EX.: 7:30-9:10
    private String horarioFim;

    public Model_reservarSalas(){

    }

    public Model_reservarSalas(String FkSalas_letraBloco, int FkSala_numSala,
                               String nomeProfessor, String nomeDisciplina,
                               String data, String turno, String horarioInicio) {
        this.FkSalas_letraBloco = FkSalas_letraBloco;
        this.FkSala_numSala = FkSala_numSala;
        this.nomeProfessor = nomeProfessor;
        this.nomeDisciplina = nomeDisciplina;
        this.data = data;
        this.turno = turno;
        this.horarioInicio = horarioInicio;
        this.hrIni = Converter.data_deStringParaInt(horarioInicio)[0]*60+Converter.data_deStringParaInt(horarioInicio)[1];
        this.horarioFim = definirHorarioFim(this.horarioInicio);
        this.idReservarSala = FkSalas_letraBloco+FkSala_numSala;
        this.diaSort = Converter.data_deStringParaInt(this.data)[0];
        this.mesSort = Converter.data_deStringParaInt(this.data)[1];
        this.anoSort = Converter.data_deStringParaInt(this.data)[2];

    }

    private String definirHorarioFim(String horarioInicio) {
        String horarioFim = "";
        if(horarioInicio.equals("7:30")){
            this.hrIni = Converter.hora_deStringParaInt("7:30")[0];
            horarioFim = "9:10";
        }else if(horarioInicio.equals("9:30")){
            this.hrIni = Converter.hora_deStringParaInt("9:30")[0];
            horarioFim = "11:10";
        }else if(horarioInicio.equals("11:20")){
            this.hrIni = Converter.hora_deStringParaInt("11:20")[0];
            horarioFim = "13:30";
        }else if(horarioInicio.equals("13:30")){
            this.hrIni = Converter.hora_deStringParaInt("13:30")[0];
            horarioFim = "15:10";
        }else if(horarioInicio.equals("15:30")){
            this.hrIni = Converter.hora_deStringParaInt("15:30")[0];
            horarioFim = "17:10";
        }else if(horarioInicio.equals("17:20")){
            this.hrIni = Converter.hora_deStringParaInt("17:20")[0];
            horarioFim = "19:00";
        }else if(horarioInicio.equals("19:0")){
            this.hrIni = Converter.hora_deStringParaInt("19:0")[0];
            horarioFim = "20:40";
        }else if(horarioInicio.equals("21:0")){
            this.hrIni = Converter.hora_deStringParaInt("21:0")[0];
            horarioFim = "22:40";
        }
        return horarioFim;
    }

    public String getFkSalas_letraBloco() {
        return FkSalas_letraBloco;
    }

    public void setFkSalas_letraBloco(String fkSalas_letraBloco) {
        FkSalas_letraBloco = fkSalas_letraBloco;
    }

    public int getFkSala_numSala() {
        return FkSala_numSala;
    }

    public void setFkSala_numSala(int fkSala_numSala) {
        FkSala_numSala = fkSala_numSala;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(String horarioFim) {
        this.horarioFim = horarioFim;
    }

    public int getHrIni() {
        return hrIni;
    }

    public void setHrIni(int hrIni) {
        this.hrIni = hrIni;
    }

    public int getHrFim() {
        return hrFim;
    }

    public void setHrFim(int hrFim) {
        this.hrFim = hrFim;
    }

}
