package org.tensorflow.lite.examples.detection.tflite;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import org.tensorflow.lite.examples.detection.database.AppDatabase;
import org.tensorflow.lite.examples.detection.database.PersonDao;
import org.tensorflow.lite.examples.detection.database.PersonEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SaveDataSet {
    public static HashMap<String, float[]> deSerializeHashMap(String fileName, Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        final HashMap<String, float[]> loadedData = new HashMap<>();

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
                        for (PersonEntity person : personEntities) {
                            loadedData.put(person.getNama(),person.getRecognition());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Log.d("FS",  fileName +".ser reloaded");

        return loadedData;
    }

    public static String saveBitmapToStorage(Bitmap image, String filename, Context context) throws IOException {
        boolean saved;
        OutputStream fos;
        String fileUrl;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + "FaceImage");
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            String storage = "/storage/emulated/0/DCIM/FaceImage";
            fileUrl = storage+"/"+filename+".png";
            fos = resolver.openOutputStream(imageUri);
        } else {
            String imagesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).toString() + File.separator + "FaceImage";
            String storage = "/storage/emulated/0/DCIM/FaceImage";
            fileUrl = storage+"/"+filename+".png";

            File file = new File(imagesDir);

            if (!file.exists()) {
                file.mkdir();
            }

            File images = new File(imagesDir, filename + ".png");
            fos = new FileOutputStream(images);

        }

        saved = image.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
        return fileUrl;
    }

    public static Bitmap readBitmapFromStorage(String imageName) {
        Bitmap image = null;
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root, "FaceRecognition/AttendanceImages");

        try {
            File myFile = new File(myDir,imageName);
            image = BitmapFactory.decodeStream(new FileInputStream(myFile));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return image;
    }

    public static void serializeHashMap(String name,String nip, float[] rec, String url, Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        db.personDao()
                .insert(new PersonEntity(name,nip,rec,url))
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context,"Berhasil Menyimpan Data Wajah", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(context,"Gagal Menyimpan Data Wajah", Toast.LENGTH_SHORT).show();
                    }
                });
        //Hawk.put(fileName, dataSet);
        Log.d("serializeHashMap: ", "Complete");
    }
}
