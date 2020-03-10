package com.example.sofra.data.local;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.sofra.data.model.clientMakeNewOrder.ClientMakeNewOrderItemForRoom;

import java.util.List;


@Dao
public interface ClientMakeNewOrderItemForRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ClientMakeNewOrderItemForRoom... orderItem);

    @Insert
    void add(ClientMakeNewOrderItemForRoom... orderItem);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ClientMakeNewOrderItemForRoom... orderItem);

    @Delete
    void delete(ClientMakeNewOrderItemForRoom... orderItem);

    @Update
    void updateUserDate(ClientMakeNewOrderItemForRoom orderItem);

//    @Query("SELECT * FROM clientNewOrder")
//    MutableLiveData<List<ClientMakeNewOrderItemForRoom>> getusers();

    @Query("SELECT * FROM clientNewOrder")
    List<ClientMakeNewOrderItemForRoom> getAllItems();
//    LiveData<List<ClientMakeNewOrderItemForRoom>> getAllItems();

    @Query("SELECT * FROM clientNewOrder WHERE  itemId = :id")
    ClientMakeNewOrderItemForRoom getUsersById(int id);


    @Query("SELECT * FROM clientNewOrder WHERE itemId = :id")
    LiveData<ClientMakeNewOrderItemForRoom> getSubjectById(int id);

//    @Query("select * from clientNewOrder where active = 1;")
//    ClientData checkIfUserExist();

    @Query("DELETE FROM clientNewOrder;")
    void deletAll();
}
