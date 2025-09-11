package com.example.notes;

import static com.example.notes.FileController.read;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    CustomAdapter adapter;
    List<String> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        read(this);
        items = NoteManager.getInstance().getItemList();
        //list
        ListView listView = findViewById(R.id.note_list);

        adapter = new CustomAdapter(this, items);
        listView.setAdapter(adapter);

        //add button
        Button addButton = findViewById(R.id.add_btn);
        addButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, NoteActivity.class);
            this.startActivity(myIntent);
        });

    }

}