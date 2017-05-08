package ArraysAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leony.np2_usando_realm.AtualizarStatusBlocoSala;
import com.example.leony.np2_usando_realm.R;

import java.util.List;

import Model_dados_de_tabelas.Model_dadosBlocos;
import Model_dados_de_tabelas.Model_dadosSalas;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Leony on 24/04/2017.
 */

public class ListarSalasParaDeletarAdapter extends ArrayAdapter<Model_dadosSalas> {
    private Realm realm;
    private final Context context;
    private final List<Model_dadosSalas> elementos;
    int pos;

    public ListarSalasParaDeletarAdapter(Context context, List<Model_dadosSalas> elementos) {
        super(context, R.layout.layout_linha_lista_salas, elementos);
        this.context = context;
        this.elementos = elementos;
        this.realm = Realm.getDefaultInstance();
    }

    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.layout_linha_lista_salas_para_deletar, parent, false);
        final View rowView2 = inflater.inflate(R.layout.fragment_adm_del_salas, parent, false);

        TextView txt_letraBloco = (TextView) rowView.findViewById(R.id.txt_letraBloco);
        TextView txt_numSala = (TextView) rowView.findViewById(R.id.txt_numSala);
        ImageView img_statusSala = (ImageView) rowView.findViewById(R.id.img_statusSala);
        TextView txt_statusAula = (TextView) rowView.findViewById(R.id.txt_status);
        ImageView img_emAulaProxAula = (ImageView) rowView.findViewById(R.id.img_emAulaProxAula);
        TextView txt_DisciplinaAgendada = (TextView) rowView.findViewById(R.id.txt_DisciplinaAgendada);
        TextView txt_ProfessorAgendado = (TextView) rowView.findViewById(R.id.txt_ProfessorAgendado);
        TextView txt_turnoAgendado = (TextView) rowView.findViewById(R.id.txt_turnoAgendado);
        TextView txt_horarioAgendado = (TextView) rowView.findViewById(R.id.txt_horarioAgendado);

        AtualizarStatusBlocoSala.atualizarStatusSalas(realm,
                context, elementos.get(position).getFk_letraBloco(), elementos.get(position).getNumSala(),
                txt_letraBloco, txt_numSala, img_statusSala, txt_statusAula,txt_DisciplinaAgendada,
                txt_ProfessorAgendado, txt_turnoAgendado, txt_horarioAgendado, elementos, position);

//        fab_deletarSala.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pos = (Integer) v.getTag();
//                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setMessage("Deseja deletar esta sala?")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                int salaDeletada;
//                                String blocoDeletado;
//                                realm.beginTransaction();
//                                Model_dadosSalas excluirSala = realm.where(Model_dadosSalas.class)
//                                        .equalTo("Fk_letraBloco", elementos.get(position).getFk_letraBloco())
//                                        .equalTo("numSala", elementos.get(position).getNumSala())
//                                        .findFirst();
//                                blocoDeletado = excluirSala.getFk_letraBloco();
//                                excluirSala.deleteFromRealm();
//                                realm.commitTransaction();
//
//                                realm.beginTransaction();
//                                RealmResults<Model_dadosSalas> buscarBlocoSala = realm.where(Model_dadosSalas.class)
//                                        .equalTo("Fk_letraBloco", blocoDeletado)
//                                        .findAll();
//                                realm.commitTransaction();
//
//                                if(buscarBlocoSala.isEmpty()){
//                                    realm.beginTransaction();
//                                    RealmResults excluirBloco = realm.where(Model_dadosBlocos.class)
//                                            .equalTo("letraBloco", blocoDeletado)
//                                            .findAll();
//                                    excluirBloco.deleteAllFromRealm();
//                                    realm.commitTransaction();
//                                }
//
//                                Toast.makeText(getContext(),
//                                        "Posicao = "+position+" Sala \""+blocoDeletado+"\" deletada com sucesso.",
//                                        Toast.LENGTH_SHORT).show();
//
//                            }
//                        })
//                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                            }
//                        });
//                builder.create();
//                builder.show();
//            }
//        });

        return rowView;
    }

}
