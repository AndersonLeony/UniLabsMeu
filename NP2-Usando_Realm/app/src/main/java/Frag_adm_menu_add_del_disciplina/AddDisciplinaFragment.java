package Frag_adm_menu_add_del_disciplina;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leony.np2_usando_realm.AtualizarStatusBlocoSala;
import com.example.leony.np2_usando_realm.R;

import Model_dados_de_tabelas.Model_dadosDisciplinas;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDisciplinaFragment extends Fragment {

    Realm realm;

    public AddDisciplinaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rowView = inflater.inflate(R.layout.fragment_adm_add_disciplina, container, false);

        realm = Realm.getDefaultInstance();

        final EditText txt_nomeDisciplina = (EditText) rowView.findViewById(R.id.edt_nomeDisciplina);
        final EditText txt_nomeProfessor = (EditText) rowView.findViewById(R.id.edt_nomeProfessor);
        Button btn_salvarAddDiscip = (Button) rowView.findViewById(R.id.btn_salvarAddDiscip);
        Button btn_listarDiscCad = (Button) rowView.findViewById(R.id.btn_listarDiscCad);

        btn_salvarAddDiscip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<Model_dadosDisciplinas> pesquisarDisc = realm.where(Model_dadosDisciplinas.class)
                        .findAll();
                if(pesquisarDisc.isEmpty() == true){
                    realm.beginTransaction();
                    Model_dadosDisciplinas dadosDiscAdd = new Model_dadosDisciplinas(1,
                            txt_nomeDisciplina.getText().toString(), txt_nomeProfessor.getText().toString());
                    realm.copyToRealm(dadosDiscAdd);
                    realm.commitTransaction();
                    Toast.makeText(getContext(), "Disciplina cadastrada com sucesso!!!", Toast.LENGTH_SHORT).show();

                }else{
                    RealmResults<Model_dadosDisciplinas> pesquisarDiscAdd = realm.where(Model_dadosDisciplinas.class)
                            .equalTo("nomeDisciplina", txt_nomeDisciplina.getText().toString())
                            .equalTo("nomeProfessor", txt_nomeProfessor.getText().toString())
                            .findAll();
                    if(pesquisarDiscAdd.isEmpty() == true){
                        int idUltimoElem = pesquisarDisc.get(pesquisarDisc.size()-1).getCod();
                        realm.beginTransaction();
                        Model_dadosDisciplinas dadosDiscAdd = new Model_dadosDisciplinas(idUltimoElem+1,
                                txt_nomeDisciplina.getText().toString(), txt_nomeProfessor.getText().toString());
                        realm.copyToRealm(dadosDiscAdd);
                        realm.commitTransaction();
                        Toast.makeText(getContext(), "Disciplina cadastrada com sucesso!!!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Disciplina já existente", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btn_listarDiscCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content, new ListarDisciplinasFragment()).commit();
            }
        });

            return rowView;
        }

//    @Override
//    public void onStart() {
//        super.onStart();
//        AtualizarStatusBlocoSala.atualizarStatusSalas(Realm.getDefaultInstance(), getContext());
//    }

}
