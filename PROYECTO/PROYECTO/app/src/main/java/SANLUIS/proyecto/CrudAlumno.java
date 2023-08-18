package SANLUIS.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CrudAlumno extends AppCompatActivity {

    EditText edtCodAlu, edtApePatAlu, edtApeMatAlu, edtNombreAlu, edtDateAlu, edtTelefonoAlu, edtUsuarioAlu, edtContraseñaAlu;
    Button btnBuscar, btnModificar, btnEliminar, btnLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crud_alumno);
        edtCodAlu = (EditText) findViewById(R.id.edtCodAlu);
        edtApePatAlu = (EditText) findViewById(R.id.edtApePatAlu);
        edtApeMatAlu = (EditText) findViewById(R.id.edtApeMatAlu);
        edtNombreAlu = (EditText) findViewById(R.id.edtNomAlu);
        edtTelefonoAlu = (EditText) findViewById(R.id.edtTlfAlu);
        edtDateAlu = (EditText) findViewById(R.id.edtFecNacAlu);
        edtUsuarioAlu = (EditText) findViewById(R.id.edtUsuAlu);
        edtContraseñaAlu = (EditText) findViewById(R.id.edtContAlu);

        btnBuscar = (Button) findViewById(R.id.btnBuscador);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { buscarAlumno();}
        });

        btnModificar = (Button) findViewById(R.id.btnModificar);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { modificarAlumno();}
        });

        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { eliminarAlumno();}
        });

        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { limpiar();}
        });
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

    public void buscarAlumno(){
        Connection connection = conexionBD();
        try{
            if(connection !=null){
                Statement st = conexionBD().createStatement();
                ResultSet rs = st.executeQuery("select * from ALUMNO where CODALU ='"+ edtCodAlu.getText().toString()+"'");

                if(rs.next()){

                    edtApePatAlu.setText(rs.getString(2));
                    edtApeMatAlu.setText(rs.getString(3));
                    edtNombreAlu.setText(rs.getString(4));
                    edtTelefonoAlu.setText(rs.getString(5));

                    String año = rs.getString(6).substring(0,4);
                    String mes = rs.getString(6).substring(5,7);
                    String dia = rs.getString(6).substring(8,10);

                    edtDateAlu.setText(dia+"/"+mes+"/"+año);

                    edtUsuarioAlu.setText(rs.getString(7));
                    edtContraseñaAlu.setText(rs.getString(8));

                }else{
                    Toast.makeText(getApplicationContext(), "ALUMNO NO ENCONTRADO",Toast.LENGTH_SHORT).show();
                }
            }
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void modificarAlumno(){
        Connection connection = conexionBD();
        try {
            if (connection !=null){
                PreparedStatement pst = conexionBD().prepareStatement("UPDATE ALUMNO set CODALU='"+ edtCodAlu.getText().toString()+"', APEPAT='"+ edtApePatAlu.getText().toString()+"', APEMAT='"+ edtApeMatAlu.getText().toString()+"', NOMALU='"+ edtNombreAlu.getText().toString()+"', TLFALU='"+ edtTelefonoAlu.getText().toString()+"',FECNAC=to_date('"+ edtDateAlu.getText().toString()+"', 'DD/MM/YYYY')"+", USUARIO='"+ edtUsuarioAlu.getText().toString()+"', CONTRASEÑA='"+ edtContraseñaAlu.getText().toString()+"' WHERE CODALU= '" + edtCodAlu.getText().toString()+"'");

                pst.executeUpdate();
                Toast.makeText(getApplicationContext(),"REGISTRO MODIFICADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarAlumno(){
        Connection connection = conexionBD();
        try{
            if(connection !=null){
                PreparedStatement pst = conexionBD().prepareStatement("DELETE FROM ALUMNO WHERE CODALU= '"+ edtCodAlu.getText().toString()+"'");
                pst.executeUpdate();
                Toast.makeText(getApplicationContext(), "REGISTRO ELIMINADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiar(){
        Connection connection = conexionBD();
        try{
            if(connection !=null){
                edtCodAlu.setText("");
                edtApePatAlu.setText("");
                edtApeMatAlu.setText("");
                edtNombreAlu.setText("");
                edtTelefonoAlu.setText("");
                edtDateAlu.setText("");
                edtUsuarioAlu.setText("");
                edtContraseñaAlu.setText("");
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    public void RegresarSitio(View view){
        Intent iRegresar = new Intent(this, RegistroAlumno.class);
        startActivity(iRegresar);
    }
}