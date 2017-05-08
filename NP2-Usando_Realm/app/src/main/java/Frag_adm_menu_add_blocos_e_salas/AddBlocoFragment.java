package Frag_adm_menu_add_blocos_e_salas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.leony.np2_usando_realm.AtualizarStatusBlocoSala;
import com.example.leony.np2_usando_realm.R;

import java.util.List;

import Model_dados_de_tabelas.Model_dadosBlocos;
import Model_dados_de_tabelas.Model_dadosSalas;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class AddBlocoFragment extends Fragment {
    Realm realm;
    List<Model_dadosBlocos> lista_dadosBlocos;
    List<Model_dadosSalas> lista_dadosSalas;
    private static final int IDENTIFICADOR_B = 1;
    private int pegarPosicaoLongClick;
    View rowView, rowView_ListarDadosBlocos, rowView_ListarDadosSalas;

    public AddBlocoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rowView = inflater.inflate(R.layout.fragment_adm_add_dados_bloco, container, false);
        rowView_ListarDadosBlocos = inflater.inflate(R.layout.fragment_adm_listar_blocos, container, false);
        rowView_ListarDadosSalas = inflater.inflate(R.layout.fragment_adm_listar_salas, container, false);

        RealmConfiguration config = new RealmConfiguration.Builder(getContext())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();

        lista_dadosBlocos = realm.where(Model_dadosBlocos.class).findAll();
        lista_dadosSalas = realm.where(Model_dadosSalas.class).findAll();

        final Spinner sp_letraBloco = (Spinner) rowView.findViewById(R.id.sp_letraBloco);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.listaBlocos, R.layout.support_simple_spinner_dropdown_item);
        sp_letraBloco.setAdapter(adapter);

        final Spinner sp_numSala = (Spinner) rowView.findViewById(R.id.sp_numSala);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.numSala, R.layout.support_simple_spinner_dropdown_item);
        sp_numSala.setAdapter(adapter2);

        final Spinner sp_qtdeComp = (Spinner) rowView.findViewById(R.id.sp_qtdeComp);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.qtdeComp, R.layout.support_simple_spinner_dropdown_item);
        sp_qtdeComp.setAdapter(adapter3);

        Button btn_gravar = (Button) rowView.findViewById(R.id.btn_gravar);
        btn_gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int numSalaEscolhido = Integer.parseInt(sp_numSala.getSelectedItem().toString());
                final String letraBlocoEscolhido = sp_letraBloco.getSelectedItem().toString();
                final int qtdeCompEscolhido = Integer.parseInt(sp_qtdeComp.getSelectedItem().toString());
                int pos_qtdeCompEscolhido= sp_qtdeComp.getSelectedItemPosition();

                Model_dadosBlocos pesquisarBloco = realm.where(Model_dadosBlocos.class)
                        .equalTo("letraBloco", letraBlocoEscolhido).findFirst();
                Model_dadosSalas pesquisarSala = realm.where(Model_dadosSalas.class)
                        .equalTo("Fk_letraBloco", letraBlocoEscolhido)
                        .equalTo("numSala", numSalaEscolhido)
                        .findFirst();

                Model_dadosBlocos dadosBloco = new Model_dadosBlocos();
                Model_dadosSalas dadosSala = new Model_dadosSalas();

                if (pesquisarBloco==null){
                    realm.beginTransaction();
                    dadosBloco.setLetraBloco(letraBlocoEscolhido);
                    dadosBloco.setStatusBloco(R.drawable.bloco_livre);

                    realm.copyToRealm(dadosBloco);

                    dadosSala = new Model_dadosSalas();
                    dadosSala.setIdSala(letraBlocoEscolhido, numSalaEscolhido);
                    dadosSala.setFk_letraBloco(letraBlocoEscolhido);
                    dadosSala.setNumSala(numSalaEscolhido);
                    for (int i = 0; i<qtdeCompEscolhido; i++){
                        dadosSala.setNomesPcs(letraBlocoEscolhido, numSalaEscolhido, (i+1), R.drawable.pc_livre);
                    }
                    //dadosSala.setStatusSala(R.drawable.sala_livre);
                    realm.copyToRealm(dadosSala);
                    realm.commitTransaction();

                    Toast.makeText(getActivity(),
                            "Bloco "+letraBlocoEscolhido+", Sala: "+numSalaEscolhido+", Qtde pc's = "+qtdeCompEscolhido+"\nAdd com sucesso!!!",
                            Toast.LENGTH_SHORT).show();

                }else if (pesquisarBloco!=null && pesquisarSala==null){
                    realm.beginTransaction();

                    dadosSala = new Model_dadosSalas();
                    dadosSala.setIdSala(letraBlocoEscolhido, numSalaEscolhido);
                    dadosSala.setFk_letraBloco(letraBlocoEscolhido);
                    dadosSala.setNumSala(numSalaEscolhido);
                    for (int i = 0; i<qtdeCompEscolhido; i++){
                        dadosSala.setNomesPcs(letraBlocoEscolhido, numSalaEscolhido, (i+1), R.drawable.pc_livre);
                    }
                    //dadosSala.setStatusSala(R.drawable.sala_livre);
                    realm.copyToRealm(dadosSala);

                    realm.commitTransaction();
                    Toast.makeText(getActivity(),
                            "Bloco "+letraBlocoEscolhido+", Sala: "+numSalaEscolhido+", Qtde pc's = "+qtdeCompEscolhido+"Add com sucesso!!!",
                            Toast.LENGTH_SHORT).show();
                    //getFragmentManager().beginTransaction().replace(R.id.content, new ListarBlocosFragment()).commit();
                }else if (pesquisarBloco!=null && pesquisarSala!=null){
                    Toast.makeText(getActivity(),"Bloco e sala já cadastrados.",Toast.LENGTH_SHORT).show();
                }

            }

        });

        Button btn_listarBlocos = (Button) rowView.findViewById(R.id.btn_listarBlocos1);
        btn_listarBlocos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content, new ListarBlocosOpcao1Fragment()).commit();
            }
        });

        Button btn_listarBlocos2 = (Button) rowView.findViewById(R.id.btn_listarBlocos2);
        btn_listarBlocos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content, new ListarBlocosFragment()).commit();
            }
        });

