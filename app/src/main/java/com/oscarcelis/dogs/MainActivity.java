package com.oscarcelis.dogs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.oscarcelis.dogs.activities.NewUserActivity;
import com.oscarcelis.dogs.activities.OkActivity;
import com.oscarcelis.dogs.activities.PrincipalActivity;
import com.oscarcelis.dogs.activities.ResetPasswordActivity;

public class MainActivity extends AppCompatActivity{

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnIngresar;
    private Button btnRecuperarClave;
    private Button btnCrearCuenta;
    private CheckBox chkvisualizarClave;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

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

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(MainActivity.this,PrincipalActivity.class));
                }else{}
            }
        };

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



        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUsuario();
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

    private void loginUsuario(){
        String emailLogin = edtEmail.getText().toString().trim();
        String passwordLogin = edtPassword.getText().toString().trim();

        if(TextUtils.isEmpty(emailLogin)){
            //Valida si el campo email está vacío
            Toast.makeText(this,"Ingrese un email válido",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(passwordLogin)){
            //Valida si el campo password está vacío
            Toast.makeText(this,"Ingrese una contraseña",Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(emailLogin, passwordLogin)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                        toastOk();
                        Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this,
                                "No se ha podido iniciar sesión, verifica los datos ingresados",
                                Toast.LENGTH_SHORT).show();
                    }
                }

        });
    }
    private void toastOk() {
        Toast toast = Toast.makeText(this,
                "Estás autenticado como "+ firebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 90|Gravity.CENTER_VERTICAL);
        toast.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}

