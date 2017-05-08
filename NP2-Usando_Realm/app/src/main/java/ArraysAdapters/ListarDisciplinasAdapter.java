package ArraysAdapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.leony.np2_usando_realm.R;

import java.util.List;

import Model_dados_de_tabelas.Model_dadosDisciplinas;

/**
 * Created by Leony on 29/04/2017.
 */

public class ListarDisciplinasAdapter extends ArrayAdapter<Model_dadosDisciplinas> {
    private Context context;
    private List<Model_dadosDisciplinas> elementos;

    public ListarDisciplinasAdapter(Context context, List<Model_dadosDisciplinas> elementos){
        super(context, R.layout.fragment_adm_listar_disciplinas, elementos);
        this.context = context;
        this.elementos = elementos;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.layout_linha_lista_disciplinas, parent, false);

        TextView txt_codDiscLinhaDisc = (TextView) rowView.findViewById(R.id.txt_codDiscLinhaDisc);
        TextView txt_nomeDiscLinhaDisc = (TextView) rowView.findViewById(R.id.txt_nomeDiscLinhaDisc);
        TextView txt_nomeProfLinhaDiscip = (TextView) rowView.findViewById(R.id.txt_nomeProfLinhaDiscip);

        txt_codDiscLinhaDisc.setText(String.valueOf(elementos.get(position).getCod()));
        txt_nomeDiscLinhaDisc.setText(elementos.get(position).getNomeDisciplina());
        txt_nomeProfLinhaDiscip.setText(elementos.get(position).getNomeProfessor());

        return rowView;
    }

}
