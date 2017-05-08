package com.example.leony.np2_usando_realm;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import Frag_adm_menu_add_blocos_e_salas.AddBlocoFragment;
import Frag_adm_menu_add_usuarios.AddUsuarioFragment;

public class TelaMenuUsuarioActivity extends AppCompatActivity {
    FragmentManager fm;
    TextView txt_usu_menuVisualizacao, txt_usu_menuAlterarDados;

    BottomNavigationView navigation1, navigation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        txt_usu_menuVisualizacao = (TextView) findViewById(R.id.txt_usu_menuVisualizacao);
        txt_usu_menuAlterarDados = (TextView) findViewById(R.id.txt_usu_menuAlterarDados);


        fm = getSupportFragmentManager();
//        txt_usu_menuVisualizacao.setBackgroundColor(R.attr.colorPrimary);
//        txt_usu_menuAlterarDados.setBackgroundColor(255);

        //fm.beginTransaction().replace(R.id.content, new AddBlocoFragment()).commit();

        navigation1 = (BottomNavigationView) findViewById(R.id.btn_us_Navigation1);
        unCheckAllMenuItems(navigation1.getMenu());
        navigation1.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener1);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener1
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.us_nv_visualizar:
                    txt_usu_menuVisualizacao.setBackgroundColor(R.attr.colorPrimary);
                    txt_usu_menuAlterarDados.setBackgroundColor(255);

                    //fm.beginTransaction().replace(R.id.content_telaUsuario, new AddBlocoFragment()).commit();
                    return true;
                case R.id.us_nv_alterarDados:
                    txt_usu_menuVisualizacao.setBackgroundColor(255);
                    txt_usu_menuAlterarDados.setBackgroundColor(R.attr.colorPrimary);

                    //fm.beginTransaction().replace(R.id.content, new AddUsuarioFragment()).commit();
                    return true;

            }
            return false;
        }

    };

    private void unCheckAllMenuItems(@NonNull final Menu menu) {
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            final MenuItem item = menu.getItem(i);
            if(item.hasSubMenu()) {
                // Un check sub menu items
                unCheckAllMenuItems(item.getSubMenu());
            } else {
                item.setChecked(false);
            }
        }
    }
}