//        Button btn_editarBlocos = (Button) rowView.findViewById(R.id.btn_editarBlocos);
//        btn_editarBlocos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final int numSalaEscolhido = Integer.parseInt(sp_numSala.getSelectedItem().toString());
//                final String letraBlocoEscolhido = sp_letraBloco.getSelectedItem().toString();
//                final int qtdeCompEscolhido = Integer.parseInt(sp_qtdeComp.getSelectedItem().toString());
//                int pos_qtdeCompEscolhido= sp_qtdeComp.getSelectedItemPosition();
//
//                Model_dadosBlocos pesquisarBloco = realm.where(Model_dadosBlocos.class)
//                        .equalTo("letraBloco", letraBlocoEscolhido).findFirst();
//                Model_dadosSalas pesquisarSala = realm.where(Model_dadosSalas.class)
//                        .equalTo("Fk_letraBloco", letraBlocoEscolhido)
//                        .equalTo("numSala", numSalaEscolhido)
//                        .findFirst();
//                if (pesquisarBloco!=null && pesquisarSala!=null){
//                    Toast.makeText(getActivity(),"Bloco e sala já cadastrados.",Toast.LENGTH_SHORT).show();
//                }else{
//                    Fragment fragm_edtBlocosSalas = new EditarBlocoSalaFragment();
//                    //Toast.makeText(container.getContext(), "LETRA: "+lista_dadosBlocos.get(position).getLetraBloco(), Toast.LENGTH_SHORT).show();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("blocoEditar",pesquisarBloco.getLetraBloco() );
//                    bundle.putString("salaEditar", String.valueOf(pesquisarSala.getNumSala()));
//                    bundle.putString("numPcsSala", String.valueOf(pesquisarSala.getNomesPcs().size()));
//                    fragm_edtBlocosSalas.setArguments(bundle);
//                    getFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content, new EditarBlocoSalaFragment(fragm_edtBlocosSalas));
//
//                }
//
//            }
//        });

        return rowView;
    }

}
