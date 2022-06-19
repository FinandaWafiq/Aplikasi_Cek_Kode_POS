package com.example.loginkodepos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class ReadData extends AppCompatActivity {
    TextView Provinsi, Kabupaten, Kecamatan, Kelurahan, KodePOS;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);

        Provinsi = findViewById(R.id.tmprov);
        Kabupaten = findViewById(R.id.tmkab);
        Kecamatan = findViewById(R.id.tmkec);
        Kelurahan = findViewById(R.id.tmkel);
        KodePOS = findViewById(R.id.tmkode);

        progressDialog = new ProgressDialog(ReadData.this);
        progressDialog.setTitle("Tunggu beberapa saat");
        progressDialog.setMessage("Sedang diproses");

        Intent intent = getIntent();

        if (intent!= null){
            Provinsi.setText(intent.getStringExtra("Provinsi"));
            Kabupaten.setText(intent.getStringExtra("Kabupaten"));
            Kecamatan.setText(intent.getStringExtra("Kecamatan"));
            Kelurahan.setText(intent.getStringExtra("Kelurahan"));
            KodePOS.setText(intent.getStringExtra("KodePOS"));
        }

    }
}