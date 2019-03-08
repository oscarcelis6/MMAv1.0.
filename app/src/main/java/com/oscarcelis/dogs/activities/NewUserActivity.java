package com.oscarcelis.dogs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.oscarcelis.dogs.MainActivity;
import com.oscarcelis.dogs.R;

public class NewUserActivity extends AppCompatActivity{

    private static final String TAG = "EmailPassword";
    private EditText edtEmailNewUser;
    private EditText edtPasswordNewUser;
    private CheckBox chkVisualizarClave;
    private Button btnCancelar;

    private FirebaseAuth firebaseAuth;

    private Button btnNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        firebaseAuth = FirebaseAuth.getInstance();

        edtEmailNewUser = findViewById(R.id.edtEmailNewUser);
        edtPasswordNewUser = findViewById(R.id.edtPasswordNewUser);
        btnNewUser = findViewById(R.id.btnCrearCuenta);
        chkVisualizarClave = findViewById(R.id.checkboxPassword);

        btnCancelar = findViewById(R.id.btnRegresarMenuPrincipal);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        chkVisualizarClave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edtPasswordNewUser.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    edtPasswordNewUser.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

    }

    private void cancelar() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    // [START create_user_with_email]
    private void registrarUsuario(){
        String emailNewUser = edtEmailNewUser.getText().toString().trim();
        String passwordNewUser = edtPasswordNewUser.getText().toString().trim();

        if(TextUtils.isEmpty(emailNewUser)){
            //Valida si el campo email está vacío
            Toast.makeText(this,"Ingrese un email válido",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(passwordNewUser)){
            //Valida si el campo password está vacío
            Toast.makeText(this,"Ingrese una contraseña",Toast.LENGTH_SHORT).show();
            return;
        }


        firebaseAuth.createUserWithEmailAndPassword(emailNewUser, passwordNewUser)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            toastOk();
                            Intent intent = new Intent(getApplicationContext(), OkActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(NewUserActivity.this,
                                    "No se pudo registrar el usuario, verifique los datos ingresados",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    // [END create_user_with_email]

    public void toastOk(){
        Toast toast = Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 90);
        toast.show();
    }

}

