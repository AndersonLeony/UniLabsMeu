package Frag_adm_menu_add_blocos_e_salas;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import ArraysAdapters.ListarBlocosAdapter;
import com.example.leony.np2_usando_realm.R;

import java.util.ArrayList;
import java.util.List;

import Model_dados_de_tabelas.Model_dadosBlocos;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListarBlocosFragment extends Fragment {
    List<Model_dadosBlocos> lista_dadosBlocos;
    private Realm realm;
    private static final int IDENTIFICADOR_B = 1;
    private int pegarPosicaoLongClick;
    private View rowView;
    private Fragment myFrag;


    public ListarBlocosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        rowView = inflater.inflate(R.layout.fragment_adm_listar_blocos, container, false);
        RealmConfiguration config = new RealmConfiguration.Builder(getContext())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();

        ListView lista = (ListView) rowView.findViewById(R.id.lvListarBlocos);
        lista_dadosBlocos = realm.where(Model_dadosBlocos.class).findAll().sort("letraBloco");
        ListarBlocosAdapter adapter = new ListarBlocosAdapter(container.getContext(), lista_dadosBlocos);
        lista.setAdapter(adapter);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //este código pega o valor da letra do bloco e envia para o outro fragment
                myFrag = new ListarSalasFragment();
                //Toast.makeText(container.getContext(), "LETRA: "+lista_dadosBlocos.get(position).getLetraBloco(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("LETRA_BLOCO", lista_dadosBlocos.get(position).getLetraBloco());
                myFrag.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.content, new ListarSalasFragment(myFrag)).commit();

            }
        });


        FloatingActionButton fab_voltarAddBloco = (FloatingActionButton) rowView.findViewById(R.id.fab_voltarAddBloco);
        fab_voltarAddBloco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content, new AddBlocoFragment()).commit();
            }
        });

        fab_voltarAddBloco.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(container.getContext(), "Voltar para \"Adicionar Bloco\"", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return rowView;
    }

}
