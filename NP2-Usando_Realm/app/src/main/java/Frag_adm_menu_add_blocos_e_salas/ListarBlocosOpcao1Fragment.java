package Frag_adm_menu_add_blocos_e_salas;


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

import com.example.leony.np2_usando_realm.R;

import java.util.ArrayList;
import java.util.List;

import ArraysAdapters.ListarBlocosOpcao1Adapter;
import Model_dados_de_tabelas.Model_dadosBlocos;
import Model_dados_de_tabelas.Model_dadosSalas;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListarBlocosOpcao1Fragment extends Fragment {
    private Spinner sp_deletarSala_ListaBlocos;
    List<String> lista_dadosBlocos;
    RealmResults<Model_dadosBlocos> buscaBlocos;
    RealmResults<Model_dadosSalas> buscaSalas;
    Realm realm;
    String blocoEscolhido;
    View rowView, rowView2;

    public ListarBlocosOpcao1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rowView = inflater.inflate(R.layout.fragment_adm_listar_blocos_add_opcao1, container, false);
        rowView2 = inflater.inflate(R.layout.layout_linha_lista_salas_adm_add_bloco, container, false);

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
                final ListView lista = (ListView) rowView.findViewById(R.id.lv_listarSalas_doBloco);
                List<Model_dadosSalas> lista_deletarSalasBloco = realm.where(Model_dadosSalas.class)
                        .equalTo("Fk_letraBloco", parent.getItemAtPosition(posicao).toString())
                        .findAll().sort("numSala");
                ListarBlocosOpcao1Adapter adapter = new ListarBlocosOpcao1Adapter(container.getContext(), lista_deletarSalasBloco);
                lista.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton fab_voltarAddBlocoOpcao2 = (FloatingActionButton) rowView.findViewById(R.id.fab_voltarAddBlocoOpcao2);
        fab_voltarAddBlocoOpcao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content, new AddBlocoFragment()).commit();
            }
        });

        fab_voltarAddBlocoOpcao2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(container.getContext(), "Voltar para \"Adicionar Bloco\"", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return rowView;
    }

}
