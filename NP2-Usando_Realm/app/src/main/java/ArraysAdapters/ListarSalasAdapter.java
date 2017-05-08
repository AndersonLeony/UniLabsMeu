package ArraysAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leony.np2_usando_realm.AtualizarStatusBlocoSala;
import com.example.leony.np2_usando_realm.R;

import java.util.List;

import Model_dados_de_tabelas.Model_dadosSalas;
import io.realm.Realm;

/**
 * Created by Leony on 24/04/2017.
 */

public class ListarSalasAdapter extends ArrayAdapter<Model_dadosSalas> {

    private final Context context;
    private final List<Model_dadosSalas> elementos;

    public ListarSalasAdapter(Context context, List<Model_dadosSalas> elementos) {
        super(context, R.layout.layout_linha_lista_salas, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.layout_linha_lista_salas, parent, false);

        TextView txt_letraBloco = (TextView) rowView.findViewById(R.id.txt_letraBloco);
        TextView txt_numSala = (TextView) rowView.findViewById(R.id.txt_numSala);
        ImageView img_statusSala = (ImageView) rowView.findViewById(R.id.img_statusSala);
        TextView txt_statusAula = (TextView) rowView.findViewById(R.id.txt_statusAula);
        ImageView img_emAulaProxAula = (ImageView) rowView.findViewById(R.id.img_emAulaProxAula);
        TextView txt_DisciplinaAgendada = (TextView) rowView.findViewById(R.id.txt_DisciplinaAgendada);
        TextView txt_ProfessorAgendado = (TextView) rowView.findViewById(R.id.txt_ProfessorAgendado);
        TextView txt_turnoAgendado = (TextView) rowView.findViewById(R.id.txt_turnoAgendado);
        TextView txt_horarioAgendado = (TextView) rowView.findViewById(R.id.txt_horarioAgendado);

//        txt_letraBloco.setText(elementos.get(position).getFk_letraBloco());
//        txt_numSala.setText(String.valueOf(elementos.get(position).getNumSala()));
//        img_statusSala.setImageResource(elementos.get(position).getStatusSala());
//        txt_statusAula.setText(elementos.get(position).getStatusAula());

        AtualizarStatusBlocoSala.atualizarStatusSalas(Realm.getDefaultInstance(),
                context, elementos.get(position).getFk_letraBloco(), elementos.get(position).getNumSala(),
                txt_letraBloco, txt_numSala, img_statusSala, txt_statusAula,
                txt_DisciplinaAgendada, txt_ProfessorAgendado,
                txt_turnoAgendado, txt_horarioAgendado, elementos, position);

        return rowView;
    }
}
