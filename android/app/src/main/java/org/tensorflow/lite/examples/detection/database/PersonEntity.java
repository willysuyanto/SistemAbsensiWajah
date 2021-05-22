package org.tensorflow.lite.examples.detection.database;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.tensorflow.lite.examples.detection.tflite.SimilarityClassifier;

import java.lang.reflect.Type;
import java.util.Collections;

@Entity(tableName = "tb_person")
public class PersonEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String nama;
    private String nip;
    private String imgUrl;
    @TypeConverters(RecConverter.class)
    private float[] recognition;

    public PersonEntity(String nama, String nip, float[] recognition, String imgUrl) {
        this.id = id;
        this.nama = nama;
        this.recognition = recognition;
        this.nip = nip;
        this.imgUrl = imgUrl;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public float[] getRecognition() {
        return recognition;
    }

    public void setRecognition(float[] recognition) {
        this.recognition = recognition;
    }
}


class RecConverter {
    @TypeConverter
    public static float[] toObject(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return null;
        }
        return gson.fromJson(data, float[].class);
    }

    @TypeConverter
    public static String toString(float[] myObjects) {
        Gson gson = new Gson();
        return gson.toJson(myObjects);
    }
}
