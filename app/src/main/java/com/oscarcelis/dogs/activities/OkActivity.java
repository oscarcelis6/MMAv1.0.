package com.oscarcelis.dogs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.oscarcelis.dogs.MainActivity;
import com.oscarcelis.dogs.R;

public class OkActivity extends AppCompatActivity {

    private Button btnRegresarMenuPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok);

        LottieAnimationView okAnimation = findViewById(R.id.animacionOk);
        okAnimation.playAnimation();
        okAnimation.getDuration();

        btnRegresarMenuPrincipal = findViewById(R.id.btnRegresarMenuPrincipal);

        btnRegresarMenuPrincipal.setOnClickListener(new View.OnClickListener() {
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
