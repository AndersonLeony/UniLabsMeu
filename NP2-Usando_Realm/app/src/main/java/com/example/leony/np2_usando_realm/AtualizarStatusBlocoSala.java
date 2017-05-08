package com.example.leony.np2_usando_realm;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import Frag_adm_menu_res_aula.Converter;
import Model_dados_de_tabelas.Model_dadosSalas;
import Model_dados_de_tabelas.Model_reservarSalas;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Leony on 03/05/2017.
 */

public class AtualizarStatusBlocoSala {

    /*TextView txt_letraBloco = (TextView) rowView.findViewById(R.id.txt_letraBloco);
        TextView txt_numSala = (TextView) rowView.findViewById(R.id.txt_numSala);
        //ImageView img_statusSala = (ImageView) rowView.findViewById(R.id.img_statusSala);
        TextView txt_statusAula = (TextView) rowView.findViewById(R.id.txt_status);
        ImageView img_emAulaProxAula = (ImageView) rowView.findViewById(R.id.img_emAulaProxAula);*/

    public static void atualizarStatusSalas(Realm realm, Context context,
                                            final String letraBloco, final int numSala,
                                            final TextView txt_letraBloco,
                                            final TextView txt_numSala,
                                            final ImageView img_statusSala,
                                            final TextView txt_statusAula,
                                            final TextView txt_DisciplinaAgendada,
                                            final TextView txt_ProfessorAgendado,
                                            final TextView txt_turnoAgendado,
                                            final TextView txt_horarioAgendado,
                                            List<Model_dadosSalas> elementos,
                                            int posicao) {
        int mes = Calendar.getInstance().get(Calendar.MONTH);
        mes = mes + 1;
        String dataTempoReal = Converter.data_deInteParaString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                mes, Calendar.getInstance().get(Calendar.YEAR));

