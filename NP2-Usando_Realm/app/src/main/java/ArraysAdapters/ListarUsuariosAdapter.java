package ArraysAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.leony.np2_usando_realm.R;

import java.util.List;

import Model_dados_de_tabelas.Model_dadosUsuarios;

public class ListarUsuariosAdapter extends ArrayAdapter<Model_dadosUsuarios> {
    private Context context;
    private List<Model_dadosUsuarios> elementos;

    public ListarUsuariosAdapter(Context context, List<Model_dadosUsuarios> elementos){
        super(context, R.layout.layout_linha_lista_usuarios, elementos);
        this.context = context;
        this.elementos = elementos;

    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.layout_linha_lista_usuarios, parent, false);

        TextView txt_loginUsuario = (TextView) rowView.findViewById(R.id.txt_loginUsuario);
        TextView txt_tipoUsuario = (TextView) rowView.findViewById(R.id.txt_tipoUsuario);

        txt_loginUsuario.setText(elementos.get(position).getLogin());

        if(elementos.get(position).getTipo() == 0){
            txt_tipoUsuario.setText("ADM");
        }else if(elementos.get(position).getTipo() == 1){
            txt_tipoUsuario.setText("Aluno");
        } else {
            txt_tipoUsuario.setText("Professor");
        }



        return rowView;

    }

}
