package Frag_adm_menu_res_aula;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.leony.np2_usando_realm.R;

import Model_dados_de_tabelas.Model_dadosBlocos;
import Model_dados_de_tabelas.Model_dadosDisciplinas;
import Model_dados_de_tabelas.Model_dadosSalas;
import Model_dados_de_tabelas.Model_reservarSalas;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


public class ReservarAulasFragment extends Fragment implements View.OnClickListener {
    private Spinner sp_listarBlocosReservarAula, sp_listarSalasReservarAula, sp_listarDiscipReservarAula,
            sp_listarProfReservarAula,sp_listarDataReservarAula,sp_listarHorariosReservarAula, sp_mostrarHorariosDisp;
    private List<String> lista_dadosBlocos, lista_dadosSalas, lista_dadosDisciplinas, lista_dadosProfessores;
    private RealmResults<Model_dadosBlocos> buscaBlocos; RealmResults<Model_dadosSalas> buscaSalas;
    private RealmResults<Model_dadosDisciplinas> buscaDisciplinas, buscaProfessores;
    private Realm realm;
    private View rowView;
    private Button btnDatePicker, btnTimePicker, btn_ReservarAula, btn_verListaAgendamentos;
    private TextView txt_dataEscolhida, txt_horaEscolhida;
    private String blocoEscolhido, salaEscolhida, dataEscolhida, horaEscolhida, profEscolhido, disciplinaEscolhida;
    int mYear, mMonth, mDay, mHour, mMinute;
    private int Ano, Mes, Dia, Hora, Minuto, sala;

    public ReservarAulasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        rowView = inflater.inflate(R.layout.fragment_adm_reservar_aulas, container, false);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
//        String dataConvertida = Converter.data(mDay,mMonth,mYear);
//        String horaConvertida = Converter.hora(mMinute, mHour);

        realm = Realm.getDefaultInstance();

        //listarBlocos e listarSalas para que apareçam no spinner 'sp_listarBlocosReservarAula'
        lista_dadosBlocos = new ArrayList<String>();
        buscaBlocos = realm.where(Model_dadosBlocos.class).equalTo("statusBloco", R.drawable.bloco_livre).findAll().sort("letraBloco");
        for(int i = 0; i<buscaBlocos.size(); i++) {
            lista_dadosBlocos.add(buscaBlocos.get(i).getLetraBloco());
        }

