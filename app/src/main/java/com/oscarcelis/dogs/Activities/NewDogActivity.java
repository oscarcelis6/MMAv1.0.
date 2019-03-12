package com.oscarcelis.dogs.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.oscarcelis.dogs.MainActivity;
import com.oscarcelis.dogs.R;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class NewDogActivity extends AppCompatActivity {

    private StorageReference mRefStorage;
    private static final int GALLERY_INTENT = 1;
    private static final int ELIMINARIMAGEN = 1;

    private TextView txtEmailUsuarioActual;

    private EditText edtNombreCanino;
    private EditText edtChipCanino;
    private EditText edtRazaCanino;
    private EditText edtSexoCanino;
    private EditText edtFechaNacimientoCanino;
    private EditText edtColorCanino;
    private EditText edtSenasCanino;
    private EditText edtProcedenciaCanino;
    private EditText edtEspecialidadCanino;

    private Button btnGuardarDatosNuevoCanino;
    private Button btnCerrarSesion;
    private Button btnImgStorage;
    private Button btnBorrarFoto;

    private ImageView imgMostrarImgCanino;

    private FirebaseAuth firebaseAuth;

    DatabaseReference mRootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dog);

        mRefStorage = FirebaseStorage.getInstance().getReference();
        mRootReference = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth datosUsuario = firebaseAuth.getInstance();

        edtNombreCanino = findViewById(R.id.edtNombreCanino);
        edtChipCanino = findViewById(R.id.edtChip);
        edtRazaCanino = findViewById(R.id.edtRazaCanino);
        edtSexoCanino = findViewById(R.id.edtSexoCanino);
        edtFechaNacimientoCanino = findViewById(R.id.edtFechaNacimientoCanino);
        edtColorCanino = findViewById(R.id.edtColorCanino);
        edtSenasCanino = findViewById(R.id.edtSenasCanino);
        edtProcedenciaCanino = findViewById(R.id.edtProcedenciaCanino);
        edtEspecialidadCanino = findViewById(R.id.edtEspecialidadCanino);

        txtEmailUsuarioActual = findViewById(R.id.txtEmailUsuarioActual);
        txtEmailUsuarioActual.setText("Estás autenticado como "  + datosUsuario.getCurrentUser().getEmail());

        imgMostrarImgCanino = findViewById(R.id.imgMostrarImgCanino);

        btnGuardarDatosNuevoCanino = findViewById(R.id.btnGuardarDatosNuevoCanino);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnImgStorage = findViewById(R.id.btnPickFoto);
        btnBorrarFoto = findViewById(R.id.btnBorrarFoto);

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

        btnGuardarDatosNuevoCanino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               guardarDatosCanino();
            }
        });

        btnImgStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

          }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK){
            final Uri uri = data.getData();


            StorageReference filePath = mRefStorage.child("fotos_caninos").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    btnImgStorage.setEnabled(false);
                    btnImgStorage.setText("" + mRefStorage.child("fotos_caninos").child(uri.getLastPathSegment()));

                    Uri mostrarImagen = uri;
                    Picasso.get()
                            .load(mostrarImagen)
                            .fit()
                            .centerCrop()
                            .into(imgMostrarImgCanino);
                }
            });
        }
    }


    private void cerrarSesion() {
        firebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }



        private void guardarDatosCanino(){

            String nombre = edtNombreCanino.getText().toString();
            String chipCanino = edtChipCanino.getText().toString();
            String razaCanino = edtRazaCanino.getText().toString();
            String sexoCanino = edtSexoCanino.getText().toString();
            String nacimientoCanino = edtFechaNacimientoCanino.getText().toString();
            String colorCanino = edtColorCanino.getText().toString();
            String senasCanino = edtSenasCanino.getText().toString();
            String procedenciaCanino = edtProcedenciaCanino.getText().toString();
            String especialidadCanino = edtEspecialidadCanino.getText().toString();


            Map<String, Object> credencialesCanino = new HashMap<>();

            credencialesCanino.put("nombre", nombre);
            credencialesCanino.put("chip", chipCanino);
            credencialesCanino.put("raza", razaCanino);
            credencialesCanino.put("sexo", sexoCanino);
            credencialesCanino.put("fecha_nacimiento", nacimientoCanino);
            credencialesCanino.put("color", colorCanino);
            credencialesCanino.put("senales", senasCanino);
            credencialesCanino.put("procedencia", procedenciaCanino);
            credencialesCanino.put("especialidad", especialidadCanino);

            mRootReference.child("Canino").push().setValue(credencialesCanino);

        }




}
