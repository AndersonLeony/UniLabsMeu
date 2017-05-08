package Frag_adm_menu_del_blocos_e_salas;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.leony.np2_usando_realm.AtualizarStatusBlocoSala;
import com.example.leony.np2_usando_realm.R;

import java.util.ArrayList;
import java.util.List;

import ArraysAdapters.ListarSalasParaDeletarAdapter;
import Model_dados_de_tabelas.Model_dadosBlocos;
import Model_dados_de_tabelas.Model_dadosSalas;
import Model_dados_de_tabelas.Model_reservarSalas;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeletarSalasFragment extends Fragment {
    private Spinner sp_deletarSala_ListaBlocos;
    List<String> lista_dadosBlocos;
    RealmResults<Model_dadosBlocos> buscaBlocos;
    RealmResults<Model_dadosSalas> buscaSalas;
    Realm realm;
    String blocoEscolhido;
    View rowView, rowView2;

    public DeletarSalasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rowView = inflater.inflate(R.layout.fragment_adm_del_salas, container, false);
        rowView2 = inflater.inflate(R.layout.layout_linha_lista_salas_para_deletar, container, false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        realm = Realm.getDefaultInstance();

        lista_dadosBlocos = new ArrayList<String>();
        buscaBlocos = realm.where(Model_dadosBlocos.class).findAll().sort("letraBloco");
        for(int i = 0; i<buscaBlocos.size(); i++) {
            lista_dadosBlocos.add(buscaBlocos.get(i).getLetraBloco());
        }

        buscaSalas = realm.where(Model_dadosSalas.class).findAll();

        sp_deletarSala_ListaBlocos = (Spinner) rowView.findViewById(R.id.sp_deletarSala_ListaBlocos);
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_spinner_dropdown_item, lista_dadosBlocos);
        ArrayAdapter spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_deletarSala_ListaBlocos.setAdapter(spinnerArrayAdapter);
        //Método do Spinner para capturar o item selecionado
        sp_deletarSala_ListaBlocos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(final AdapterView<?> parent, View v, final int posicao, long id) {
                //pega nome pela posição
                blocoEscolhido = parent.getItemAtPosition(posicao).toString();
                //imprime um Toast na tela com o nome que foi selecionado
                List<Model_dadosSalas> lista_deletarSalasBloco = realm.where(Model_dadosSalas.class)
                        .equalTo("Fk_letraBloco", blocoEscolhido)
                        .findAll().sort("Fk_letraBloco").sort("numSala");
                final ListView lista = (ListView) rowView.findViewById(R.id.lv_listarSalas_doBloco);
                ListarSalasParaDeletarAdapter adapter = new ListarSalasParaDeletarAdapter(container.getContext(), lista_deletarSalasBloco);
                lista.setAdapter(adapter);
                lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                        final Model_dadosSalas salaEscolhida = (Model_dadosSalas) parent.getItemAtPosition(position);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final RealmResults<Model_reservarSalas> buscarAgend = realm.where(Model_reservarSalas.class)
                                .equalTo("FkSalas_letraBloco", salaEscolhida.getFk_letraBloco())
                                .equalTo("FkSala_numSala", salaEscolhida.getNumSala())
                                .findAll();
                        if(buscarAgend.isEmpty()==false){
                            builder.setMessage("Deseja deletar esta sala?\n(Possui agendamentos)\n")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            int salaDeletada;
                                            String blocoDeletado;
                                            realm.beginTransaction();
                                            Model_dadosSalas excluirSala = realm.where(Model_dadosSalas.class)
                                                    .equalTo("Fk_letraBloco", blocoEscolhido)
                                                    .equalTo("numSala", salaEscolhida.getNumSala())
                                                    .findFirst();
                                            blocoDeletado = excluirSala.getFk_letraBloco();
                                            excluirSala.deleteFromRealm();
                                            realm.commitTransaction();

                                            realm.beginTransaction();
                                            RealmResults<Model_dadosSalas> buscarBlocoSala = realm.where(Model_dadosSalas.class)
                                                    .equalTo("Fk_letraBloco", blocoDeletado)
                                                    .findAll();
                                            realm.commitTransaction();

                                            if(buscarBlocoSala.isEmpty()){
                                                realm.beginTransaction();
                                                RealmResults excluirBloco = realm.where(Model_dadosBlocos.class)
                                                        .equalTo("letraBloco", blocoDeletado)
                                                        .findAll();
                                                excluirBloco.deleteAllFromRealm();
                                                realm.commitTransaction();
                                            }

                                            realm.beginTransaction();
                                            buscarAgend.deleteAllFromRealm();
                                            realm.commitTransaction();

                                            Toast.makeText(getContext(),
                                                    "Posicao = "+position+" Sala \""+blocoDeletado+"\" deletada com sucesso.",
                                                    Toast.LENGTH_SHORT).show();
                                            onResume();
                                        }
                                    })
                                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            onResume();
                                        }
                                    });
                            builder.create();
                            builder.show();
                        }else{
                            builder.setMessage("Deseja deletar esta sala?")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            int salaDeletada;
                                            String blocoDeletado;
                                            realm.beginTransaction();
                                            Model_dadosSalas excluirSala = realm.where(Model_dadosSalas.class)
                                                    .equalTo("Fk_letraBloco", blocoEscolhido)
                                                    .equalTo("numSala", salaEscolhida.getNumSala())
                                                    .findFirst();
                                            blocoDeletado = excluirSala.getFk_letraBloco();
                                            excluirSala.deleteFromRealm();
                                            realm.commitTransaction();

                                            realm.beginTransaction();
                                            RealmResults<Model_dadosSalas> buscarBlocoSala = realm.where(Model_dadosSalas.class)
                                                    .equalTo("Fk_letraBloco", blocoDeletado)
                                                    .findAll();
                                            realm.commitTransaction();

                                            if(buscarBlocoSala.isEmpty()){
                                                realm.beginTransaction();
                                                RealmResults excluirBloco = realm.where(Model_dadosBlocos.class)
                                                        .equalTo("letraBloco", blocoDeletado)
                                                        .findAll();
                                                excluirBloco.deleteAllFromRealm();
                                                realm.commitTransaction();
                                            }

                                            Toast.makeText(getContext(),
                                                    "Posicao = "+position+" Sala \""+blocoDeletado+"\" deletada com sucesso.",
                                                    Toast.LENGTH_SHORT).show();
                                            onResume();
                                        }
                                    })
                                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            onResume();
                                        }
                                    });
                            builder.create();
                            builder.show();
                        }

                        return false;
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rowView;
    }

    @Override
    public void onResume() {
        List<Model_dadosSalas> lista_deletarSalasBloco = realm.where(Model_dadosSalas.class)
                .equalTo("Fk_letraBloco", blocoEscolhido)
                .findAll().sort("Fk_letraBloco").sort("numSala");
        final ListView lista = (ListView) rowView.findViewById(R.id.lv_listarSalas_doBloco);
        ListarSalasParaDeletarAdapter adapter = new ListarSalasParaDeletarAdapter(getContext(), lista_deletarSalasBloco);
        lista.setAdapter(adapter);
        super.onResume();

    }

    //    @Override
//    public void onStart() {
//        super.onStart();
//        AtualizarStatusBlocoSala.atualizarStatusSalas(this.realm, getContext());
//    }

}
