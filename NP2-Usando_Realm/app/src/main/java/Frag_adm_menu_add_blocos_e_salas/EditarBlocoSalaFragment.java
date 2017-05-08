package Frag_adm_menu_add_blocos_e_salas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.leony.np2_usando_realm.R;

public class EditarBlocoSalaFragment extends Fragment {
    Fragment fm;

    public EditarBlocoSalaFragment() {
        // Required empty public constructor
    }

    public EditarBlocoSalaFragment(Fragment fm) {
        this.fm = fm;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_bloco_sala, container, false);
    }

}
