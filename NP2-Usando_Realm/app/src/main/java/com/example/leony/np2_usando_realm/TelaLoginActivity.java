package com.example.leony.np2_usando_realm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Model_dados_de_tabelas.Model_dadosUsuarios;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TelaLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RealmConfiguration config = new RealmConfiguration.Builder(getBaseContext())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        final Realm realm = Realm.getDefaultInstance();

        EditText edt_login = (EditText) findViewById(R.id.edt_addLoginTelaLogin);
        final String login = edt_login.getText().toString();
        EditText edt_senha = (EditText) findViewById(R.id.edt_addSenhaTelaAddUsuario);
        final String senha = edt_senha.getText().toString();
        final TextView txt_realizarCadastro = (TextView) findViewById(R.id.txt_realizarCadastro);

        Button btn_entrar = (Button) findViewById(R.id.btn_entrar);
        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model_dadosUsuarios buscarUsuario = realm.where(Model_dadosUsuarios.class)
                        .equalTo("idUsuario", login+senha).findFirst();

                if(buscarUsuario!=null){
                    if(buscarUsuario.getTipo()==0){
                        Bundle bundle = new Bundle();
                        bundle.putString("LoginUsuario", buscarUsuario.getLogin());
                        bundle.putString("TipoUsuario", buscarUsuario.getLogin());
                        Intent it = new Intent(TelaLoginActivity.this, TelaMenuADMActivity.class);
                        it.putExtras(bundle);
                        startActivity(it);
                    }
                    Toast.makeText(getBaseContext(), "Logado com sucesso! = ", Toast.LENGTH_SHORT).show();
                    //Intent it = new Intent(this, )
                }else{
                    Toast.makeText(getBaseContext(), "Usuario não cadastrado = ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        txt_realizarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaLoginActivity.this, TelaRealizarCadastroActivity.class);
                startActivity(it);

            }
        });

    }
}
