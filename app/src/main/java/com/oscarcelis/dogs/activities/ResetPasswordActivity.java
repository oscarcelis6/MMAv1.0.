package com.oscarcelis.dogs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.oscarcelis.dogs.MainActivity;
import com.oscarcelis.dogs.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText edtEmailPassword;
    private Button btnRecuperarClave;
    private Button btnCancelar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        edtEmailPassword = findViewById(R.id.edtEmailRecovery);
        btnRecuperarClave = findViewById(R.id.btnRecuperarClave);
        btnCancelar = findViewById(R.id.btnRegresarMenuPrincipal);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });


        btnRecuperarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restablecerClave();
            }
        });

    }

    public void restablecerClave(){

        String recoveryEmail = edtEmailPassword.getText().toString().trim();

        if(TextUtils.isEmpty(recoveryEmail)){
            //Valida si el campo email está vacío
            Toast.makeText(this,"Ingrese un email válido",Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.getInstance().sendPasswordResetEmail(recoveryEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            toastClaveRestablecidaOK();
                            Intent intent = new Intent(getApplicationContext(), OkResetPasswordActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(ResetPasswordActivity.this,
                                    "No se ha podido validar el email, " +
                                            "inténtalo de nuevo",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void cancelar() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    private void toastClaveRestablecidaOK(){
        Toast toast = Toast.makeText(this,
                "Revise su correo electrónico para restablecer la clave"
                , Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 90);
        toast.show();
    }

}