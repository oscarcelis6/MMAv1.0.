package com.oscarcelis.dogs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.oscarcelis.dogs.MainActivity;
import com.oscarcelis.dogs.R;

public class OkResetPasswordActivity extends AppCompatActivity {

    private Button btnRegresarInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_reset_password);

        btnRegresarInicio = findViewById(R.id.btnRegresarMenuPrincipal);

        LottieAnimationView animation = findViewById(R.id.animacionOk);
        animation.playAnimation();

        btnRegresarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresarMenuPrincipal();
            }
        });

    }

    private void regresarMenuPrincipal() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}
