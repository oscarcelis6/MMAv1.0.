package com.oscarcelis.dogs.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.oscarcelis.dogs.MainActivity;
import com.oscarcelis.dogs.R;

public class NewUserActivity extends AppCompatActivity {

    private EditText edtEmailNewUser;
    private EditText edtPasswordNewUser;
    private CheckBox chkVisualizarClave;


    private Button btnNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

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

    }
}
