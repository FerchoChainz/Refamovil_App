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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private TextView registrarCuenta;
    private Button entrar;
    private EditText user, password;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registrarCuenta = findViewById(R.id.jlbNewAccount);
        entrar = findViewById(R.id.btnRegistrar);
        user = findViewById(R.id.txtUser);
        password = findViewById(R.id.txtPsswd);

        firebaseFirestore = FirebaseFirestore.getInstance();

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = user.getText().toString();
                String p = password.getText().toString();

                if (!u.isEmpty() && !p.isEmpty()) {
                    verificarCredenciales(u,p);
                } else {
                    Toast.makeText(getApplicationContext(), "Ingresa nombre y contraseña", Toast.LENGTH_LONG).show();
                }
            }
        });

        registrarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void verificarCredenciales(String nombre, String passwd) {
        firebaseFirestore.collection("user")
                .whereEqualTo("nombre", nombre)
                .whereEqualTo("password", passwd)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null && !task.getResult().isEmpty()) {
                                startActivity(new Intent(getApplicationContext(), InicioActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                                clearComponents();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Error al verificar las credenciales", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void clearComponents(){
        user.setText("");
        password.setText("");
    }
}

