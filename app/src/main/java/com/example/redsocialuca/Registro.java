package com.example.redsocialuca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {
    EditText Correo, Nombre, Contraseña, Edad, Apellido;
    Button Registrarse;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ActionBar actionbar = getSupportActionBar();
                assert actionbar !=null;
                actionbar.setTitle("Registro");
                actionbar.setDisplayShowHomeEnabled(true);
                actionbar.setDisplayHomeAsUpEnabled(true);

                Correo = findViewById(R.id.Correo);
                Nombre = findViewById(R.id.Nombre);
                Contraseña = findViewById(R.id.Contraseña);
                Edad = findViewById(R.id.Edad);
                Apellido = findViewById(R.id.Apellido);


                Registrarse = findViewById(R.id.Registarse);

                firebaseAuth = firebaseAuth.getInstance();
                Registrarse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String correo = Correo.getText().toString();
                        String pass = Contraseña.getText().toString();

                        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                            Correo.setError("Correo no valido");
                            Correo.setFocusable(true);
                        }else if (pass.length()<6) {
                            Contraseña.setError("Contraseña debe ser mayor a 6 caracteres");
                            Contraseña.setFocusable(true);
                        }else{
                            Registrar(correo,pass);

                    }

                    }
                });



    }

    private void Registrar(String correo, String pass) {
        firebaseAuth.createUserWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();


                            assert user != null;
                            String uid = user.getUid();
                            String correo = Correo.getText().toString();
                            String pass = Contraseña.getText().toString();
                            String nombre = Nombre.getText().toString();
                            String apellido = Apellido.getText().toString();

                            HashMap <Object,String> DatosUsuarios = new HashMap<>();

                            DatosUsuarios.put("uid", uid);
                            DatosUsuarios.put("correo",correo);
                            DatosUsuarios.put("contraseña", pass);
                            DatosUsuarios.put("nombre", nombre);
                            DatosUsuarios.put("apellido", apellido);

                            DatosUsuarios.put("imagen", "");

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("USUARIOS_DE_APP");
                            reference.child(uid).setValue(DatosUsuarios);
                            Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registro.this,inicio.class));

                        }else{
                            Toast.makeText(Registro.this, "Algo saliò mal", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registro.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}