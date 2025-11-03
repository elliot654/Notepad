package com.example.notes;

import static com.example.notes.FileController.read;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get file data on app start
        read(this);
        //build list
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
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        for (NoteObject note : NoteManager.getInstance().getItemList()) {
            Log.d("NotesList", "Note title: " + note.content);
        }
    }
}