        realm = Realm.getDefaultInstance();
        RealmResults<Model_reservarSalas> buscarAulasAgendadas = realm.where(Model_reservarSalas.class)
                .equalTo("FkSalas_letraBloco", letraBloco)
                .equalTo("FkSala_numSala", numSala)
                .equalTo("data", dataTempoReal)
                .findAllSorted("hrIni");
        Toast.makeText(context, letraBloco+numSala+" Tamanho = "+buscarAulasAgendadas.size(), Toast.LENGTH_SHORT).show();
        if (buscarAulasAgendadas.isEmpty() == false) {
            for (int i = 0; i < buscarAulasAgendadas.size(); i++) {
                int[] diaMesAnoAgendamento = Converter.data_deStringParaInt(buscarAulasAgendadas.get(i).getData().toString());
                int[] horaMinInicioAgendamento = Converter.hora_deStringParaInt(buscarAulasAgendadas.get(i).getHorarioInicio());
                int[] horaMinFimAgendamento = Converter.hora_deStringParaInt(buscarAulasAgendadas.get(i).getHorarioFim());
                int diaAgendamento = diaMesAnoAgendamento[0];
                int mesAgendamento = diaMesAnoAgendamento[1];
                int anoAgendamento = diaMesAnoAgendamento[2];
                int hrInicioAgend = horaMinInicioAgendamento[0] * 60 + horaMinInicioAgendamento[1];
                int hrFimAgend = horaMinFimAgendamento[0] * 60 + horaMinFimAgendamento[1];
                int hrAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60 + Calendar.getInstance().get(Calendar.MINUTE);
                int hrIniProxAgend = 0;
                if ((i + 1) < buscarAulasAgendadas.size()) {
                    hrIniProxAgend = Converter.hora_deStringParaInt(buscarAulasAgendadas.get(i + 1).getHorarioInicio())[0]
                            * 60 + Converter.hora_deStringParaInt(buscarAulasAgendadas.get(i + 1).getHorarioInicio())[1];
                }

                Calendar c = Calendar.getInstance();
                int mesAtual = c.get(Calendar.MONTH);
                mesAtual = mesAtual + 1;

                if ((c.get(Calendar.DAY_OF_MONTH) == diaAgendamento &&
                        mesAtual == mesAgendamento &&
                        c.get(Calendar.YEAR) == anoAgendamento)) {

                    if (hrAtual >= hrInicioAgend && hrAtual <= hrFimAgend) {
                        Model_dadosSalas buscarNomesPcs_dadosSalas = realm.where(Model_dadosSalas.class)
                                .equalTo("Fk_letraBloco", buscarAulasAgendadas.get(i).getFkSalas_letraBloco())
                                .equalTo("numSala", buscarAulasAgendadas.get(i).getFkSala_numSala())
                                .findFirst();

                        final Model_dadosSalas atualizarStatusSala = new Model_dadosSalas();
                        atualizarStatusSala.setIdSala(buscarNomesPcs_dadosSalas.getFk_letraBloco(), buscarNomesPcs_dadosSalas.getNumSala());
                        atualizarStatusSala.setFk_letraBloco(buscarNomesPcs_dadosSalas.getFk_letraBloco());
                        atualizarStatusSala.setNumSala(buscarNomesPcs_dadosSalas.getNumSala());
                        atualizarStatusSala.setStatusSala(R.drawable.sala_ocupada);
                        for (int j = 0; j < elementos.get(posicao).getNomesPcs().size(); j++) {
                            atualizarStatusSala.setNomesPcs(elementos.get(posicao).getFk_letraBloco(),
                                    elementos.get(posicao).getNumSala(), (j + 1), R.drawable.pc_livre);
                        }
                        atualizarStatusSala.setStatusAula("Em Aula  ");

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(atualizarStatusSala);
                            }
                        });
                        img_statusSala.setImageResource(atualizarStatusSala.getStatusSala());
                        txt_statusAula.setText(atualizarStatusSala.getStatusAula());
                        txt_letraBloco.setText(atualizarStatusSala.getFk_letraBloco());
                        txt_numSala.setText(String.valueOf(atualizarStatusSala.getNumSala()));
                        txt_DisciplinaAgendada.setText(buscarAulasAgendadas.get(i).getNomeDisciplina());
                        txt_ProfessorAgendado.setText(buscarAulasAgendadas.get(i).getNomeProfessor());
                        txt_turnoAgendado.setText(buscarAulasAgendadas.get(i).getTurno());
                        txt_horarioAgendado.setText(buscarAulasAgendadas.get(i).getHorarioInicio() +
                                " - " + buscarAulasAgendadas.get(i).getHorarioFim());


                    } else if (hrIniProxAgend != 0) {
                        if (hrAtual > hrFimAgend && hrAtual < hrIniProxAgend) {
                            Model_dadosSalas buscarNomesPcs_dadosSalas = realm.where(Model_dadosSalas.class)
                                    .equalTo("Fk_letraBloco", letraBloco)
                                    .equalTo("numSala", numSala)
                                    .findFirst();

                            int qtdePcs = buscarNomesPcs_dadosSalas.getNomesPcs().size();
                            int qtdePcsOcupados = 0;
                            for (int b = 0; b < elementos.get(posicao).getNomesPcs().size(); b++) {
                                if (buscarNomesPcs_dadosSalas.getNomesPcs().get(b).getStatusPc() == R.drawable.pc_ocupado) {
                                    qtdePcsOcupados = qtdePcsOcupados + 1;
                                }
                            }

                            if (qtdePcsOcupados == buscarNomesPcs_dadosSalas.getNomesPcs().size()) {
                                final Model_dadosSalas atualizarStatusSala = new Model_dadosSalas();
                                atualizarStatusSala.setIdSala(buscarNomesPcs_dadosSalas.getFk_letraBloco(), buscarNomesPcs_dadosSalas.getNumSala());
                                atualizarStatusSala.setFk_letraBloco(buscarNomesPcs_dadosSalas.getFk_letraBloco());
                                atualizarStatusSala.setNumSala(buscarNomesPcs_dadosSalas.getNumSala());
                                atualizarStatusSala.setStatusSala(R.drawable.sala_ocupada);
                                for (int k = 0; k < elementos.get(posicao).getNomesPcs().size(); k++) {
                                    atualizarStatusSala.setNomesPcs(elementos.get(posicao).getFk_letraBloco(), elementos.get(posicao).getNumSala(), k, R.drawable.pc_livre);
                                }
                                atualizarStatusSala.setStatusAula("Próx Aula");

                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.copyToRealmOrUpdate(atualizarStatusSala);
                                    }
                                });
                                img_statusSala.setImageResource(atualizarStatusSala.getStatusSala());
                                txt_statusAula.setText(atualizarStatusSala.getStatusAula());
                                txt_letraBloco.setText(atualizarStatusSala.getFk_letraBloco());
                                txt_numSala.setText(String.valueOf(atualizarStatusSala.getNumSala()));
                                txt_DisciplinaAgendada.setText(buscarAulasAgendadas.get(i + 1).getNomeDisciplina());
                                txt_ProfessorAgendado.setText(buscarAulasAgendadas.get(i + 1).getNomeProfessor());
                                txt_turnoAgendado.setText(buscarAulasAgendadas.get(i + 1).getTurno());
                                txt_horarioAgendado.setText(buscarAulasAgendadas.get(i + 1).getHorarioInicio() +
                                        " - " + buscarAulasAgendadas.get(i + 1).getHorarioFim());

                            } else if (qtdePcsOcupados < buscarNomesPcs_dadosSalas.getNomesPcs().size()) {
                                final Model_dadosSalas atualizarStatusSala = new Model_dadosSalas();
                                atualizarStatusSala.setIdSala(buscarNomesPcs_dadosSalas.getFk_letraBloco(), buscarNomesPcs_dadosSalas.getNumSala());
                                atualizarStatusSala.setFk_letraBloco(buscarNomesPcs_dadosSalas.getFk_letraBloco());
                                atualizarStatusSala.setNumSala(buscarNomesPcs_dadosSalas.getNumSala());
                                atualizarStatusSala.setStatusSala(R.drawable.sala_livre);
                                for (int l = 0; l < elementos.get(posicao).getNomesPcs().size(); l++) {
                                    atualizarStatusSala.setNomesPcs(atualizarStatusSala.getFk_letraBloco(), atualizarStatusSala.getNumSala(), l, R.drawable.pc_livre);
                                }
                                atualizarStatusSala.setStatusAula("Lab Livre\n(Próx Aula)");

                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.copyToRealmOrUpdate(atualizarStatusSala);
                                    }
                                });
                                img_statusSala.setImageResource(atualizarStatusSala.getStatusSala());
                                txt_statusAula.setText(atualizarStatusSala.getStatusAula());
                                txt_letraBloco.setText(atualizarStatusSala.getFk_letraBloco());
                                txt_numSala.setText(String.valueOf(atualizarStatusSala.getNumSala()));
                                txt_DisciplinaAgendada.setText(buscarAulasAgendadas.get(i + 1).getNomeDisciplina());
                                txt_ProfessorAgendado.setText(buscarAulasAgendadas.get(i + 1).getNomeProfessor());
                                txt_turnoAgendado.setText(buscarAulasAgendadas.get(i + 1).getTurno());
                                txt_horarioAgendado.setText(buscarAulasAgendadas.get(i + 1).getHorarioInicio() +
                                        " - " + buscarAulasAgendadas.get(i + 1).getHorarioFim());
                            }
                            break;
                        }else if(hrAtual < hrIniProxAgend){
                            Model_dadosSalas buscarNomesPcs_dadosSalas = realm.where(Model_dadosSalas.class)
                                    .equalTo("Fk_letraBloco", letraBloco)
                                    .equalTo("numSala", numSala)
                                    .findFirst();

                            int qtdePcs = buscarNomesPcs_dadosSalas.getNomesPcs().size();
                            int qtdePcsOcupados = 0;
                            for (int b = 0; b < elementos.get(posicao).getNomesPcs().size(); b++) {
                                if (buscarNomesPcs_dadosSalas.getNomesPcs().get(b).getStatusPc() == R.drawable.pc_ocupado) {
                                    qtdePcsOcupados = qtdePcsOcupados + 1;
                                }
                            }

                            if (qtdePcsOcupados == buscarNomesPcs_dadosSalas.getNomesPcs().size()) {
                                final Model_dadosSalas atualizarStatusSala = new Model_dadosSalas();
                                atualizarStatusSala.setIdSala(buscarNomesPcs_dadosSalas.getFk_letraBloco(), buscarNomesPcs_dadosSalas.getNumSala());
                                atualizarStatusSala.setFk_letraBloco(buscarNomesPcs_dadosSalas.getFk_letraBloco());
                                atualizarStatusSala.setNumSala(buscarNomesPcs_dadosSalas.getNumSala());
                                atualizarStatusSala.setStatusSala(R.drawable.sala_ocupada);
                                for (int k = 0; k < elementos.get(posicao).getNomesPcs().size(); k++) {
                                    atualizarStatusSala.setNomesPcs(elementos.get(posicao).getFk_letraBloco(), elementos.get(posicao).getNumSala(), k, R.drawable.pc_livre);
                                }
                                atualizarStatusSala.setStatusAula("Lab Ocup.\n(Próx Aula)");

                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.copyToRealmOrUpdate(atualizarStatusSala);
                                    }
                                });
                                img_statusSala.setImageResource(atualizarStatusSala.getStatusSala());
                                txt_statusAula.setText(atualizarStatusSala.getStatusAula());
                                txt_letraBloco.setText(atualizarStatusSala.getFk_letraBloco());
                                txt_numSala.setText(String.valueOf(atualizarStatusSala.getNumSala()));
                                txt_DisciplinaAgendada.setText(buscarAulasAgendadas.get(i + 1).getNomeDisciplina());
                                txt_ProfessorAgendado.setText(buscarAulasAgendadas.get(i + 1).getNomeProfessor());
                                txt_turnoAgendado.setText(buscarAulasAgendadas.get(i + 1).getTurno());
                                txt_horarioAgendado.setText(buscarAulasAgendadas.get(i + 1).getHorarioInicio() +
                                        " - " + buscarAulasAgendadas.get(i + 1).getHorarioFim());

                            } else if (qtdePcsOcupados < buscarNomesPcs_dadosSalas.getNomesPcs().size()) {
                                final Model_dadosSalas atualizarStatusSala = new Model_dadosSalas();
                                atualizarStatusSala.setIdSala(buscarNomesPcs_dadosSalas.getFk_letraBloco(), buscarNomesPcs_dadosSalas.getNumSala());
                                atualizarStatusSala.setFk_letraBloco(buscarNomesPcs_dadosSalas.getFk_letraBloco());
                                atualizarStatusSala.setNumSala(buscarNomesPcs_dadosSalas.getNumSala());
                                atualizarStatusSala.setStatusSala(R.drawable.sala_livre);
                                for (int l = 0; l < elementos.get(posicao).getNomesPcs().size(); l++) {
                                    atualizarStatusSala.setNomesPcs(atualizarStatusSala.getFk_letraBloco(), atualizarStatusSala.getNumSala(), l, R.drawable.pc_livre);
                                }
                                atualizarStatusSala.setStatusAula("Lab Livre\n(Próx Aula)");

                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.copyToRealmOrUpdate(atualizarStatusSala);
                                    }
                                });
                                img_statusSala.setImageResource(atualizarStatusSala.getStatusSala());
                                txt_statusAula.setText(atualizarStatusSala.getStatusAula());
                                txt_letraBloco.setText(atualizarStatusSala.getFk_letraBloco());
                                txt_numSala.setText(String.valueOf(atualizarStatusSala.getNumSala()));
                                txt_DisciplinaAgendada.setText(buscarAulasAgendadas.get(i + 1).getNomeDisciplina());
                                txt_ProfessorAgendado.setText(buscarAulasAgendadas.get(i + 1).getNomeProfessor());
                                txt_turnoAgendado.setText(buscarAulasAgendadas.get(i + 1).getTurno());
                                txt_horarioAgendado.setText(buscarAulasAgendadas.get(i + 1).getHorarioInicio() +
                                        " - " + buscarAulasAgendadas.get(i + 1).getHorarioFim());
                            }
                            break;
                        }else if(hrInicioAgend==(Converter.hora_deStringParaInt("7:30")[0]*60+Converter.hora_deStringParaInt("7:30")[1])
                                && hrAtual<hrInicioAgend){
                            img_statusSala.setImageResource(R.drawable.sala_livre);
                            txt_statusAula.setText("Lab Livre\n(Prox. Aula)");
                            txt_letraBloco.setText(letraBloco);
                            txt_numSala.setText(String.valueOf(numSala));
                            txt_DisciplinaAgendada.setText(buscarAulasAgendadas.get(i).getNomeDisciplina());
                            txt_ProfessorAgendado.setText(buscarAulasAgendadas.get(i).getNomeProfessor());
                            txt_turnoAgendado.setText(buscarAulasAgendadas.get(i).getTurno());
                            txt_horarioAgendado.setText(buscarAulasAgendadas.get(i).getHorarioInicio() +
                                    " - " + buscarAulasAgendadas.get(i).getHorarioFim());
                            break;
                        }
                    }
                }
            }

        }else{
            final Model_dadosSalas dadosSala = realm.where(Model_dadosSalas.class)
                    .equalTo("Fk_letraBloco", elementos.get(posicao).getFk_letraBloco())
                    .equalTo("numSala", elementos.get(posicao).getNumSala())
                    .findFirst();

            int qtdePcs = dadosSala.getNomesPcs().size();
            int qtdePcsOcupados = 0;
            for (int b = 0; b < qtdePcs; b++) {
                if (dadosSala.getNomesPcs().get(b).getStatusPc() == R.drawable.pc_ocupado) {
                    qtdePcsOcupados = qtdePcsOcupados + 1;
                }
            }


            if (qtdePcs == qtdePcsOcupados) {
                final Model_dadosSalas atualizarStatusSala = new Model_dadosSalas();
                atualizarStatusSala.setIdSala(dadosSala.getFk_letraBloco(), dadosSala.getNumSala());
                atualizarStatusSala.setFk_letraBloco(dadosSala.getFk_letraBloco());
                atualizarStatusSala.setNumSala(dadosSala.getNumSala());
                for (int l = 0; l < elementos.get(posicao).getNomesPcs().size(); l++) {
                    atualizarStatusSala.setNomesPcs(dadosSala.getFk_letraBloco(), dadosSala.getNumSala(), l, R.drawable.pc_livre);
                }
                atualizarStatusSala.setStatusSala(R.drawable.sala_ocupada);

                atualizarStatusSala.setStatusAula("Lab Lotado\n(Pcs ocupados)");

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        realm.copyToRealmOrUpdate(atualizarStatusSala);
                    }
                });
                img_statusSala.setImageResource(atualizarStatusSala.getStatusSala());
                txt_statusAula.setText(atualizarStatusSala.getStatusAula());
                txt_letraBloco.setText(atualizarStatusSala.getFk_letraBloco());
                txt_numSala.setText(String.valueOf(atualizarStatusSala.getNumSala()));
                txt_DisciplinaAgendada.setText("");
                txt_ProfessorAgendado.setText("");
                txt_turnoAgendado.setText("");
                txt_horarioAgendado.setText("");
            } else if (qtdePcsOcupados < qtdePcs) {
                final Model_dadosSalas atualizarStatusSala = new Model_dadosSalas();
                atualizarStatusSala.setIdSala(letraBloco, numSala);
                atualizarStatusSala.setFk_letraBloco(letraBloco);
                atualizarStatusSala.setNumSala(numSala);
                for (int o = 0; o < elementos.get(posicao).getNomesPcs().size(); o++) {
                    atualizarStatusSala.setNomesPcs(dadosSala.getFk_letraBloco(), dadosSala.getNumSala(), o, R.drawable.pc_livre);
                }
                atualizarStatusSala.setStatusSala(R.drawable.sala_livre);

                atualizarStatusSala.setStatusAula("Sem aulas\n(Livre)");

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        realm.copyToRealmOrUpdate(atualizarStatusSala);
                    }
                });
                img_statusSala.setImageResource(atualizarStatusSala.getStatusSala());
                txt_statusAula.setText(atualizarStatusSala.getStatusAula());
                txt_letraBloco.setText(atualizarStatusSala.getFk_letraBloco());
                txt_numSala.setText(String.valueOf(atualizarStatusSala.getNumSala()));
                txt_DisciplinaAgendada.setText("");
                txt_ProfessorAgendado.setText("");
                txt_turnoAgendado.setText("");
                txt_horarioAgendado.setText("");
            }
        }
    }

    public static void atualizarStatusBlocos(Realm realm, int qtdePcsOcupados){

    }

}
