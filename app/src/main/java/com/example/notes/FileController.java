package com.example.notes;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileController{
    public static void read(Context context){
        List<String> readList = new ArrayList<>();

        try{
            FileInputStream inputStream = context.openFileInput("notes.json");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            StringBuilder b = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                readList.add(line);
            }
            bufferedReader.close();
            NoteManager.getInstance().setItemList(readList);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void write(Context context){
        List<String> items = NoteManager.getInstance().getItemList();
        try{
            FileOutputStream outputStream = context.openFileOutput("notes.json", MODE_PRIVATE);
            OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);

            for(String str : items){
                bufferedWriter.write(str);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            streamWriter.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
