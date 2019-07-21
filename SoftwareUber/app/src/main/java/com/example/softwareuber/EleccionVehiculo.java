package com.example.softwareuber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EleccionVehiculo extends AppCompatActivity {
    Button conVehiculo, sinVehiculo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleccion_vehiculo);

        conVehiculo = (Button)findViewById(R.id.btn_conVehiculo);
        sinVehiculo = (Button)findViewById(R.id.btn_sinVehiculo);

        conVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent convehiculo = new Intent(EleccionVehiculo.this, RegistroConductor.class);
                EleccionVehiculo.this.startActivity(convehiculo);
               // startActivity(new Intent (EleccionVehiculo.this,RegistroConductor.class)); --Es lo mismo de arriba
            }
        });
        sinVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sinvehiculo = new Intent(EleccionVehiculo.this, RegistroConductor.class);
                EleccionVehiculo.this.startActivity(sinvehiculo);
                //startActivity(new Intent (EleccionVehiculo.this,RegistroConductor.class)); --Es lo mismo de arriba
            }
        });
    }
}
