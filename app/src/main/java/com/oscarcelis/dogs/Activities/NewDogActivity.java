package com.oscarcelis.dogs.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.oscarcelis.dogs.MainActivity;
import com.oscarcelis.dogs.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NewDogActivity extends AppCompatActivity implements View.OnClickListener {

    private StorageReference mRefStorage;

    private static final int GALLERY_INTENT = 1;
    private static final int ELIMINARIMAGEN = 1;

    private TextView edtUrlImagenCanino;
    private TextView txtGenero;
    private TextView txtEspecialidad;
    private TextView txtTitulo;
    private TextView txtEmailUsuarioActual;
    Calendar C = Calendar.getInstance();
    private EditText edtFechaNacimientoCanino;

    private EditText edtNombreCanino;
    private EditText edtChipCanino;

    private RadioGroup radioGroup;
    private RadioButton radioMasculino;
    private RadioButton radioFemenino;

    private int dia, mes, anio;
    static final int DATE_ID = 'C';

    private EditText edtSenasCanino;
    private EditText edtProcedenciaCanino;

    private Button btnImgStorage;
    private Button btnGuardarDatosNuevoCanino;
    private Button btnCerrarSesion;


    private AutoCompleteTextView autoCompleteRaza;
    private AutoCompleteTextView autoCompleteColor;

    private Spinner spinnerEspecialidad;

    private ImageView imgMostrarImgCanino;

    private FirebaseAuth firebaseAuth;

    DatabaseReference mRootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dog);

        mRefStorage = FirebaseStorage.getInstance().getReference();
        mRootReference = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth datosUsuario = firebaseAuth.getInstance();

        btnImgStorage = findViewById(R.id.btnImgStorage);
        imgMostrarImgCanino = findViewById(R.id.imgMostrarImgCanino);

        edtNombreCanino = findViewById(R.id.edtNombreCanino);
        edtChipCanino = findViewById(R.id.edtChip);

        radioGroup = findViewById(R.id.radioGeneroGroup);
        radioMasculino = findViewById(R.id.radioButtonMasculino);
        radioFemenino = findViewById(R.id.radioButtonFemenino);

        txtGenero = findViewById(R.id.txtGenero);
        txtEspecialidad = findViewById(R.id.txtEspecialidad);
        txtTitulo = findViewById(R.id.txtTitulo);

        //Inicia fecha de nacimiento
        dia = C.get(Calendar.DAY_OF_MONTH);
        mes = C.get(Calendar.MONTH);
        anio = C.get(Calendar.YEAR);
        edtFechaNacimientoCanino = findViewById(R.id.edtFechaNacimientoCanino);
        //Termina fecha de nacimiento

        edtSenasCanino = findViewById(R.id.edtSenasCanino);
        edtProcedenciaCanino = findViewById(R.id.edtProcedenciaCanino);

        edtUrlImagenCanino = findViewById(R.id.edtUrlImagenCanino);
        edtUrlImagenCanino.setVisibility(View.INVISIBLE);

        txtEmailUsuarioActual = findViewById(R.id.txtEmailUsuarioActual);
        txtEmailUsuarioActual.setText("Estás autenticado como " + datosUsuario.getCurrentUser().getEmail());

        autoCompleteRaza = findViewById(R.id.autRazaCanino);
        autoCompleteColor = findViewById(R.id.autColorCanino);

        spinnerEspecialidad = findViewById(R.id.spinnerEspecialidadCanino);

        btnGuardarDatosNuevoCanino = findViewById(R.id.btnGuardarDatosNuevoCanino);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);


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

        imgMostrarImgCanino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
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


        edtFechaNacimientoCanino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioMasculino.isChecked()) {
                    txtGenero.setText("M");
                }
                if (radioFemenino.isChecked()) {
                    txtGenero.setText("F");
                }
            }
        });

        //Autocomplete para razas
        autoCompleteRaza = findViewById(R.id.autRazaCanino);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.RAZAS, android.R.layout.simple_list_item_1);

        autoCompleteRaza.setAdapter(adapter);


        //Autocomplete para colores del canino
        autoCompleteColor = findViewById(R.id.autColorCanino);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.COLORES_CANINOS, android.R.layout.simple_list_item_1);

        autoCompleteColor.setAdapter(adapter1);


        //Spinner especialidades
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array .ESPECIALIDADES, android.R.layout.simple_spinner_item);
        spinnerEspecialidad.setAdapter(adapter2);



    }



    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case DATE_ID:
                return new DatePickerDialog(this, DateSetListener, anio, mes, dia);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener DateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                  anio = year;
                  mes = month;
                  dia = dayOfMonth;
                  colocarFecha();
        }
    };

    private void colocarFecha(){
            edtFechaNacimientoCanino.setText((anio)+"-"+(mes)+"-"+(dia));
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
                    imgMostrarImgCanino.setEnabled(false);
                    btnImgStorage.setEnabled(false);
                    edtUrlImagenCanino.setText("" + mRefStorage.child("fotos_caninos").child(uri.getLastPathSegment()));

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

    private void validarChip(){
        String chipCanino = edtChipCanino.getText().toString();

    }









    private void guardarDatosCanino(){

            String urlImagenCanino = edtUrlImagenCanino.getText().toString();
            String nombre = edtNombreCanino.getText().toString();
            String chipCanino = edtChipCanino.getText().toString();
            String razaCanino = autoCompleteRaza.getText().toString();

            //String sexoCanino = edtSexoCanino.getText().toString();
            String genero = txtGenero.getText().toString();

            String nacimientoCanino = edtFechaNacimientoCanino.getText().toString();
            String colorCanino = autoCompleteColor.getText().toString();
            String senasCanino = edtSenasCanino.getText().toString();
            String procedenciaCanino = edtProcedenciaCanino.getText().toString();
            String especialidad = spinnerEspecialidad.getSelectedItem().toString();


        if(TextUtils.isEmpty(chipCanino)){
            Toast.makeText(this,"Ingrese un chip",Toast.LENGTH_SHORT).show();
            return;
        }


        if(TextUtils.isEmpty(nombre)){
            Toast.makeText(this,"Ingrese un nombre",Toast.LENGTH_SHORT).show();
            return;
        }


         if(TextUtils.isEmpty(razaCanino)){
            Toast.makeText(this, "Ingrese una raza", Toast.LENGTH_SHORT).show();
            return;
            }


        if(!radioFemenino.isChecked() && !radioMasculino.isChecked()) {
            Toast.makeText(this, "Elija un género", Toast.LENGTH_SHORT).show();
            return;
        }

            if(TextUtils.isEmpty(procedenciaCanino)){
                Toast.makeText(this, "Ingrese procedencia", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(colorCanino)){
                Toast.makeText(this, "Ingrese un color", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(senasCanino)){
                Toast.makeText(this, "Si no hay señales, escriba NINGUNA", Toast.LENGTH_SHORT).show();
                return;
            }


            Map<String, Object> credencialesCanino = new HashMap<>();

            credencialesCanino.put("urlImagenCanino", urlImagenCanino);
            credencialesCanino.put("nombre", nombre);
            credencialesCanino.put("chip", chipCanino);
            credencialesCanino.put("raza", razaCanino);

            credencialesCanino.put("genero", genero);

            credencialesCanino.put("fecha_nacimiento", nacimientoCanino);
            credencialesCanino.put("color", colorCanino);
            credencialesCanino.put("senales", senasCanino);
            credencialesCanino.put("procedencia", procedenciaCanino);
            credencialesCanino.put("especialidad", especialidad);


            mRootReference.child("Canino").push().setValue(credencialesCanino);

        }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.edtNombreCanino:

        }
    }
}