        sp_listarBlocosReservarAula = (Spinner) rowView.findViewById(R.id.sp_listarBlocosReservarAula);
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_spinner_dropdown_item, lista_dadosBlocos);
        final ArrayAdapter spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_listarBlocosReservarAula.setAdapter(spinnerArrayAdapter);
        //Método do Spinner para capturar o item selecionado
        sp_listarBlocosReservarAula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(final AdapterView<?> parent, View v, final int posicao, long id) {
                blocoEscolhido = parent.getItemAtPosition(posicao).toString();
                //listarSala para que apareçam no spinner 'sp_listarSalasReservarAula'
                lista_dadosSalas = new ArrayList<String>();
                buscaSalas = realm.where(Model_dadosSalas.class)
                        .equalTo("Fk_letraBloco", blocoEscolhido)
                        .findAll().sort("numSala");

                for(int i = 0; i<buscaSalas.size(); i++) {
                    lista_dadosSalas.add(String.valueOf(buscaSalas.get(i).getNumSala()));
                }

                sp_listarSalasReservarAula = (Spinner) rowView.findViewById(R.id.sp_listarSalasReservarAula);
                //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
                ArrayAdapter arrayAdapterSalas = new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_spinner_dropdown_item, lista_dadosSalas);
                ArrayAdapter spinnerArrayAdapterSalas = arrayAdapterSalas;
                spinnerArrayAdapterSalas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_listarSalasReservarAula.setAdapter(spinnerArrayAdapterSalas);
                sp_listarSalasReservarAula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        salaEscolhida = parent.getItemAtPosition(position).toString();
                        sala = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //listarDisciplinas
        lista_dadosDisciplinas = new ArrayList<String>();
        buscaDisciplinas = realm.where(Model_dadosDisciplinas.class).distinct("nomeDisciplina").sort("nomeDisciplina");
        for(int i = 0; i<buscaDisciplinas.size(); i++) {
            lista_dadosDisciplinas.add(buscaDisciplinas.get(i).getNomeDisciplina());
        }
        sp_listarDiscipReservarAula = (Spinner) rowView.findViewById(R.id.sp_listarDiscipReservarAula);
        ArrayAdapter arrayAdapterDisciplinas = new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_spinner_dropdown_item, lista_dadosDisciplinas);
        final ArrayAdapter spinnerArrayAdapterDiscip = arrayAdapterDisciplinas;
        spinnerArrayAdapterDiscip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_listarDiscipReservarAula.setAdapter(spinnerArrayAdapterDiscip);
        sp_listarDiscipReservarAula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                disciplinaEscolhida = parent.getItemAtPosition(position).toString();

                lista_dadosProfessores = new ArrayList<String>();
                buscaProfessores = realm.where(Model_dadosDisciplinas.class)
                        .equalTo("nomeDisciplina", parent.getItemAtPosition(position).toString(), Case.INSENSITIVE)
                        .findAll();
                for(int i = 0; i<buscaProfessores.size(); i++) {
                    lista_dadosProfessores.add(buscaProfessores.get(i).getNomeProfessor());
                }
                sp_listarProfReservarAula = (Spinner) rowView.findViewById(R.id.sp_listarProfReservarAula);
                ArrayAdapter arrayAdapterProfessores = new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_spinner_dropdown_item, lista_dadosProfessores);
                final ArrayAdapter spinnerArrayAdapterProf = arrayAdapterProfessores;
                spinnerArrayAdapterDiscip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_listarProfReservarAula.setAdapter(spinnerArrayAdapterProf);
                sp_listarProfReservarAula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        profEscolhido = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDatePicker=(Button) rowView.findViewById(R.id.btn_date);
        btnDatePicker.setOnClickListener(this);
        txt_dataEscolhida = (TextView) rowView.findViewById(R.id.txt_dataEscolhida);

        btn_ReservarAula = (Button) rowView.findViewById(R.id.btn_ReservarAula);
        btn_ReservarAula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model_reservarSalas verificarAgendamento = realm.where(Model_reservarSalas.class)
                        .equalTo("nomeProfessor", profEscolhido)
                        .equalTo("nomeDisciplina", disciplinaEscolhida)
                        .equalTo("data", dataEscolhida)
                        .equalTo("turno", definirTurno(horaEscolhida))
                        .equalTo("horarioInicio", horaEscolhida)
                        .findFirst();
                if(verificarAgendamento==null){
                    final Model_reservarSalas reservarSala = new Model_reservarSalas(blocoEscolhido,
                            Integer.parseInt(salaEscolhida),
                            profEscolhido,disciplinaEscolhida,
                            dataEscolhida, definirTurno(horaEscolhida),
                            horaEscolhida);
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(reservarSala);
                        }
                    });
                    Toast.makeText(getContext(), "Aula agendada com sucesso!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Esta aula já está agendada em:\n"
                            + ""+verificarAgendamento.getFkSalas_letraBloco()
                            +verificarAgendamento.getFkSala_numSala()+"\n"
                            +verificarAgendamento.getHorarioInicio()+" - "
                            +verificarAgendamento.getHorarioFim(), Toast.LENGTH_LONG).show();
                }

            }
        });

        Button btn_verListaAgendamentos = (Button) rowView.findViewById(R.id.btn_verListaAgendamentos);
        btn_verListaAgendamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content, new ListarReservarAulasFragment()).commit();
            }
        });
        return rowView;
    }

    private String definirTurno(String horaEscolhida) {
        String turnoRetorno = "";

        if(horaEscolhida.equals("7:30")){
            turnoRetorno = "AB - Manhã";

        }else if(horaEscolhida.equals("9:30")){
            turnoRetorno = "CD - Manhã";

        }else if(horaEscolhida.equals("11:20")){
            turnoRetorno = "EF - Manhã";

        }else if(horaEscolhida.equals("13:30")){
            turnoRetorno = "AB - Tarde";

        }else if(horaEscolhida.equals("15:30")){
            turnoRetorno = "CD - Tarde";

        }else if(horaEscolhida.equals("17:20")){
            turnoRetorno = "EF - Tarde";

        }else if(horaEscolhida.equals("19:0")){
            turnoRetorno = "AB - Noite";

        }else if(horaEscolhida.equals("21:0")){
            turnoRetorno = "CD - Noite";
        }
        return turnoRetorno;
    }

    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            dataEscolhida = Converter.data_deInteParaString(dayOfMonth,(monthOfYear+1),year);
                            txt_dataEscolhida.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);

                            sp_mostrarHorariosDisp = (Spinner) rowView.findViewById(R.id.sp_mostrarHorariosDisp);
                            //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
                            ArrayAdapter arrayAdapterMostrarHorariosDisp = new ArrayAdapter<String>(getContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    listarHorariosDisponiveis(dataEscolhida, blocoEscolhido, sala));
                            final ArrayAdapter spinnerArrayAdapterMostrarHorariosDisp = arrayAdapterMostrarHorariosDisp;
                            spinnerArrayAdapterMostrarHorariosDisp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_mostrarHorariosDisp.setAdapter(spinnerArrayAdapterMostrarHorariosDisp);
                            sp_mostrarHorariosDisp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    horaEscolhida = parent.getItemAtPosition(position).toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    }, mYear, mMonth, mDay);

            datePickerDialog.show();

        }
    }


    public ArrayList<String>listarHorariosDisponiveis(String dataConsultar, String letraBloco, int numSala){
        ArrayList<String> horariosGerais = new ArrayList<String>();
        ArrayList<String> horariosDisponiveis = new ArrayList<String>();
        horariosGerais.add("7:30");horariosGerais.add("9:30");horariosGerais.add("11:20");horariosGerais.add("13:30");horariosGerais.add("15:30");horariosGerais.add("17:20");horariosGerais.add("19:0");horariosGerais.add("21:0");
        horariosDisponiveis.add("7:30");
        horariosDisponiveis.add("9:30");
        horariosDisponiveis.add("11:20");
        horariosDisponiveis.add("13:30");
        horariosDisponiveis.add("15:30");
        horariosDisponiveis.add("17:20");
        horariosDisponiveis.add("19:0");
        horariosDisponiveis.add("21:0");
        String horarioAtualString = Converter.hora_deIntParaString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE));
        int horarioAtualInt = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)*60 + Calendar.getInstance().get(Calendar.MINUTE);
        RealmResults<Model_reservarSalas> data = realm.where(Model_reservarSalas.class)
                .equalTo("data", dataConsultar)
                .equalTo("FkSalas_letraBloco",letraBloco)
                .equalTo("FkSala_numSala",numSala)
                .findAll().sort("data").sort("horarioInicio").sort("FkSala_numSala");
        int[] diaMesAnoAgendamento = Converter.data_deStringParaInt(dataConsultar);
        int diaAgendamento = diaMesAnoAgendamento[0];
        int mesAgendamento = diaMesAnoAgendamento[1];
        int anoAgendamento = diaMesAnoAgendamento[2];
        Calendar c = Calendar.getInstance();
        int mesAtual = c.get(Calendar.MONTH); mesAtual = mesAtual+1;

        if(data.isEmpty()==false){

            if ((c.get(Calendar.DAY_OF_MONTH) == diaAgendamento &&
                    mesAtual == mesAgendamento &&
                    c.get(Calendar.YEAR) == anoAgendamento)) {

                for(int i = 0; i<data.size();i++){
                    horariosGerais.remove(data.get(i).getHorarioInicio());
                    horariosDisponiveis.remove(data.get(i).getHorarioInicio());
                }


                for(int j = 0; j<horariosGerais.size();j++){
                    int []vetorHrGeral = Converter.hora_deStringParaInt(horariosGerais.get(j));
                    int horarioGeral = vetorHrGeral[0]*60 + vetorHrGeral[1];
                    //Toast.makeText(getContext(), "Hora real = "+horariosGerais.get(j)+"\nhorarioGeral = "+horarioGeral+"\nhorarioAtualInt = "+horarioAtualInt, Toast.LENGTH_SHORT).show();
                    if(horarioAtualInt>horarioGeral){
                        horariosDisponiveis.remove(horariosGerais.get(j));
                    }
                }

            }else{
                for(int i = 0; i<data.size();i++){
                    horariosGerais.remove(data.get(i).getHorarioInicio());
                    horariosDisponiveis.remove(data.get(i).getHorarioInicio());
                }
            }


        }else{
            if ((c.get(Calendar.DAY_OF_MONTH) == diaAgendamento &&
                    mesAtual == mesAgendamento &&
                    c.get(Calendar.YEAR) == anoAgendamento)) {
                for(int j = 0; j<horariosGerais.size();j++){
                    int []vetorHrGeral = Converter.hora_deStringParaInt(horariosGerais.get(j));
                    int horarioGeral = vetorHrGeral[0]*60 + vetorHrGeral[1];
                    //Toast.makeText(getContext(), "Hora real = "+horariosGerais.get(j)+"\nhorarioGeral = "+horarioGeral+"\nhorarioAtualInt = "+horarioAtualInt, Toast.LENGTH_SHORT).show();
                    if(horarioAtualInt>horarioGeral){
                        horariosDisponiveis.remove(horariosGerais.get(j));
                    }
                }

            }else{
                Toast.makeText(getContext(), "Não há agendamentos para esta data.", Toast.LENGTH_SHORT).show();
            }

        }

        return horariosDisponiveis;

    }

}
