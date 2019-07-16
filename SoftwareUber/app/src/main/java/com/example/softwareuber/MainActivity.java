package com.example.softwareuber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText edtCedula, edtNombre, edtApellido,
            edtCorreo, edtPassword, edtVerifPass;
    Button btnAceptar;
    BigInteger valorMd5 = null;

    public static final String REGEX_NUMEROS = "^[0-9]+$"; //validar numeros
    public static final String REGEX_LETRAS = "^[a-zA-ZáÁéÉíÍóÓúÚñÑüÜ\\s]+$"; //validar letras
    public static final String REGEX_EMAIL ="^[a-zA-Z0-9\\._-]+@[a-zA-Z0-9-]{2,}[.][a-zA-Z]{2,4}$"; //validar correo electronico


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCedula = (EditText)findViewById(R.id.id_cedula);
        edtNombre = (EditText)findViewById(R.id.id_Nombre);
        edtApellido = (EditText)findViewById(R.id.id_Apellido);
        edtCorreo = (EditText)findViewById(R.id.id_correo);
        edtPassword = (EditText)findViewById(R.id.id_password);
        edtVerifPass = (EditText)findViewById(R.id.id_valPass);

        btnAceptar = (Button)findViewById(R.id.id_button);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //registrar("http://192.168.0.102/ConexionWebServices/insert.php");
                //validar();
                startActivity(new Intent(MainActivity.this,Telefono.class));
            }
        });
    }

    private void validar(){
        String stCedula = edtCedula.getText().toString();
        String stNombre = edtNombre.getText().toString().trim();
        String stApellido = edtApellido.getText().toString().trim();
        String stCorreo = edtCorreo.getText().toString().trim();
        String stPassword = edtPassword.getText().toString();
        String stVeriPass = edtVerifPass.getText().toString();

        if(!stCedula.isEmpty() && !stNombre.isEmpty() && !stApellido.isEmpty() && !stCorreo.isEmpty() && !stPassword.isEmpty() && !stVeriPass.isEmpty()){
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
                byte[] md5Password = stPassword.getBytes();
                try {
                    valorMd5 = new BigInteger(1,MD5.cryptMD5(md5Password));
                    Toast.makeText(this,"Llegue al hash",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(this,"Sali del hash",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Las constraseñas no son iguales",Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this,"Justo estoy aqui",Toast.LENGTH_SHORT).show();
            if(i==4){
                Toast.makeText(this, "Ya llegaste aqui, ahora vas a telefono", Toast.LENGTH_SHORT).show();
                //registrar("http://192.168.0.102/ConexionWebServices/insert.php",valorMd5.toString());
                //Intent siguiente = new Intent(this, Telefono.class);
                //startActivity(siguiente);
            }

        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void registrar(String url, final String md5){
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
                parametros.put("cedula",edtCedula.getText().toString());
                parametros.put("nombre",edtNombre.getText().toString());
                parametros.put("apellido",edtApellido.getText().toString());
                parametros.put("correo",edtCorreo.getText().toString());
                parametros.put("contrasena",md5);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
