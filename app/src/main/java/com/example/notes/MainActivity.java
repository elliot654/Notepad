package com.example.notes;

import static com.example.notes.FileController.read;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CustomAdapter adapter;
    List<NoteObject> notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //data
        read(this);
        notes = NoteManager.getInstance().getItemList();
        //list
        RecyclerView listView = findViewById(R.id.note_list);
        adapter = new CustomAdapter(this);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
        //add button
        FloatingActionButton addButton = findViewById(R.id.add_btn);
        addButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, NoteActivity.class);
            this.startActivity(myIntent);
        });
    }
}