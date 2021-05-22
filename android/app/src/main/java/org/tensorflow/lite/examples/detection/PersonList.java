package org.tensorflow.lite.examples.detection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.examples.detection.database.AppDatabase;
import org.tensorflow.lite.examples.detection.database.PersonEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PersonList extends AppCompatActivity implements AttendantAdapter.OnDeleteListener{

    RecyclerView rvPerson;
    TextView tvJumlah, tvTambah;
    LinearLayout lyNodata;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<PersonEntity> personList = new ArrayList<>();
    AppDatabase db;
    AttendantAdapter.OnDeleteListener that;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);

        rvPerson = findViewById(R.id.rvAttend);
        tvJumlah = findViewById(R.id.tv_jumlah);
        tvTambah = findViewById(R.id.tv_addPerson);
        lyNodata = findViewById(R.id.ly_nodata);
        that = this;

        rvPerson.setHasFixedSize(true);
        db = AppDatabase.getDatabase(getApplicationContext());

        db.personDao()
                .findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PersonEntity>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(List<PersonEntity> personEntities) {
                        String jumlahData = personEntities.size()+"";
                        personList = personEntities;
                        adapter = new AttendantAdapter(personList, that);
                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        rvPerson.setLayoutManager(layoutManager);
                        rvPerson.setAdapter(adapter);
                        tvJumlah.setText(jumlahData);
                        rvPerson.setVisibility(View.VISIBLE);
                        lyNodata.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });

        tvTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonList.this, AddAttendantActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDelete(int position) {
        db.personDao()
                .delete(personList.get(position))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onComplete() {
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Berhasil Hapus Data", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "Gagal Hapus Data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}