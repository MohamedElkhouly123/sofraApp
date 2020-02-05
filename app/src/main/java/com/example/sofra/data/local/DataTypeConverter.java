package com.example.sofra.data.local;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Date;
import java.util.List;

class DataTypeConverter {
    @TypeConverter
    public static List<String> fromString( String value){

        Type listType =new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public static String fromArrayList( List<String> list){
        Gson gson =new Gson();
        String json =gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static Date toDate(Long timestamp) {
        if (timestamp != null) {
            return new Date(timestamp);
        } else {
            return null;
        }
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        if (date != null) {
            return date.getTime();
        } else {
            return null;
        }
    }

}
