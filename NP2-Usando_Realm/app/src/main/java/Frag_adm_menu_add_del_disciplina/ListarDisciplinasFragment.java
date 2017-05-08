package Frag_adm_menu_add_del_disciplina;


import android.content.DialogInterface;
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

import com.example.leony.np2_usando_realm.R;

import java.util.List;

import ArraysAdapters.ListarBlocosAdapter;
import ArraysAdapters.ListarDisciplinasAdapter;
import Model_dados_de_tabelas.Model_dadosBlocos;
import Model_dados_de_tabelas.Model_dadosDisciplinas;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListarDisciplinasFragment extends Fragment {
    List<Model_dadosDisciplinas> lista_dadosDisciplinas;
    View rowView;
    public ListarDisciplinasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rowView = inflater.inflate(R.layout.fragment_adm_listar_disciplinas, container, false);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        RealmConfiguration config = new RealmConfiguration.Builder(getContext())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        final Realm realm = Realm.getDefaultInstance();
        lista_dadosDisciplinas = realm.where(Model_dadosDisciplinas.class).findAll();

        ListView listaDiscipListView = (ListView) rowView.findViewById(R.id.lvListarDisciplinas);
        ListarDisciplinasAdapter adapter = new ListarDisciplinasAdapter(container.getContext(), lista_dadosDisciplinas);
        listaDiscipListView.setAdapter(adapter);

        listaDiscipListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                builder.setMessage("Deseja deletar:\nDiscip: "
                        +lista_dadosDisciplinas.get(position).getNomeDisciplina()+
                        "\nProf.: " +lista_dadosDisciplinas.get(position).getNomeProfessor()+"?")

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                realm.beginTransaction();
                                RealmResults<Model_dadosDisciplinas> deletarDiscp = realm.where(Model_dadosDisciplinas.class)
                                        .equalTo("cod", lista_dadosDisciplinas.get(position).getCod()).findAll();
                                deletarDiscp.deleteAllFromRealm();
                                realm.commitTransaction();
                                onResume();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                builder.create();
                builder.show();

                return false;
            }
        });


        FloatingActionButton fab_voltarCadastroDiscip = (FloatingActionButton) rowView.findViewById(R.id.fab_voltarCadastroDiscip);
        fab_voltarCadastroDiscip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.content, new AddDisciplinaFragment()).commit();

            }
        });
        // Inflate the layout for this fragment
        return rowView;
    }

    @Override
    public void onResume() {
        Realm realm = Realm.getDefaultInstance();
        ListView listaDiscipListView = (ListView) rowView.findViewById(R.id.lvListarDisciplinas);
        lista_dadosDisciplinas = realm.where(Model_dadosDisciplinas.class).findAll().sort("cod");
        ListarDisciplinasAdapter adapter = new ListarDisciplinasAdapter(getContext(), lista_dadosDisciplinas);
        listaDiscipListView.setAdapter(adapter);
        super.onResume();
    }
}
