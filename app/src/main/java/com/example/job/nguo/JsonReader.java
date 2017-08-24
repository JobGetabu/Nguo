package com.example.job.nguo;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * Created by JOB on 8/24/2017.
 */

public class JsonReader {
    public static final String TAG = JsonReader.class.getSimpleName();

    public static  <T> T readJsonStream(InputStream inputStream, Type typeOfT) throws IOException {
       Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        int pointer;
        while ((pointer = reader.read(buffer))!=-1){
            writer.write(buffer, 0,pointer);
        }

        String jsonString = writer.toString();
        Gson gson = new Gson();

        return gson.fromJson(jsonString, typeOfT);
    }
}
