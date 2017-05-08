package ArraysAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leony.np2_usando_realm.R;

import java.util.ArrayList;
import java.util.List;

import Model_dados_de_tabelas.Model_dadosBlocos;

/**
 * Created by Leony on 24/04/2017.
 */

public class ListarBlocosAdapter extends ArrayAdapter<Model_dadosBlocos> {

    private final Context context;
    private final List<Model_dadosBlocos> elementos;

    public ListarBlocosAdapter(Context context, List<Model_dadosBlocos> elementos) {
        super(context, R.layout.layout_linha_lista_blocos, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.layout_linha_lista_blocos, parent, false);

        TextView nomeBloco = (TextView) rowView.findViewById(R.id.nomeBloco);
        ImageView img_statusBloco = (ImageView) rowView.findViewById(R.id.img_statusBloco);

        nomeBloco.setText(elementos.get(position).getLetraBloco());
        img_statusBloco.setImageResource(elementos.get(position).getStatusBloco());

        return rowView;
    }
}
