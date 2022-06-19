package com.example.loginkodepos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button btnLogin;
    Button btnRegis;

    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegis = (Button) findViewById(R.id.btnRegis);

        myDB = new DBHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || user.equals("")){
                    Toast.makeText(LoginActivity.this, "Silahkan masukkan kredensial", Toast.LENGTH_SHORT).show();

                }
                else
                {
                   Boolean result = myDB.checkusernamePassword(user,pass);
                   if(result==true){
                       Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                       startActivity(intent);
                   }
                   else
                   {
                       Toast.makeText(LoginActivity.this, "Kredensial tidak valid",Toast.LENGTH_SHORT).show();
                   }
                }
            }
        });
        btnRegis.setOnClickListener(v->{
            Intent reg = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(reg);
        });
    }
}