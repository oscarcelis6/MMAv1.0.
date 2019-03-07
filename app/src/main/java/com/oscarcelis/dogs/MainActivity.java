package com.oscarcelis.dogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.oscarcelis.dogs.activities.NewUserActivity;
import com.oscarcelis.dogs.activities.ResetPasswordActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnIngresar;
    private Button btnRecuperarClave;
    private Button btnCrearCuenta;
    private CheckBox chkvisualizarClave;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btnIngresar = findViewById(R.id.btnIngresar);
        btnRecuperarClave = findViewById(R.id.btnRecuperarClave);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        chkvisualizarClave = findViewById(R.id.checkboxPassword);

        chkvisualizarClave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //Mostrar clave
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //Ocultar clave
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnRecuperarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abrirRecuperarClaveActivity();
            }
        });

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearNuevoUsuario();
            }
        });
    }

    private void crearNuevoUsuario() {
        Intent i = new Intent(getApplicationContext(), NewUserActivity.class);
        startActivity(i);
        finish();
    }

    private void abrirRecuperarClaveActivity() {
        Intent i = new Intent(getApplicationContext(), ResetPasswordActivity.class);
        startActivity(i);
        finish();
    }
}
