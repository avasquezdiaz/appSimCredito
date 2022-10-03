package com.example.appsimcredito;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // Arreglo que alimentará el adaptador para el spinner
    String[] tprestamos = {"Vivienda","Educación","Libre Inversión"};
    String presSel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instanciar y referenciar los IDs del archivo xml
        EditText nombre =  findViewById(R.id.etnombre);
        EditText fecha =  findViewById(R.id.etfecha);
        EditText monto =  findViewById(R.id.etmonto);
        Spinner tipocred = findViewById(R.id.sptipo);
        RadioButton rb12 = findViewById(R.id.rb12);
        RadioButton rb24 = findViewById(R.id.rb24);
        RadioButton rb36 = findViewById(R.id.rb36);
        TextView deuda=findViewById(R.id.tvdeuda);
        TextView cuota=findViewById(R.id.tvcuota);
        ImageButton calcular = findViewById(R.id.btncalc);
        ImageButton limpiar = findViewById(R.id.btnclean);
        Switch cuotamanejo= findViewById(R.id.swcuotamanejo);

        // Crear el adaptador que contendrá el arreglo tprestamos
        ArrayAdapter adpTprestamo = new ArrayAdapter(this, android.R.layout.simple_list_item_checked,tprestamos);
        // Asignar el adaptador al spinner
        tipocred.setAdapter(adpTprestamo);
        // Chequear el tipo de crédito seleccionado
        tipocred.setOnItemSelectedListener(this);
        // Evento click del boton calcular
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nombre.getText().toString().isEmpty()){
                    if (!fecha.getText().toString().isEmpty()){
                        if (!monto.getText().toString().isEmpty()){
                            if (parseDouble(monto.getText().toString()) >= 1000000 && parseDouble(monto.getText().toString()) <= 100000000){
                                // Chequear el tipo de credito seleccionado
                                double interes = 0;
                                switch (presSel){
                                    case "Vivienda":
                                        interes = 0.015;
                                        break;
                                    case "Educación":
                                        interes = 0.01;
                                        break;
                                    case "Libre Inversión":
                                        interes = 0.02;
                                        break;
                                }
                                double xmonto = parseDouble(monto.getText().toString());
                                double ncuotas = 0;
                                if (rb12.isChecked()){
                                    ncuotas = 12;
                                }
                                if (rb24.isChecked()){
                                    ncuotas = 24;
                                }
                                if (rb36.isChecked()){
                                    ncuotas = 36;
                                }
                                double totaldeuda = xmonto + (xmonto * interes * ncuotas);
                                DecimalFormat valueFormat =new DecimalFormat("###,###,###,###.#");
                                deuda.setText(valueFormat.format(totaldeuda));
                                double vlrcuota=totaldeuda/ncuotas;
                                cuota.setText(valueFormat.format(vlrcuota));

                                if (cuotamanejo.isChecked()){
                                    cuota.setText(valueFormat.format(vlrcuota+1000));
                                }


                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Monto debe estar entre 1 y 100 millones",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Monto es obligatorio",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Fecha obligatoria",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Nombre obligatorio",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presSel = tprestamos[1]; // tomar el valor de la opción seleccionado Tipo de Prestamo
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}