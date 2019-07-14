package com.example.softwareuber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class Telefono extends AppCompatActivity {
    EditText edtTelefono;


    public static final String REGEX_NUM_CELULAR = "^[0-9]{10}$"; // validar telefono
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefono);

        edtTelefono = (EditText)findViewById(R.id.id_telefono);

        //String stTelefono = edtTelefono.getText().toString().trim();
    }
}
