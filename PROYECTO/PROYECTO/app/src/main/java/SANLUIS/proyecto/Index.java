package SANLUIS.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Index extends AppCompatActivity {

    Button btnAlumno, btnDesarrollador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        btnAlumno = (Button) findViewById(R.id.btnAlumno);
        btnDesarrollador =(Button) findViewById(R.id.btnDesarrollador);
    }

    public void Alumno(View view){
        Intent iInfo = new Intent(this, InicioSesionAlumno.class);
        startActivity(iInfo);
    }

    public void Desarrollador(View view){
        Intent iInfo = new Intent(this, InicioSesionDesarrollador.class);
        startActivity(iInfo);
    }
}
