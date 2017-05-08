package Frag_adm_menu_add_blocos_e_salas;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import ArraysAdapters.ListarBlocosAdapter;
import ArraysAdapters.ListarSalasAdapter;
import com.example.leony.np2_usando_realm.R;

import java.util.ArrayList;
import java.util.List;

import Model_dados_de_tabelas.Model_dadosBlocos;
import Model_dados_de_tabelas.Model_dadosSalas;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListarSalasFragment extends Fragment {
    Fragment myFrag;
    private Realm realm;
    List<Model_dadosSalas> lista_dadosSalas;

    public ListarSalasFragment() {
        // Required empty public constructor
    }

    public ListarSalasFragment(Fragment myFrag) {
        this.myFrag = myFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rowView = inflater.inflate(R.layout.fragment_adm_listar_salas, container, false);

        //Toast.makeText(container.getContext(), "LETRA: "+myFrag.getArguments().get("LETRA_BLOCO"), Toast.LENGTH_SHORT).show();

        realm = Realm.getDefaultInstance();

        ListView lista = (ListView) rowView.findViewById(R.id.lvListarSalas_menu_add_blocos_e_salas);
        lista_dadosSalas = realm.where(Model_dadosSalas.class)
                .equalTo("Fk_letraBloco", myFrag.getArguments().get("LETRA_BLOCO").toString())
                .findAllSorted("numSala");
        ListarSalasAdapter adapter = new ListarSalasAdapter(container.getContext(), lista_dadosSalas);
        lista.setAdapter(adapter);

        FloatingActionButton fab_voltarListarBlocos = (FloatingActionButton) rowView.findViewById(R.id.fab_voltarListarBlocos);
        fab_voltarListarBlocos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content, new ListarBlocosFragment()).commit();
            }
        });

        fab_voltarListarBlocos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(container.getContext(), "Voltar para \"Adicionar Bloco\"", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return rowView;
    }

}
