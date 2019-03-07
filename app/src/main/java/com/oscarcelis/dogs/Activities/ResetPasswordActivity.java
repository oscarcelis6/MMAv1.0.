package com.oscarcelis.dogs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.oscarcelis.dogs.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText edtEmailPassword;
    private Button btnRecuperarClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edtEmailPassword = findViewById(R.id.edtEmailRecovery);
        btnRecuperarClave = findViewById(R.id.btnRecuperarClave);
    }
}
