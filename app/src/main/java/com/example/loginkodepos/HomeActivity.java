package com.example.loginkodepos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginkodepos.adapter.POSAdapter;
import com.example.loginkodepos.model.PosModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnAdd;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<PosModel> list = new ArrayList<>();
    private POSAdapter posAdapter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.DataKodePOS);
        btnAdd = findViewById(R.id.btnAdd);
        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setTitle("Tunggu beberapa saat");
        progressDialog.setMessage("Sedang diproses");
        posAdapter = new POSAdapter(getApplicationContext(), list);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddData = new Intent(getApplicationContext(), AddData.class);
                startActivity(AddData);
            }
        });

        posAdapter.setDialog(new POSAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Update Data", "Delete Data", "Read Data"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);

                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){

                            /* Pilihan untuk Edit Data */
                            case 0:
                                Intent edit = new Intent(getApplicationContext(),AddData.class);
                                edit.putExtra("id", list.get(pos).getId());
                                edit.putExtra("Provinsi",list.get(pos).getProvinsi());
                                edit.putExtra("Kabupaten", list.get(pos).getKabupaten());
                                edit.putExtra("Kecamatan", list.get(pos).getKecamatan());
                                edit.putExtra("Kelurahan", list.get(pos).getKelurahan());
                                edit.putExtra("KodePOS", list.get(pos).getKodePOS());
                                startActivity(edit);
                                break;

                            /* Pilihan Untuk Hapus Data */
                            case 1:
                                DeleteData(list.get(pos).getId());
                                break;
                            case 2:
                                Intent read = new Intent(getApplicationContext(), ReadData.class);
                                read.putExtra("id",list.get(pos).getId());
                                read.putExtra("Provinsi",list.get(pos).getProvinsi());
                                read.putExtra("Kabupaten", list.get(pos).getKabupaten());
                                read.putExtra("Kecamatan", list.get(pos).getKecamatan());
                                read.putExtra("Kelurahan", list.get(pos).getKelurahan());
                                read.putExtra("KodePOS", list.get(pos).getKodePOS());
                                startActivity(read);
                        }
                    }
                });
                dialog.show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(posAdapter);
    }

    private void DeleteData(String id) {
        progressDialog.show();
        db.collection("CekKodePOS").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(HomeActivity.this, "Gagal di hapus", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(HomeActivity.this, "Data Berhasil di hapus", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        getData();
                    }
                });
    }

    @Override
    protected void onStart(){
        super.onStart();
        getData();
    }

    private void getData() {
        progressDialog.show();

        db.collection("CekKodePOS")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                PosModel posModel = new PosModel(documentSnapshot.getString("Provinsi"), documentSnapshot.getString("Kabupaten"), documentSnapshot.getString("Kecamatan"),
                                        documentSnapshot.getString("Kelurahan"), documentSnapshot.getString("KodePOS"));
                                posModel.setId(documentSnapshot.getId());
                                list.add(posModel);
                            }
                            posAdapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(HomeActivity.this, "Data Gagal ", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}