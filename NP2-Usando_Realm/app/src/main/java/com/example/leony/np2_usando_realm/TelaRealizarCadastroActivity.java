package com.example.leony.np2_usando_realm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import Model_dados_de_tabelas.Model_dadosUsuarios;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

public class TelaRealizarCadastroActivity extends AppCompatActivity {
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_cadastro);
        realm = Realm.getDefaultInstance();

        final EditText edt_cadLogin = (EditText) findViewById(R.id.edt_cadLogin);
        final EditText edt_cadSenha = (EditText) findViewById(R.id.edt_cadSenha);
        final EditText edt_cadConfirmarSenha = (EditText) findViewById(R.id.edt_cadConfirmarSenha);

        final RadioButton rb_realCad_ADM_0 = (RadioButton) findViewById(R.id.rb_realCad_ADM_0);
        final RadioButton rb_realCad_ALUNO_1 = (RadioButton) findViewById(R.id.rb_realCad_ALUNO_1);
        final RadioButton rb_realCad_PROF_2 = (RadioButton) findViewById(R.id.rb_realCad_PROF_2);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup_realCad);

        final String loginUs = edt_cadLogin.getText().toString();
        final String senhaUs = edt_cadSenha.getText().toString();
        final String confirmarSenha = edt_cadConfirmarSenha.getText().toString();

        Button btn_cadastrar = (Button) findViewById(R.id.btn_cadastrar);
        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<Model_dadosUsuarios> result2 = realm.where(Model_dadosUsuarios.class)
                        .contains("idUsuario", loginUs+senhaUs, Case.INSENSITIVE).findAll();
                if((edt_cadSenha.getText().toString().equals("")||
                        edt_cadConfirmarSenha.getText().toString().equals("")||
                        edt_cadLogin.getText().toString().equals("")&&radioGroup.getCheckedRadioButtonId() == -1)){
                    Toast.makeText(getBaseContext(), "Informe um usuário válido. ", Toast.LENGTH_SHORT).show();

                }else if(edt_cadSenha.getText().toString().equals(edt_cadConfirmarSenha.getText().toString())==false){
                    Toast.makeText(getBaseContext(), "Digite a mesma senha em \"Confirmar Senha\"!!!", Toast.LENGTH_SHORT).show();
                }else if (result2.size()>0){
                    Toast.makeText(getBaseContext(), "Usuário já cadastrado", Toast.LENGTH_SHORT).show();
                }else{
                    Model_dadosUsuarios dadosUsuarios = new Model_dadosUsuarios();

                    if (rb_realCad_ADM_0.isChecked()==true){
                        int tipoUsEscolhido = 0;
                        dadosUsuarios.setLogin(loginUs);
                        dadosUsuarios.setSenha(senhaUs);
                        dadosUsuarios.setTipo(tipoUsEscolhido);
                        dadosUsuarios.setIdUsuario(loginUs, senhaUs);

                        realm.beginTransaction();
                        realm.copyToRealm(dadosUsuarios);
                        realm.commitTransaction();

                        Toast.makeText(getBaseContext(), "Salvo com sucesso!!!", Toast.LENGTH_SHORT).show();

                    } else if (rb_realCad_ALUNO_1.isChecked()==true){
                        int tipoUsEscolhido = 1;
                        dadosUsuarios.setLogin(loginUs);
                        dadosUsuarios.setSenha(senhaUs);
                        dadosUsuarios.setTipo(tipoUsEscolhido);
                        dadosUsuarios.setIdUsuario(loginUs, senhaUs);

                        realm.beginTransaction();
                        realm.copyToRealm(dadosUsuarios);
                        realm.commitTransaction();

                        Toast.makeText(getBaseContext(), "Salvo com sucesso!!!", Toast.LENGTH_SHORT).show();

                    } else if (rb_realCad_PROF_2.isChecked()==true){
                        int tipoUsEscolhido = 2;
                        dadosUsuarios.setLogin(loginUs);
                        dadosUsuarios.setSenha(senhaUs);
                        dadosUsuarios.setTipo(tipoUsEscolhido);
                        dadosUsuarios.setIdUsuario(loginUs, senhaUs);

                        realm.beginTransaction();
                        realm.copyToRealm(dadosUsuarios);
                        realm.commitTransaction();

                        Toast.makeText(getBaseContext(), "Salvo com sucesso!!!", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getBaseContext(), "Escolha um tipo de Usuário!!!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        Button btn_limparCampos = (Button) findViewById(R.id.btn_limparCampos);
        btn_limparCampos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_cadLogin.setText("");
                edt_cadSenha.setText("");
                edt_cadConfirmarSenha.setText("");
            }
        });

    }
}
