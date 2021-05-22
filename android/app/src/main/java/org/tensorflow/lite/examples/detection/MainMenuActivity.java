package org.tensorflow.lite.examples.detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    TextView tvAdmin;
    View vAbsenMasuk, vAbsenPulang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        tvAdmin = findViewById(R.id.tv_menuAdmin);
        vAbsenMasuk = findViewById(R.id.vAbsenMasuk);
        vAbsenPulang = findViewById(R.id.vAbsenPulang);


        tvAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        vAbsenMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Masih Dalam Pengembangan", Toast.LENGTH_SHORT).show();
            }
        });

        vAbsenPulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Masih Dalam Pengembangan", Toast.LENGTH_SHORT).show();
            }
        });


    }
}