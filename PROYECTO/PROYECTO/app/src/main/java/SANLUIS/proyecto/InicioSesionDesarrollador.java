package SANLUIS.proyecto;

import android.content.Intent;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InicioSesionDesarrollador extends AppCompatActivity {
    TextInputEditText edtUsuarioDes, edtContraseñaDes;
    Button btnIniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion_desarrollador);

        edtUsuarioDes=findViewById(R.id.edtUsuarioDes);
        edtContraseñaDes=(TextInputEditText) findViewById(R.id.edtContraseñaDes);
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

            String usu=edtUsuarioDes.getText().toString();
            String con=edtContraseñaDes.getText().toString();

            if(validarUsuario(usu, con)){
                Intent iRegistrar = new Intent(this, RegistroAlumno.class);
                startActivity(iRegistrar);
            }else{
                Toast.makeText(getApplicationContext(),"USUARIO Y/O CONTRASEÑA NO ENCONTRADO",Toast.LENGTH_SHORT).show();
            }


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarUsuario(String usuario, String contraseña) {
        boolean validacion=false;
        try {
            Statement st=conexionBD().createStatement();
            ResultSet rs=st.executeQuery(
                    "select usuario,contraseña from desarrollador where usuario='"+usuario+"' and " +
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
