package org.tensorflow.lite.examples.detection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AdminMenuActivity extends AppCompatActivity {

    CardView cvAbsen, cvKehadiran, cvLogs, cvExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        cvAbsen = findViewById(R.id.cv_face_list);
        cvKehadiran = findViewById(R.id.cv_attendance);
        cvLogs = findViewById(R.id.cv_logs);
        cvExit = findViewById(R.id.cv_exit);

        cvAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMenuActivity.this, PersonList.class);
                startActivity(intent);
            }
        });

        cvKehadiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Masih Dalam Pengembangan", Toast.LENGTH_SHORT).show();
            }
        });

        cvLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Masih Dalam Pengembangan", Toast.LENGTH_SHORT).show();
            }
        });

        cvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        finish();
    }
}