package com.example.notes;

import static com.example.notes.FileController.write;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        List<NoteObject> notes = NoteManager.getInstance().getItemList();
        //text
        EditText editTitle = findViewById(R.id.note_title);
        EditText editText = findViewById(R.id.note_content);
        TextView detailsText = findViewById(R.id.note_details);

        Bundle extras = getIntent().getExtras();
        int position;
        if (extras == null) {
            position = notes.size(); // new note
        } else {
            position = extras.getInt("Source");
            NoteObject note = notes.get(position);
            editTitle.setText(note.title);
            editText.setText(note.content);
            detailsText.setText("Words : " + note.wordCount);
        }
        //word counter
        editText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                detailsText.setText("Words : " + s.toString().split("\\s+").length);
            }
        });

        //save button
        FloatingActionButton saveButton = findViewById(R.id.save_btn);
        saveButton.setOnClickListener(v -> {
            String title = editTitle.getText().toString();
            String content = editText.getText().toString();
            NoteObject newNote = new NoteObject(title, content, content.split("\\s").length, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

            if(notes.isEmpty() || extras==null){
                notes.add(newNote);
            }else{
                notes.set(position, newNote);
            }
            //moves to front of list
            NoteObject updated = notes.remove(position);
            notes.add(0, updated);
            //saves data
            NoteManager.getInstance().setItemList(notes);
            write(this);
            //moves back to main activity
            Intent myIntent = new Intent(NoteActivity.this, MainActivity.class);
            myIntent.putExtra("Response", editText.getText());
            this.startActivity(myIntent);
        });
    }
    public abstract class SimpleTextWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }
}