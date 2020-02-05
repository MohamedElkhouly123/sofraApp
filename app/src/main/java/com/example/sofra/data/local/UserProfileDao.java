package com.example.sofra.data.local;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;



import java.util.List;


@Dao
public interface UserProfileDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insert(Client... user);
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    void update(Client... user);
//
//    @Delete
//    void delete(Client... user);
//
//    @Update
//    void updateUserDate(Client userModel);

//    @Query("SELECT * FROM user")
//    MutableLiveData<List<Client>> getusers();

//    @Query("SELECT * FROM user")
//    LiveData<List<Client>> getAllSubject();

//    @Query("SELECT * FROM user WHERE  subject_id= :id")
//    Client getUsersById(int id);


//    @Query("SELECT * FROM user WHERE subject_id = :id")
//    LiveData<Client> getSubjectById(int id);

//    @Query("select * from user where active = 1;")
//    ClientData checkIfUserExist();

//    @Query("DELETE FROM user;")
//    void deletAll();
}
