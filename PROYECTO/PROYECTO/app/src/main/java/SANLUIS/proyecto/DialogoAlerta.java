package SANLUIS.proyecto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogoAlerta extends DialogFragment {

    //DIALOGO DE ALERTA

    /*@NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=
                new AlertDialog.Builder(getActivity());

        builder.setMessage("Te gusta programar en Android")
                .setTitle("Información")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        return builder.create();
    }*/

    //DIALOGO DE ALERTA USANDO DOS BOTONES

    /*@NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=
                new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Confirma la acción seleccionada?")
                .setTitle("Confirmación")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Confirmación aceptada", Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Confirmación cancelada", Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();
                    }
                });
        return builder.create();
    }*/

    //DIALOGO DE ALERTA CON LISTA DE OPCIONES

  /* @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final String[] items={"Español","Inglés","Francés"};
        AlertDialog.Builder builder=
                new AlertDialog.Builder(getActivity());

        builder.setTitle("Seleccione el idioma que más te gusta")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        Toast.makeText(getContext(),
                                "El idioma que más te gusta es: "+items[item],
                                Toast.LENGTH_LONG).show();
                    }
                });
        return builder.create();
    }*/

        //DIALOGO DE ALERTA CON RADIO BUTTON

     /* @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final String[] items={"Español","Inglés","Francés"};
            AlertDialog.Builder builder=
                    new AlertDialog.Builder(getActivity());

            builder.setTitle("Seleccione el idioma que más dominas")
                    .setSingleChoiceItems(items, -1,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int item) {
                                    Toast.makeText(getContext(),
                                            "El idioma que más dominas es: "+items[item],
                                            Toast.LENGTH_LONG).show();
                                }
                            });
            return builder.create();
        }*/

    //DIALOGO DE ALERTA CON CHECKBOX
   /*ArrayList<String> lista=new ArrayList<>();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final String[] items = {"Español", "Inglés", "Francés"};
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setTitle("Seleccione el idioma que más dominas \n")
                .setMultiChoiceItems(items, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int item,
                                                boolean isCheked) {
                                if (isCheked)
                                    lista.add(items[item]);
                                else
                                    lista.remove(items[item]);
                            }
                        });
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String lista2 = "";
                for (String l : lista)
                    lista2 += l + "\n";
                Toast.makeText(getActivity(),
                        "El idioma que más dominas es: \n" + lista2,
                        Toast.LENGTH_LONG).show();
            }
        });
        return builder.create();
    }*/

    //DIALOGO PERSONALIZADO
   /*@NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();

        View v=inflater.inflate(R.layout.dialogo_personalizado,null);
        final EditText edtUsu=v.findViewById(R.id.edtUsu);
        final EditText edtCon=v.findViewById(R.id.edtCon);
        builder.setView(v)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(),
                                "El usuario es: "+edtUsu.getText()+
                                "\n La contraseña es: "+edtCon.getText(),
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(),
                                "Cancela acceso.",
                                Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();
                    }
                });
        return builder.create();
    }*/
}
