package com.example.refamovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private TextView login;
    private EditText nombre, apellidos, correo, password;
    private Button registro;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = findViewById(R.id.jlbLogin);
        nombre = findViewById(R.id.txtNombre);
        apellidos = findViewById(R.id.txtApellidos);
        correo = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPsswdRegistro);

        registro = findViewById(R.id.btnRegistrar);

        firebaseFirestore = FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unombre = nombre.getText().toString();
                String uapellidos = apellidos.getText().toString();
                String ucorreo = correo.getText().toString();
                String upassword = password.getText().toString();

                if(unombre.isEmpty() || uapellidos.isEmpty() || ucorreo.isEmpty() || upassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Datos incompletos", Toast.LENGTH_LONG).show();
                } else {
                    registerUser(unombre, uapellidos, ucorreo, upassword);
                }
            }
        });
    }

    private void registerUser(String unombre, String uapellidos, String ucorreo, String upassword) {
        Map<String, Object> map = new HashMap<>();

        map.put("nombre",unombre);
        map.put("apellidos",uapellidos);
        map.put("correo",ucorreo);
        map.put("password",upassword);
        firebaseFirestore.collection("user").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Usuario creado", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error to introduce item", Toast.LENGTH_LONG).show();
            }
        });
    }
}