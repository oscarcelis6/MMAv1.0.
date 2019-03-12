package com.oscarcelis.dogs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.oscarcelis.dogs.MainActivity;
import com.oscarcelis.dogs.R;

public class NewRegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button btnNuevoCanino;
    private Button btnCerrarSesion;
    private TextView edtEmailUsuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);

        FirebaseAuth datosUsuario = firebaseAuth.getInstance();

        btnNuevoCanino = findViewById(R.id.btnNuevoCanino);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        edtEmailUsuarioActual = findViewById(R.id.edtEmailUsuarioActual);
        edtEmailUsuarioActual.setText("Estás autenticado como "  + datosUsuario.getCurrentUser().getEmail());

        btnNuevoCanino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoCanino();
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });
    }

    private void nuevoCanino() {
        startActivity(new Intent(this, NewDogActivity.class));
        finish();
    }

    private void cerrarSesion() {

        firebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));

    }
}
