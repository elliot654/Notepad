package com.example.notes;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileController{
    public static void read(Context context){
        List<NoteObject> readList = new ArrayList<>();
        try{
            FileInputStream inputStream = context.openFileInput("notes.json");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            StringBuilder b = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                b.append(line);
            }
            JSONArray jsonArray = new JSONArray(b.toString());
            for(int i =0; i < jsonArray.length(); i++){
                JSONObject noteJson = jsonArray.getJSONObject(i);
                String title = noteJson.getString("title");
                String content = noteJson.getString("content");
                int wordCount = noteJson.getInt("wordCount");
                String accessed = noteJson.getString("accessed");
                NoteObject note = new NoteObject(title, content, wordCount, accessed);
                readList.add(note);
            }
            NoteManager.getInstance().setItemList(readList);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void write(Context context){
        List<NoteObject> items = NoteManager.getInstance().getItemList();
        try{
            FileOutputStream outputStream = context.openFileOutput("notes.json", MODE_PRIVATE);
            JSONArray container = new JSONArray();
            for(NoteObject note : items){
                JSONObject noteJson = new JSONObject();
                noteJson.put("title", note.title);
                noteJson.put("content", note.content);
                noteJson.put("wordCount", note.wordCount);
                noteJson.put("accessed", note.accessed);
                container.put(noteJson);
            }
            outputStream.write(container.toString().getBytes());
            outputStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
