package com.example.loginkodepos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddData extends AppCompatActivity {
    EditText Provinsi, Kabupaten, Kecamatan, Kelurahan, KodePOS;
    Button SimpanData;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        Provinsi = findViewById(R.id.prov);
        Kabupaten = findViewById(R.id.kab);
        Kecamatan = findViewById(R.id.kec);
        Kelurahan = findViewById(R.id.kel);
        KodePOS = findViewById(R.id.kode);
        SimpanData = findViewById(R.id.btnsave);

        progressDialog = new ProgressDialog(AddData.this);
        progressDialog.setTitle("Tunggu beberapa saat");
        progressDialog.setMessage("Sukses Menyimpan");

        SimpanData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Provinsi.getText().length() > 0 && Kabupaten.getText().length() > 0 && Kecamatan.getText().length() > 0 &&
                Kelurahan.getText().length() > 0 && KodePOS.getText().length() > 0) {
                    simpandata(Provinsi.getText().toString(), Kabupaten.getText().toString(), Kecamatan.getText().toString(),
                            Kelurahan.getText().toString(), KodePOS.getText().toString());
                } else {
                    Toast.makeText(AddData.this, "Isi Semua", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Intent intent = getIntent();
        if (intent != null){
            id = intent.getStringExtra("id");
            Provinsi.setText(intent.getStringExtra("Provinsi"));
            Kabupaten.setText(intent.getStringExtra("Kabupaten"));
            Kecamatan.setText(intent.getStringExtra("Kecamatan"));
            Kelurahan.setText(intent.getStringExtra("Kelurahan"));
            KodePOS.setText(intent.getStringExtra("KodePOS"));
        }
    }

    private void simpandata(String prov, String kab, String kec, String kel, String kode) {
        Map<String, Object> POS = new HashMap<>();

        POS.put("Provinsi", prov);
        POS.put("Kabupaten", kab);
        POS.put("Kecamatan", kec);
        POS.put("Kelurahan", kel);
        POS.put("KodePOS", kode);

        progressDialog.show();

        if (id != null) {

            db.collection("CekKodePOS").document(id)
                    .set(POS)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddData.this, "Sukses Menyimpan Data", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddData.this, "Gagal Menyimpan data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {

            db.collection("CekKodePOS")
                    .add(POS)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(AddData.this, "Sukses Menyimpan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddData.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}