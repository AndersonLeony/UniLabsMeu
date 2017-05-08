package Frag_adm_menu_res_aula;


import android.content.DialogInterface;
import android.os.Bundle;
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

import ArraysAdapters.ListarSalasReservadasAdapter;
import Model_dados_de_tabelas.Model_dadosBlocos;
import Model_dados_de_tabelas.Model_dadosSalas;
import Model_dados_de_tabelas.Model_reservarSalas;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListarReservarAulasFragment extends Fragment {
    View rowView;
    Realm realm;
    List<Model_reservarSalas> buscarSalasReservadas;

    public ListarReservarAulasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rowView = inflater.inflate(R.layout.fragment_adm_listar_reservar_aulas, container, false);
        realm = Realm.getDefaultInstance();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        ListView listaSalasReservadas = (ListView) rowView.findViewById(R.id.lv_listarSalasReservadas);
        buscarSalasReservadas = realm.where(Model_reservarSalas.class).findAll()
                .sort("diaSort")
                .sort("mesSort")
                .sort("anoSort")
                .sort("FkSalas_letraBloco")
                .sort("FkSala_numSala")
                .sort("hrIni");
        ListarSalasReservadasAdapter adapter = new ListarSalasReservadasAdapter(container.getContext(), buscarSalasReservadas);
        listaSalasReservadas.setAdapter(adapter);

        listaSalasReservadas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                builder.setMessage("Deseja deletar esta agendamento?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //String agendamentoDeletado = buscarSalasReservadas.get(position).get
                                realm.beginTransaction();
                                RealmResults <Model_reservarSalas> excluirAgendamento = realm.where(Model_reservarSalas.class)
                                        .equalTo("FkSalas_letraBloco",buscarSalasReservadas.get(position).getFkSalas_letraBloco())
                                        .equalTo("FkSala_numSala",buscarSalasReservadas.get(position).getFkSala_numSala())
                                        .equalTo("data",buscarSalasReservadas.get(position).getData())
                                        .equalTo("turno",buscarSalasReservadas.get(position).getTurno())
                                        .findAll();
                                excluirAgendamento.deleteAllFromRealm();
                                realm.commitTransaction();
                                onResume();
                                Toast.makeText(getContext(), "Agendamento deletado com sucesso.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onResume();
                            }
                        });
                builder.create();
                builder.show();
                return false;
            }
        });

        // Inflate the layout for this fragment
        return rowView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ListView listaSalasReservadas = (ListView) rowView.findViewById(R.id.lv_listarSalasReservadas);
        buscarSalasReservadas = realm.where(Model_reservarSalas.class).findAll().sort("diaSort").sort("mesSort");
        ListarSalasReservadasAdapter adapter = new ListarSalasReservadasAdapter(getContext(), buscarSalasReservadas);
        listaSalasReservadas.setAdapter(adapter);
    }
}
