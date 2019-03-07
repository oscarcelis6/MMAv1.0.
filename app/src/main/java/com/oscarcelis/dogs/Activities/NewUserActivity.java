package com.oscarcelis.dogs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.oscarcelis.dogs.R;

public class NewUserActivity extends AppCompatActivity{

    private static final String TAG = "EmailPassword";
    private EditText edtEmailNewUser;
    private EditText edtPasswordNewUser;
    private CheckBox chkVisualizarClave;

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

    // [START create_user_with_email]
    private void registrarUsuario(){
        String emailLogin = edtEmailNewUser.getText().toString().trim();
        String password = edtPasswordNewUser.getText().toString().trim();

        if(TextUtils.isEmpty(emailLogin)){
            //Valida si el campo email está vacío
            Toast.makeText(this,"Ingrese un email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //Valida si el campo password está vacío
            Toast.makeText(this,"Ingrese una contraseña",Toast.LENGTH_SHORT).show();
            return;
        }

/*        progressDialog.setMessage("Registrando usuario");
        progressDialog.show();*/

        firebaseAuth.createUserWithEmailAndPassword(emailLogin, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                          //  progressDialog.hide();
                            Toast.makeText(NewUserActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(NewUserActivity.this, "No se pudo registrar el usuario, intente de nuevo", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    // [END create_user_with_email]


}

