package Frag_adm_menu_add_usuarios;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.leony.np2_usando_realm.AtualizarStatusBlocoSala;
import com.example.leony.np2_usando_realm.R;

import Model_dados_de_tabelas.Model_dadosUsuarios;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddUsuarioFragment extends Fragment {
    Realm realm;

    public AddUsuarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rowView = inflater.inflate(R.layout.fragment_adm_add_dados_usuarios, container, false);

        realm = Realm.getDefaultInstance();

        final EditText edt_login = (EditText) rowView.findViewById(R.id.edt_addLoginTelaAddUsuario);
        final EditText edt_senha = (EditText) rowView.findViewById(R.id.edt_addSenhaTelaAddUsuario);
        final EditText edt_ConfirmarSenha = (EditText) rowView.findViewById(R.id.edt_addConfSenhaUsuario);

        final RadioButton rb_ADM_0 = (RadioButton) rowView.findViewById(R.id.rb_ADM_0);
        final RadioButton rb_ALUNO_1 = (RadioButton) rowView.findViewById(R.id.rb_ALUNO_1);
        final RadioButton rb_PROF_2 = (RadioButton) rowView.findViewById(R.id.rb_PROF_2);
        final RadioGroup radioGroup = (RadioGroup) rowView.findViewById(R.id.radioGroup_realCad);

        final String loginUs = edt_login.getText().toString();
        final String senhaUs = edt_senha.getText().toString();
        final String confirmarSenha = edt_ConfirmarSenha.getText().toString();

        Button btn_listarUsuarios = (Button) rowView.findViewById(R.id.btn_listarUsuarios);
        btn_listarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content, new ListarUsuariosFragment()).commit();
            }
        });

        Button btn_salvarUs = (Button) rowView.findViewById(R.id.btn_salvarUs);
        btn_salvarUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int escolherTipoUS = -1;
                if (rb_ADM_0.isChecked()==true){
                    escolherTipoUS = 0;
                }else if (rb_ALUNO_1.isChecked()==true){
                    escolherTipoUS = 1;
                }else if (rb_PROF_2.isChecked()==true){
                    escolherTipoUS = 2;
                }
                RealmResults<Model_dadosUsuarios> result2 = realm.where(Model_dadosUsuarios.class)
                        .contains("idUsuario", loginUs+escolherTipoUS, Case.INSENSITIVE)
                        .findAll();

                if((edt_senha.getText().toString().equals("")||
                        edt_ConfirmarSenha.getText().toString().equals("")||
                        edt_login.getText().toString().equals("")&&radioGroup.getCheckedRadioButtonId() == -1)){
                    Toast.makeText(getContext(), "Informe um usuário válido. ", Toast.LENGTH_SHORT).show();

                }else if(edt_senha.getText().toString().equals(edt_ConfirmarSenha.getText().toString())==false){
                    Toast.makeText(getContext(), "Digite a mesma senha em \"Confirmar Senha\"!!!", Toast.LENGTH_SHORT).show();
                }else if (result2.isEmpty()==false){
                    Toast.makeText(getContext(), "Usuário já cadastrado", Toast.LENGTH_SHORT).show();
                }else{
                    Model_dadosUsuarios dadosUsuarios = new Model_dadosUsuarios();

                    if (rb_ADM_0.isChecked()==true){
                        int tipoUsEscolhido = 0;
                        dadosUsuarios.setLogin(edt_login.getText().toString());
                        dadosUsuarios.setSenha(edt_senha.getText().toString());
                        dadosUsuarios.setTipo(tipoUsEscolhido);
                        dadosUsuarios.setIdUsuario(edt_login.getText().toString(), String.valueOf(tipoUsEscolhido));

                        realm.beginTransaction();
                        realm.copyToRealm(dadosUsuarios);
                        realm.commitTransaction();

                        Toast.makeText(getContext(), "Salvo com sucesso!!! = "+edt_login.getText().toString(), Toast.LENGTH_SHORT).show();

                    } else if (rb_ALUNO_1.isChecked()==true){
                        int tipoUsEscolhido = 1;
                        dadosUsuarios.setLogin(edt_login.getText().toString());
                        dadosUsuarios.setSenha(edt_senha.getText().toString());
                        dadosUsuarios.setTipo(tipoUsEscolhido);
                        dadosUsuarios.setIdUsuario(edt_login.getText().toString(), String.valueOf(tipoUsEscolhido));

                        realm.beginTransaction();
                        realm.copyToRealm(dadosUsuarios);
                        realm.commitTransaction();

                        Toast.makeText(getActivity(), "Salvo com sucesso!!!", Toast.LENGTH_SHORT).show();

                    } else if (rb_PROF_2.isChecked()==true){

                        int tipoUsEscolhido = 2;
                        dadosUsuarios.setLogin(edt_login.getText().toString());
                        dadosUsuarios.setSenha(edt_senha.getText().toString());
                        dadosUsuarios.setTipo(tipoUsEscolhido);
                        dadosUsuarios.setIdUsuario(edt_login.getText().toString(), String.valueOf(tipoUsEscolhido));

                        realm.beginTransaction();
                        realm.copyToRealm(dadosUsuarios);
                        realm.commitTransaction();

                        Toast.makeText(getActivity(), "Salvo com sucesso!!!", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getActivity(), "Escolha um tipo de Usuário!!!", Toast.LENGTH_SHORT).show();
                    }
                }

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
