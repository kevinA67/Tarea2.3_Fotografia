package com.example.tarea23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import com.example.tarea23.Config.ListAdapter;

import java.util.ArrayList;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;
import Models.Images;

public class ActivityList extends AppCompatActivity {

    SQLiteConexion conexion;
    ArrayList<Images> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_images);

        conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        obtenerDatos();
    }

    private void obtenerDatos() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Images images = null;
        lista = new ArrayList<Images>();

        //Cursor de base de datos para recorrer los datos
        Cursor cursor = db.rawQuery(Transacciones.SelectAllImage, null);

        while (cursor.moveToNext()) {
            images=new Images();
            images.setId(cursor.getInt(0));
            images.setDescription(cursor.getString(1));

            // Obtener el dato Base64 como una cadena
            String base64String = cursor.getString(2);

            // Decodificar la cadena Base64 a un arreglo de bytes
            byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);

            // Convertir el arreglo de bytes a un Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

            images.setImage(bitmap);

            lista.add(images);
        }

        cursor.close();
        LlenarData();
    }

    private void LlenarData() {
        ListAdapter listAdapter = new ListAdapter(lista, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}