package SANLUIS.proyecto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Matricula extends AppCompatActivity {

    ListView lstC;
    ListView lstM;
    ArrayList<String> listaC;
    ArrayList<Ent_Mat> listaE;
    Button btnBuscar;
    String cen;
    String cur;
    String fre;
    String hor;
    String codalu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matricula);
        lstC=(ListView)findViewById(R.id.lstC);
        lstM=(ListView)findViewById(R.id.lstMat);
        datosListView();
        clickenListView();
        clickenListMatricula();
        recuperarData();
        btnBuscar=(Button)findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listar();
            }
        });


    }

    private void recuperarData() {
        Bundle bundle= getIntent().getExtras();
        if (bundle==null){
            codalu=null;
        }else{
            codalu=(String)bundle.getSerializable("data");
        }
    }

    private void clickenListMatricula() {
        lstM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Ent_Mat e=listaE.get(position);
                String seccion=buscarSec(e.getNiv(),e.getHor(),e.getFre(),e.getDoc());
                AlertDialog.Builder alerta=
                        new AlertDialog.Builder(Matricula.this);

                alerta.setMessage("¿Esta seguro que desea matricularse en esta opción?")
                        .setCancelable(true)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                matricularse(buscarCodCentro(e.getDis()),e.getNiv(),seccion,e.getDoc());
                                dialogInterface.cancel();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog titulo=alerta.create();
                titulo.setTitle("Matrícula");
                titulo.show();
            }
        });
    }

    private String buscarCodCentro(String dis) {
        try {
            Statement st=conexionBD().createStatement();
            ResultSet rs=st.executeQuery(
                    "select codcentro from centro where distrito='" + dis+"'");

            if(rs.next()){
                return rs.getString(1);
            }
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private String buscarSec(String niv,String hor,String fre,String cod){
        try {
            Statement st=conexionBD().createStatement();
            ResultSet rs=st.executeQuery(
                    "select seccion_ciclo from ciclo where " +
                            "nivel_ciclo='"+niv+"' " +
                            "and horario='"+hor+"' " +
                            "and frecuencia='"+fre+"' " +
                            "and coddoc='"+cod+"'");
            if(rs.next()){
                return rs.getString(1);
            }

        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void matricularse(String codcentro,String nivel, String sec,String coddoc){
        try {

            PreparedStatement pst=conexionBD().prepareStatement("insert into matricula values(?,?,?,?,?,?)");
            pst.setString(1,codalu.substring(1,3)+coddoc.charAt(3)+codalu.substring(3,5));
            pst.setString(2,codalu);
            pst.setString(3,codcentro);
            pst.setString(4,nivel);
            pst.setString(5,sec);
            pst.setString(6,coddoc);
            pst.executeUpdate();
            Toast.makeText(getApplicationContext(),"REGISTRO AGREGADO",Toast.LENGTH_SHORT).show();
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }



    private void listar(){
        listaE=buscar();
        Adaptador adaptador=new Adaptador(this,listaE);
        lstM.setAdapter(adaptador);
    }

    private ArrayList<Ent_Mat> buscar() {
        ArrayList<Ent_Mat> listaPer = new ArrayList<>();
        try {
            Statement st=conexionBD().createStatement();
            ResultSet rs=st.executeQuery(
                    "select ce.distrito,ci.nivel_ciclo,ci.frecuencia,ci.horario,ci.coddoc " +
                            "from ciclo ci,centro ce,docente d " +
                            "where ci.coddoc=d.coddoc " +
                            "and distrito='"+cen+"' " +
                            "and nivel_ciclo='"+cur+"' " +
                            "and frecuencia='"+fre+"' " +
                            "and horario='"+hor+"'");

            while(rs.next()){
                listaPer.add(new Ent_Mat(rs.getString(1),rs.getString(2),
                        rs.getString(3),rs.getString(4),rs.getString(5)));
            }

        }catch (SQLException e){
            Toast.makeText(this, "NO SE ENCONTRARON DATOS", Toast.LENGTH_SHORT).show();
        }
        return listaPer;
    }

    public Connection conexionBD(){
        Connection conexion= null;
        try {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conexion= DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.69:1521:XE",
                    "SANLUIS", "SANLUIS");

        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"Conexion fallida",Toast.LENGTH_SHORT).show();

        }
        return conexion;
    }

    private void clickenListView() {
        lstC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                final String[] centro = new String[1];
                final String[] curso = new String[1];
                final String[] frecuencia = new String[1];
                final String[] horario = new String[1];

                switch (listaC.get(position)){

                    case "Centros:":
                        List<String> items = new ArrayList<>();
                        items=buscarDato("distrito","centro");
                        final String[] valor=items.toArray(new String[items.size()]);
                        AlertDialog.Builder alerta=
                                new AlertDialog.Builder(Matricula.this);
                        alerta.setTitle(listaC.get(position))
                                .setSingleChoiceItems(valor, -1,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int item) {
                                                centro[0] =valor[item];
                                            }
                                        })
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        cen =centro[0];
                                        Toast.makeText(Matricula.this, "Confirmación aceptada", Toast.LENGTH_LONG).show();
                                        dialogInterface.cancel();
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(Matricula.this, "Confirmación cancelada", Toast.LENGTH_LONG).show();
                                        dialogInterface.cancel();
                                    }
                                });
                        alerta.show();
                        break;

                    case "Cursos disponibles:":
                        List<String> items2 = new ArrayList<>();
                        items2=buscarDato("nivel_ciclo","ciclo");
                        final String[] valor2=items2.toArray(new String[items2.size()]);
                        AlertDialog.Builder alerta2=
                                new AlertDialog.Builder(Matricula.this);
                        alerta2.setTitle(listaC.get(position))
                                .setSingleChoiceItems(valor2, -1,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int item) {
                                                curso[0] =valor2[item];
                                            }
                                        })
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        cur =curso[0];
                                        Toast.makeText(Matricula.this, "Confirmación aceptada", Toast.LENGTH_LONG).show();
                                        dialogInterface.cancel();
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(Matricula.this, "Confirmación cancelada", Toast.LENGTH_LONG).show();
                                        dialogInterface.cancel();
                                    }
                                });
                        alerta2.show();
                        break;

                    case "Frecuencia:":
                        List<String> items3 = new ArrayList<>();
                        items3=buscarDato("frecuencia","ciclo");
                        final String[] valor3=items3.toArray(new String[items3.size()]);
                        AlertDialog.Builder alerta3=
                                new AlertDialog.Builder(Matricula.this);
                        alerta3.setTitle(listaC.get(position))
                                .setSingleChoiceItems(items3.toArray(new String[items3.size()]), -1,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int item) {
                                                frecuencia[0] =valor3[item];
                                            }
                                        })
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        fre =frecuencia[0];
                                        Toast.makeText(Matricula.this, "Confirmación aceptada", Toast.LENGTH_LONG).show();
                                        dialogInterface.cancel();
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(Matricula.this, "Confirmación cancelada", Toast.LENGTH_LONG).show();
                                        dialogInterface.cancel();
                                    }
                                });
                        alerta3.show();
                        break;

                    default:
                        if(cur==null) {
                            Toast.makeText(Matricula.this, "Escoja primero un curso", Toast.LENGTH_LONG).show();
                        }else {
                            List<String> items4 = new ArrayList<>();
                            items4 = buscarDato2("horario", "ciclo",cur);
                            final String[] valor4 = items4.toArray(new String[items4.size()]);
                            AlertDialog.Builder alerta4 =
                                    new AlertDialog.Builder(Matricula.this);
                            alerta4.setTitle(listaC.get(position))
                                    .setSingleChoiceItems(items4.toArray(new String[items4.size()]), -1,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int item) {
                                                    horario[0] = valor4[item];
                                                }
                                            })
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            hor = horario[0];
                                            Toast.makeText(Matricula.this, "Confirmación aceptada", Toast.LENGTH_LONG).show();
                                            dialogInterface.cancel();
                                        }
                                    })
                                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(Matricula.this, "Confirmación cancelada", Toast.LENGTH_LONG).show();
                                            dialogInterface.cancel();
                                        }
                                    });
                            alerta4.show();
                        }

                }
            }
        });
    }

    private List buscarDato(String datos, String tabla) {
        List dato=new ArrayList();

        try {
            Statement st=conexionBD().createStatement();
            ResultSet rs=st.executeQuery(
                    "select distinct("+datos+") from " + tabla);

            while(rs.next()){
                dato.add(rs.getString(datos));
            }
        }catch (SQLException e){
            Toast.makeText(this, "NO SE ENCONTRARON DATOS", Toast.LENGTH_SHORT).show();
        }
        return dato;
    }

    private List buscarDato2(String datos, String tabla,String cur) {
        List dato=new ArrayList();

        try {
            Statement st=conexionBD().createStatement();
            ResultSet rs=st.executeQuery(
                    "select distinct("+datos+") from " + tabla+" where nivel_ciclo='"+cur+"'");

            while(rs.next()){
                dato.add(rs.getString(datos));
            }
        }catch (SQLException e){
            Toast.makeText(this, "NO SE ENCONTRARON DATOS", Toast.LENGTH_SHORT).show();
        }
        return dato;
    }

    private void datosListView() {
        listaC=new ArrayList<String>();
        listaC.add("Centros:");
        listaC.add("Cursos disponibles:");
        listaC.add("Frecuencia:");
        listaC.add("Horario:");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaC);
        lstC.setAdapter(adapter);
    }
}
