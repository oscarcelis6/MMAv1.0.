package com.oscarcelis.dogs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.oscarcelis.dogs.MainActivity;
import com.oscarcelis.dogs.R;

public class PrincipalActivity extends AppCompatActivity {

    private Button btnCerrarSesion;
    private Button btnNuevoCanino;
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

        btnNuevoCanino = findViewById(R.id.btnNuevoCanino);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);


        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

    }

    private void cerrarSesion() {

        firebaseAuth.getInstance().signOut  ();
        finish();
        startActivity(new Intent(this, MainActivity.class));

    }
}
