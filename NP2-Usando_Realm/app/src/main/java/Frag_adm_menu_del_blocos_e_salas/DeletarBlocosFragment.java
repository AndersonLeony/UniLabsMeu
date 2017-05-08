package Frag_adm_menu_del_blocos_e_salas;


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

import ArraysAdapters.ListarBlocosAdapter;

import com.example.leony.np2_usando_realm.AtualizarStatusBlocoSala;
import com.example.leony.np2_usando_realm.R;

import java.util.List;

import Model_dados_de_tabelas.Model_dadosBlocos;
import Model_dados_de_tabelas.Model_dadosSalas;
import Model_dados_de_tabelas.Model_reservarSalas;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeletarBlocosFragment extends Fragment {
    List<Model_dadosBlocos> lista_dadosBlocos;
    List<Model_dadosSalas> lista_dadosSalas;
    private static final int IDENTIFICADOR_B = 1;
    private int pegarPosicaoLongClick;
    View rowView;

    public DeletarBlocosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rowView = inflater.inflate(R.layout.fragment_adm_del_blocos, container, false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //bd = new BDSQLiteHelper(getContext());
        final Realm realm = Realm.getDefaultInstance();
        lista_dadosSalas = realm.where(Model_dadosSalas.class).findAll();
        final ListView lista = (ListView) rowView.findViewById(R.id.lvListarBlocos_deletarBlocos);
        lista_dadosBlocos = realm.where(Model_dadosBlocos.class).findAll().sort("letraBloco");
        final ListarBlocosAdapter adapter = new ListarBlocosAdapter(getContext(), lista_dadosBlocos);
        lista.setAdapter(adapter);

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String blocoEscolhido = lista_dadosBlocos.get(position).getLetraBloco();
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final RealmResults<Model_reservarSalas> buscarAgend = realm.where(Model_reservarSalas.class)
                        .equalTo("FkSalas_letraBloco", blocoEscolhido)
                        .findAll();
                Toast.makeText(getContext(), "lll = "+buscarAgend.isEmpty(), Toast.LENGTH_SHORT).show();
                if(buscarAgend.isEmpty()==false) {
                    builder.setMessage("Deseja deletar este Bloco?\n(Possui aulas agendadas)")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String blocoDeletado;

                                    realm.beginTransaction();
                                    RealmResults excluirSala = realm.where(Model_dadosSalas.class)
                                            .equalTo("Fk_letraBloco", blocoEscolhido)
                                            .findAll();
                                    excluirSala.deleteAllFromRealm();
                                    realm.commitTransaction();

                                    realm.beginTransaction();
                                    RealmResults excluirBloco = realm.where(Model_dadosBlocos.class)
                                            .equalTo("letraBloco", blocoEscolhido)
                                            .findAll();
                                    excluirBloco.deleteAllFromRealm();
                                    realm.commitTransaction();

                                    realm.beginTransaction();
                                    buscarAgend.deleteAllFromRealm();
                                    realm.commitTransaction();

                                    Toast.makeText(getContext(),
                                            " Bloco \"" + blocoEscolhido + "\" deletado com sucesso.",
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
                    builder.setMessage("Deseja deletar o blobo '"+lista_dadosBlocos.get(position).getLetraBloco()+"'?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String bloco_a_ser_Deletado = lista_dadosBlocos.get(position).getLetraBloco();
                                    realm.beginTransaction();
                                    RealmResults<Model_dadosBlocos> excluirBloco = realm.where(Model_dadosBlocos.class)
                                            .equalTo("letraBloco", lista_dadosBlocos.get(position).getLetraBloco())
                                            .findAll().sort("letraBloco");
                                    RealmResults<Model_dadosSalas> excluirSala = realm.where(Model_dadosSalas.class)
                                            .equalTo("Fk_letraBloco", lista_dadosBlocos.get(position).getLetraBloco())
                                            .findAll().sort("Fk_letraBloco");
                                    excluirSala.deleteAllFromRealm();
                                    excluirBloco.deleteAllFromRealm();
                                    realm.commitTransaction();
                                    onResume();

                                    Toast.makeText(getContext(), "Bloco \""+bloco_a_ser_Deletado+"\" deletado com sucesso.", Toast.LENGTH_SHORT).show();
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

        return rowView;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        AtualizarStatusBlocoSala.atualizarStatusSalas(Realm.getDefaultInstance(), getContext());
//    }

    @Override
    public void onResume() {
        Realm realm = Realm.getDefaultInstance();
        ListView lista = (ListView) rowView.findViewById(R.id.lvListarBlocos_deletarBlocos);
        lista_dadosBlocos = realm.where(Model_dadosBlocos.class).findAll();
        ListarBlocosAdapter adapter = new ListarBlocosAdapter(getContext(), lista_dadosBlocos);
        lista.setAdapter(adapter);
        super.onResume();
    }

}
