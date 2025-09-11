package com.example.notes;

import static com.example.notes.FileController.write;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Objects;


public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        EditText editText = findViewById(R.id.edit_txt);
        List<String> items = NoteManager.getInstance().getItemList();

        int position;
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            position = items.size();
            editText.setText("new note");
        }else{
            position = extras.getInt("Source");
            editText.setText(items.get(position));
        }


        Button saveButton = findViewById(R.id.save_btn);
        saveButton.setOnClickListener(v -> {
            if(items.isEmpty() || extras==null){
                items.add(editText.getText().toString());
            }else{
                items.set(position, editText.getText().toString());
            }

            write(this);
            Intent myIntent = new Intent(NoteActivity.this, MainActivity.class);
            myIntent.putExtra("Response", editText.getText());
            this.startActivity(myIntent);
        });
    }

}