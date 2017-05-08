package com.example.leony.np2_usando_realm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import Frag_adm_menu_add_blocos_e_salas.AddBlocoFragment;
import Frag_adm_menu_add_del_disciplina.AddDisciplinaFragment;
import Frag_adm_menu_add_usuarios.AddUsuarioFragment;
import Frag_adm_menu_del_blocos_e_salas.DeletarBlocosFragment;
import Frag_adm_menu_del_blocos_e_salas.DeletarSalasFragment;
import Frag_adm_menu_res_aula.ReservarAulasFragment;

public class TelaMenuADMActivity extends AppCompatActivity {
    FragmentManager fm;
    TextView txt_menuAddBloco,txt_menuAddUsuario,
            txt_menuAlterarDados,txt_menuDelBloco,
            txt_menuDelSala,txt_menuAgendarAula;
    BottomNavigationView navigation1, navigation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm);

        Intent it = getIntent();
        //setTitle("Tela Adm - Bem vindo "+it.getExtras().get("LoginUsuario").toString()+"!");

        txt_menuAddBloco = (TextView) findViewById(R.id.txt_adm_menuAddBloco);
        txt_menuAddUsuario = (TextView) findViewById(R.id.txt_adm_menuAddUsuario);
        txt_menuAlterarDados = (TextView) findViewById(R.id.txt_menuAlterarDados);
        txt_menuDelBloco = (TextView) findViewById(R.id.txt_adm_menuDelBloco);
        txt_menuDelSala = (TextView) findViewById(R.id.txt_adm_menuDelSala);
        txt_menuAgendarAula = (TextView) findViewById(R.id.txt_adm_menuAgendarAula);

        fm = getSupportFragmentManager();
        txt_menuAddBloco.setBackgroundColor(R.attr.colorPrimary);
        txt_menuAddUsuario.setBackgroundColor(255);
        txt_menuAlterarDados.setBackgroundColor(255);
        txt_menuDelBloco.setBackgroundColor(255);
        txt_menuDelSala.setBackgroundColor(255);
        txt_menuAgendarAula.setBackgroundColor(255);
        fm.beginTransaction().replace(R.id.content, new AddBlocoFragment()).commit();

        navigation1 = (BottomNavigationView) findViewById(R.id.btn_Navigation1);
        navigation1.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener1);

        navigation2 = (BottomNavigationView) findViewById(R.id.btn_Navigation2);
        navigation2.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener2);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener1
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nv_adicionarBloco:
                    setTitle("Menu Adm - Adcionar Bloco");
                    txt_menuAddBloco.setBackgroundColor(R.attr.colorPrimary);
                    txt_menuAddUsuario.setBackgroundColor(255);
                    txt_menuAlterarDados.setBackgroundColor(255);
                    txt_menuDelBloco.setBackgroundColor(255);
                    txt_menuDelSala.setBackgroundColor(255);
                    txt_menuAgendarAula.setBackgroundColor(255);
                    fm.beginTransaction().replace(R.id.content, new AddBlocoFragment()).commit();
                    return true;
                case R.id.nv_adicionarUsuario:
                    setTitle("Menu Adm - Adcionar Usuário");
                    txt_menuAddBloco.setBackgroundColor(255);
                    txt_menuAddUsuario.setBackgroundColor(R.attr.colorPrimary);
                    txt_menuAlterarDados.setBackgroundColor(255);
                    txt_menuDelBloco.setBackgroundColor(255);
                    txt_menuDelSala.setBackgroundColor(255);
                    txt_menuAgendarAula.setBackgroundColor(255);
                    fm.beginTransaction().replace(R.id.content, new AddUsuarioFragment()).commit();
                    return true;
                case R.id.nv_addDisciplina:
                    setTitle("Menu Adm - Adcionar Disciplina");
                    txt_menuAddBloco.setBackgroundColor(255);
                    txt_menuAddUsuario.setBackgroundColor(255);
                    txt_menuAlterarDados.setBackgroundColor(R.attr.colorPrimary);
                    txt_menuDelBloco.setBackgroundColor(255);
                    txt_menuDelSala.setBackgroundColor(255);
                    txt_menuAgendarAula.setBackgroundColor(255);
                    fm.beginTransaction().replace(R.id.content, new AddDisciplinaFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener2
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nv_deletarBloco:
                    setTitle("Menu Adm - Deletar Bloco");
                    txt_menuAddBloco.setBackgroundColor(255);
                    txt_menuAddUsuario.setBackgroundColor(255);
                    txt_menuAlterarDados.setBackgroundColor(255);
                    txt_menuDelBloco.setBackgroundColor(R.attr.colorPrimary);
                    txt_menuDelSala.setBackgroundColor(255);
                    txt_menuAgendarAula.setBackgroundColor(255);
                    fm.beginTransaction().replace(R.id.content, new DeletarBlocosFragment()).commit();
                    return true;
                case R.id.nv_deletarSala:
                    setTitle("Menu Adm - Deletar Sala");
                    txt_menuAddBloco.setBackgroundColor(255);
                    txt_menuAddUsuario.setBackgroundColor(255);
                    txt_menuAlterarDados.setBackgroundColor(255);
                    txt_menuDelBloco.setBackgroundColor(255);
                    txt_menuDelSala.setBackgroundColor(R.attr.colorPrimary);
                    txt_menuAgendarAula.setBackgroundColor(255);
                    fm.beginTransaction().replace(R.id.content, new DeletarSalasFragment()).commit();
                    return true;
                case R.id.nv_agendarAula:
                    setTitle("Menu Adm - Agendar Sala");
                    txt_menuAddBloco.setBackgroundColor(255);
                    txt_menuAddUsuario.setBackgroundColor(255);
                    txt_menuAlterarDados.setBackgroundColor(255);
                    txt_menuDelBloco.setBackgroundColor(255);
                    txt_menuDelSala.setBackgroundColor(255);
                    txt_menuAgendarAula.setBackgroundColor(R.attr.colorPrimary);
                    fm.beginTransaction().replace(R.id.content, new ReservarAulasFragment()).commit();
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
