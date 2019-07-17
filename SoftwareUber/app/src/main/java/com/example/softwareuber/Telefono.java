package com.example.softwareuber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Telefono extends AppCompatActivity {
    private int currentStep = 0;
    EditText edtTelefono, edtVerificarCod;
    Button botonEnviar, botonverificarCod;
    LinearLayout visibilidad;
    ArrayList<String> datos;
    String NumeroTelefono;

    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String codigoVerificacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefono);

        edtTelefono = (EditText)findViewById(R.id.id_telefono);
        edtVerificarCod = (EditText)findViewById(R.id.id_verificarCod);

        botonEnviar = (Button)findViewById(R.id.id_buttonEnviar);
        botonverificarCod = (Button)findViewById(R.id.id_buttonVerificar);
        visibilidad = (LinearLayout) findViewById(R.id.id_visibilidad);

        datos =new ArrayList<>();
        NumeroTelefono = null;
        datos = getIntent().getStringArrayListExtra("Datos");

        auth = FirebaseAuth.getInstance();

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumeroTelefono= "+593"+edtTelefono.getText().toString();

                if (TextUtils.isEmpty(NumeroTelefono)) {
                    edtTelefono.setError("Enter a Phone Number");
                    edtTelefono.requestFocus();
                }else if (edtTelefono.length() < 9 || edtTelefono.length() >= 10 ) {
                    edtTelefono.setError("Please enter a valid phone");
                    edtTelefono.requestFocus();
                } else {


                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            NumeroTelefono,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            Telefono.this,               // Activity (for callback binding)
                            mCallback);        // OnVerificationStateChangedCallbacks
                }

            }
        });
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.i("Fallo ","Fallo" + e.toString());
                Toast.makeText(Telefono.this,"La verificacion fallo "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                codigoVerificacion = s;
                Toast.makeText(getApplicationContext(), "Se envio un codigo a su telefono", Toast.LENGTH_SHORT).show();
                visibilidad.setVisibility(View.VISIBLE);
            }
        };

        botonverificarCod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo= edtVerificarCod.getText().toString();
                if (TextUtils.isEmpty(codigo)) {
                    edtTelefono.setError("Ingrese el codigo");
                    edtTelefono.requestFocus();
                }else if (edtTelefono.length() < 6) {
                    edtTelefono.setError("Por favor ingrese un codigo valido");
                    edtTelefono.requestFocus();
                } else {
                    singInWithPhone(PhoneAuthProvider.getCredential(codigoVerificacion,codigo));

                }
            }
        });

    }

    public void singInWithPhone(PhoneAuthCredential credential){
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Telefono.this, "Los datos ingresados son correctos, Bienvenido", Toast.LENGTH_SHORT).show();
                            registrar("http://172.29.65.1/ConexionWebServices/insert.php");//cambiar la ip
                        }else{
                            Toast.makeText(Telefono.this,"No ingreso",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                parametros.put("cedula",datos.get(0));
                parametros.put("nombre",datos.get(1));
                parametros.put("apellido",datos.get(2));
                parametros.put("correo",datos.get(3));
                parametros.put("telefono",NumeroTelefono);
                parametros.put("contrasena",datos.get(4));
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
