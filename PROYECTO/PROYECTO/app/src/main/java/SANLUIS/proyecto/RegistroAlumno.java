package SANLUIS.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroAlumno extends AppCompatActivity {

    TextInputEditText edtCod,edtApePat, edtApeMat, edtNombre, edtDate,edtTelefono;
    Button btnAgregar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        edtApeMat= findViewById(R.id.edtApeMat);
        edtApePat= findViewById(R.id.edtApePat);
        edtNombre= findViewById(R.id.edtNombre);
        edtDate= findViewById(R.id.edtFecNac);
        edtCod= findViewById(R.id.edtCod);
        edtTelefono= findViewById(R.id.edtTelefono);
        btnAgregar=(Button) findViewById(R.id.btnSesionDes);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarUsuario();
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

    public void agregarUsuario(){
        try {
            PreparedStatement pst=conexionBD().prepareStatement("insert into alumno values(?,?,?,?,?,to_date(?,'DD/MM/YYYY'),?,?)");
            pst.setString(1,edtCod.getText().toString());
            pst.setString(2,edtApePat.getText().toString());
            pst.setString(3,edtApeMat.getText().toString());
            pst.setString(4,edtNombre.getText().toString());
            pst.setString(5,edtTelefono.getText().toString());
            pst.setString(6,edtDate.getText().toString());
            String usu=edtNombre.getText().toString()+"_"+edtApePat.getText().toString();
            String con=edtCod.getText().toString().substring(0,3)+
                    edtApePat.getText().toString().substring(1,3)+
                    edtCod.getText().toString().substring(4,6)+
                    edtNombre.getText().toString().substring(1,3)+
                    edtCod.getText().toString().substring(6,7);
            pst.setString(7,usu);
            pst.setString(8,con);
            pst.executeUpdate();
            Toast.makeText(getApplicationContext(),"REGISTRO AGREGADO",Toast.LENGTH_SHORT).show();
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void irSitio(View view){
        Intent iCrud = new Intent(this, CrudAlumno.class);
        startActivity(iCrud);
    }


/*
    public void enseñarfecha(){
        String date=edtDate.getText().toString();
        Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();
    }

     private TextView textView;

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private  static final String URL = "jdbc:oracle:thin:@192.168.100.69:1521:XE";
    private static final String USERNAME = "SANLUIS";
    private static final String PASSWORD = "SANLUIS";

    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conexion);

        textView = findViewById(R.id.textView6);
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
    }

    public void buttonConnectToOracleDB(View view){
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Toast.makeText(this, "CONNECTED", Toast.LENGTH_LONG).show();

            Statement statement = connection.createStatement();
            StringBuffer stringBuffer = new StringBuffer();
            ResultSet resultSet = statement.executeQuery("select apepatalu from alumno");
            while (resultSet.next()){
                stringBuffer.append(resultSet.getString(1) + "\n");
            }
            textView.setText(stringBuffer.toString());
            connection.close();
        }
        catch (Exception e){
            textView.setText(e.toString());
        }
    }*/
}