package Frag_adm_menu_add_usuarios;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.leony.np2_usando_realm.R;

import java.util.List;

import ArraysAdapters.ListarUsuariosAdapter;
import Model_dados_de_tabelas.Model_dadosUsuarios;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListarUsuariosFragment extends Fragment {
    List<Model_dadosUsuarios> lista_DadosUsuarios;
    View rowView;
    public ListarUsuariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rowView = inflater.inflate(R.layout.fragment_adm_add_usuarios_listar_usuarios, container, false);

        Realm realm  = Realm.getDefaultInstance();

        ListView lista = (ListView) rowView.findViewById(R.id.lv_addUsuarios_listarUsuarios);
        lista_DadosUsuarios = realm.where(Model_dadosUsuarios.class).findAll();
        ListarUsuariosAdapter adapter = new ListarUsuariosAdapter(container.getContext(), lista_DadosUsuarios);
        lista.setAdapter(adapter);


        // Inflate the layout for this fragment
        return rowView;
    }

}
