package SANLUIS.proyecto;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InicioSesionAlumno extends AppCompatActivity {


    TextInputEditText edtUsuario, edtContraseña;
    Button btnIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion_alumno);

        edtUsuario=(TextInputEditText) findViewById(R.id.edtUsuario);
        edtContraseña=(TextInputEditText) findViewById(R.id.edtContraseña);
        btnIniciar=(Button) findViewById(R.id.button);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
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

    public void iniciarSesion(){
        try {
            //PreparedStatement pst =bdOracle.conexionBD().prepareStatement("select codalu,apepat from alumno");;

            String usu=edtUsuario.getText().toString();
            String con=edtContraseña.getText().toString();

            if(validarUsuario(usu, con)){
                Intent iRegistrar = new Intent(this, Matricula.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("data",buscarid(usu,con));
                iRegistrar.putExtras(bundle);
                startActivity(iRegistrar);
                Toast.makeText(getApplicationContext(),"SE HA LOGEADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"USUARIO Y/O CONTRASEÑA NO ENCONTRADO",Toast.LENGTH_SHORT).show();
            }


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private String buscarid(String usuario,String contraseña){
        try {
            Statement st=conexionBD().createStatement();
            ResultSet rs=st.executeQuery(
                    "select codalu from alumno where usuario='"+usuario+"' and " +
                            "contraseña='"+contraseña+"'");

            if(rs.next()){
                return rs.getString(1);
            }
        }catch (SQLException e){
            Toast.makeText(this, "NO SE ENCONTRO USUARIO Y/O CONTRASEÑA", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private boolean validarUsuario(String usuario, String contraseña) {
        boolean validacion=false;
        try {
            Statement st=conexionBD().createStatement();
            ResultSet rs=st.executeQuery(
                    "select usuario,contraseña from alumno where usuario='"+usuario+"' and " +
                            "contraseña='"+contraseña+"'");

            if(rs.next()){
                validacion=true;
            }
        }catch (SQLException e){
            Toast.makeText(this, "NO SE ENCONTRO USUARIO Y/O CONTRASEÑA", Toast.LENGTH_SHORT).show();
        }
        return validacion;
    }
}
