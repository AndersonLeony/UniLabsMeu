package ArraysAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.leony.np2_usando_realm.R;

import java.util.List;

import Model_dados_de_tabelas.Model_reservarSalas;

/**
 * Created by Leony on 02/05/2017.
 */

public class ListarSalasReservadasAdapter extends ArrayAdapter<Model_reservarSalas> {

    Context context;
    List<Model_reservarSalas> elementos;

    public ListarSalasReservadasAdapter(Context context, List<Model_reservarSalas> elementos){
        super(context, R.layout.layout_linha_lista_salas_agendadas_adm_reservar_salas, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    public View getView (final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.layout_linha_lista_salas_agendadas_adm_reservar_salas, parent, false);

        TextView txt_dataReserva = (TextView) rowView.findViewById(R.id.txt_dataReserva);
        TextView txt_TurnoReserva = (TextView) rowView.findViewById(R.id.txt_TurnoReserva);
        TextView txt_horarioReserva = (TextView) rowView.findViewById(R.id.txt_horarioReserva);
        TextView txt_blocoSalaReserva = (TextView) rowView.findViewById(R.id.txt_blocoSalaReserva);
        TextView txt_discReserva = (TextView) rowView.findViewById(R.id.txt_discReserva);
        TextView txt_profReserva = (TextView) rowView.findViewById(R.id.txt_profReserva);

        txt_dataReserva.setText(elementos.get(position).getData().toString());
        txt_TurnoReserva.setText(elementos.get(position).getTurno().toString());
        txt_horarioReserva.setText(elementos.get(position).getHorarioInicio().toString()+"0 - "+elementos.get(position).getHorarioFim().toString());
        txt_blocoSalaReserva.setText(elementos.get(position).getFkSalas_letraBloco().toString()+String.valueOf(elementos.get(position).getFkSala_numSala()).toString());
        txt_discReserva.setText(elementos.get(position).getNomeDisciplina().toString());
        txt_profReserva.setText(elementos.get(position).getNomeProfessor().toString());


        return rowView;
    }
}
