package com.example.softwareuber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText edtCedula, edtNombre, edtApellido,
            edtCorreo, edtTelefono, edtPassword, edtVerifPass;
    Button btnAceptar;

    public static final String REGEX_NUMEROS = "^[0-9]+$"; //validar numeros
    public static final String REGEX_LETRAS = "^[a-zA-ZáÁéÉíÍóÓúÚñÑüÜ\\s]+$"; //validar letras
    public static final String REGEX_EMAIL ="^[a-zA-Z0-9\\._-]+@[a-zA-Z0-9-]{2,}[.][a-zA-Z]{2,4}$"; //validar correo electronico
    public static final String REGEX_NUM_CELULAR = "^[0-9]{10}$"; // validad telefono


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCedula = (EditText)findViewById(R.id.id_cedula);
        edtNombre = (EditText)findViewById(R.id.id_Nombre);
        edtApellido = (EditText)findViewById(R.id.id_Apellido);
        edtCorreo = (EditText)findViewById(R.id.id_correo);
        edtTelefono = (EditText)findViewById(R.id.id_telefono);
        edtPassword = (EditText)findViewById(R.id.id_password);
        edtVerifPass = (EditText)findViewById(R.id.id_valPass);

        btnAceptar = (Button)findViewById(R.id.id_button);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //registrar("http://192.168.0.102/ConexionWebServices/insert.php");
                validar();
            }
        });
    }

    private void validar(){
        String stCedula = edtCedula.getText().toString();
        String stNombre = edtNombre.getText().toString().trim();
        String stApellido = edtApellido.getText().toString().trim();
        String stCorreo = edtCorreo.getText().toString().trim();
        String stTelefono = edtTelefono.getText().toString().trim();
        String stPassword = edtPassword.getText().toString();
        String stVeriPass = edtVerifPass.getText().toString();

        if(!stCedula.isEmpty() && !stNombre.isEmpty() && !stApellido.isEmpty() && !stCorreo.isEmpty() && !stTelefono.isEmpty() && !stPassword.isEmpty() && !stVeriPass.isEmpty()){
            int i=0;
            Pattern patron = Pattern.compile(REGEX_LETRAS);
            if(patron.matcher(stNombre).matches()){
                i++;
            }else{
                Toast.makeText(this, "Ingrese correcto el nombre",Toast.LENGTH_SHORT).show();
            }

            if(patron.matcher(stApellido).matches()){
                i++;
            }else{
                Toast.makeText(this, "Ingrese correcto el apellido",Toast.LENGTH_SHORT).show();
            }
            Pattern patron1 = Pattern.compile(REGEX_EMAIL);
            if(patron1.matcher(stCorreo).matches()){
                i++;
            }else{
                Toast.makeText(this, "Ingrese correcto el correo",Toast.LENGTH_SHORT).show();
            }

            if(stPassword.compareTo(stVeriPass) == 0){
                i++;
            }else{
                Toast.makeText(this, "Las constraseñas no son iguales",Toast.LENGTH_SHORT).show();
            }

            if(i==4){
                Toast.makeText(this, "Ya llegaste aqui", Toast.LENGTH_SHORT).show();
                registrar("http://192.168.0.102/ConexionWebServices/insert.php");
            }

        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void registrar(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operacion Exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("",edtCedula.getText().toString());
                //Ponemos los demas parametros del registro

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
