package com.hdrescuer.supportyourdiscoveries.common;

import android.location.Address;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hdrescuer.supportyourdiscoveries.db.entity.AddressShort;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddressListConverter {


    @TypeConverter
    public static ArrayList<AddressShort> fromString(String value) {
        ArrayList<AddressShort> result = new ArrayList<>();

        String[] parts = value.split("#");

        for(int i = 0; i< parts.length; i++)
        {
            String parts2[] = parts[i].split("_");
            result.add(new AddressShort(parts2[0],Double.parseDouble(parts2[1]), Double.parseDouble(parts2[2])));
        }

        return result;
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<AddressShort> list) {
       String result = "";
        ArrayList<String> tmp = new ArrayList<>();
       for(int i = 0; i < list.size(); i++){
           tmp.add(list.get(i).getAddress() + '_' + Double.toString(list.get(i).getLatitud()) + '_' +Double.toString(list.get(i).getLongitud()));
       }

        for(int j = 0; j < tmp.size(); j++){
            result += tmp.get(j);

            if(j!=tmp.size()-1)
                result += '#';
        }


       return result;
    }
}
