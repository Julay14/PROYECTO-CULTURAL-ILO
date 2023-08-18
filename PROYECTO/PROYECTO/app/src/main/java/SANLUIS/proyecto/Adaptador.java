package SANLUIS.proyecto;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    Activity activity;
    ArrayList<Ent_Mat> listaE;

    public Adaptador(Activity activity, ArrayList<Ent_Mat> listaE){
        this.activity=activity;
        this.listaE=listaE;
    }

    @Override
    public int getCount() {
        return listaE.size();
    }

    @Override
    public Object getItem(int position) {
        return listaE.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        TextView txtDocente,txtCurso,txtHorario,txtFrecuencia,txtCentro;
        ImageView imgFoto;
        if (v==null){
            LayoutInflater inflater= (LayoutInflater)activity.getLayoutInflater();
            v=inflater.inflate(R.layout.item_lyt,null);
        }
        txtDocente=v.findViewById(R.id.txtProfesor);
        txtCurso=v.findViewById(R.id.txtCurso);
        txtHorario=v.findViewById(R.id.txtHorario);
        txtCentro=v.findViewById(R.id.txtCentro);
        txtFrecuencia=v.findViewById(R.id.txtFrecuencia);
        Ent_Mat p=listaE.get(position);
        txtCentro.setText(p.getDis());
        txtCurso.setText(p.getNiv());
        txtDocente.setText(p.getDoc());
        txtFrecuencia.setText(p.getFre());
        txtHorario.setText(p.getHor());
        return v;
    }
}
