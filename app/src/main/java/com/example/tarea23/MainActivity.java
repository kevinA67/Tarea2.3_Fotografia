package com.example.tarea23;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE = 101;
    static final int ACCESS_CAMERA = 201;
    String currentPhotoPath;
    Button btnTomarFoto, btnGuardar, btnList;
    ImageView imageView;
    EditText txtDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTomarFoto=(Button) findViewById(R.id.btnTomarFoto);
        btnGuardar=(Button) findViewById(R.id.btnGuardar);
        btnList=(Button) findViewById(R.id.btnList);
        imageView = (ImageView) findViewById(R.id.imageView);
        txtDescription = (EditText) findViewById(R.id.txtDescription);

        btnTomarFoto.setOnClickListener(View ->{
            PermisosCamara();
        });

        btnGuardar.setOnClickListener(View ->{
            SendData();
        });

        btnList.setOnClickListener(View ->{
            Intent intent=new Intent(getApplicationContext(), ActivityList.class);
            startActivity(intent);
        });
    }

    private void SendData() {
        if (txtDescription.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Favor llenar todos los campos.", Toast.LENGTH_LONG).show();
        } else {
            //mainBinding.imageView.setImageBitmap(signBitmap);
            SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
            SQLiteDatabase db = conexion.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put(Transacciones.description,txtDescription.getText().toString());
            datos.put(Transacciones.image, ConvertImageBase64(currentPhotoPath));

            Long resultado = db.insert(Transacciones.TableImage, Transacciones.id, datos);

            Toast.makeText(getApplicationContext(), "Imagen registrada correctamente " + resultado.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private String ConvertImageBase64(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imageArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imageArray, Base64.DEFAULT);
    }
    private void PermisosCamara() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, ACCESS_CAMERA);
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ACCESS_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(getApplicationContext(), "Se necesita permiso de la camara.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.toString();
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.tarea23.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE) {
            try {
                File foto = new File(currentPhotoPath);
                imageView.setImageURI(Uri.fromFile(foto));
            } catch (Exception ex) {
                ex.toString();
            }
        }
    }
}