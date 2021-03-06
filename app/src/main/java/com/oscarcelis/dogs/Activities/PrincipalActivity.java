package com.oscarcelis.dogs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.oscarcelis.dogs.MainActivity;
import com.oscarcelis.dogs.R;

public class PrincipalActivity extends AppCompatActivity {

    private Button btnCerrarSesion;
    private Button btnNuevoRegistro;
    FirebaseAuth firebaseAuth;
    private TextView edtEmailUsuarioActual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        FirebaseAuth datosUsuario = firebaseAuth.getInstance();

        edtEmailUsuarioActual = findViewById(R.id.edtEmailUsuarioActual);

        edtEmailUsuarioActual.setText("Estás autenticado como "+ datosUsuario.getCurrentUser().getEmail());

        btnNuevoRegistro = findViewById(R.id.btnNuevoRegistro);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);


        btnNuevoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoRegistro();
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

    }


    private void nuevoRegistro() {
        startActivity(new Intent(this, NewRegisterActivity.class));
        finish();
    }

    private void cerrarSesion() {

        firebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));

    }
}
