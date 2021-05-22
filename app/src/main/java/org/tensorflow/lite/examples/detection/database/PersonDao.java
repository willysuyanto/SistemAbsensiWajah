package org.tensorflow.lite.examples.detection.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(PersonEntity personEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Completable update(PersonEntity personEntity);

    @Delete
    Completable delete(PersonEntity personEntity);

    @Query("SELECT * FROM tb_person")
    Observable<List<PersonEntity>> findAll();

    @Query("SELECT * FROM tb_person WHERE id=:id")
    Observable<PersonEntity> findById(int id);
}
