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

    private EditText editTitle;
    private EditText editText;
    private TextView detailsText;
    private List<NoteObject> notes;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        notes = NoteManager.getInstance().getItemList();

        editTitle = findViewById(R.id.note_title);
        editText = findViewById(R.id.note_content);
        detailsText = findViewById(R.id.note_details);
        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            position = notes.size(); // New note
        } else {
            position = extras.getInt("Source");
            if (position < notes.size()) {
                NoteObject note = notes.get(position);
                editTitle.setText(note.title);
                editText.setText(note.content);
                detailsText.setText("Words : " + note.wordCount);
            }
        }
        //listener for word count
        editText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                int wordCount = s.toString().trim().isEmpty() ? 0 : s.toString().trim().split("\\s+").length;
                detailsText.setText("Words : " + wordCount);
            }
        });
        //save button
        FloatingActionButton saveButton = findViewById(R.id.save_btn);
        saveButton.setOnClickListener(v -> {
            saveNote();
            finish();
        });
    }
    private void saveNote() {
        String title = editTitle.getText().toString();
        String content = editText.getText().toString();
        int wordCount = content.trim().isEmpty() ? 0 : content.trim().split("\\s+").length;

        NoteObject newNote = new NoteObject(
                title,
                content,
                wordCount,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        );

        if (position >= notes.size()) {
            // New note
            notes.add(newNote);
            position = notes.size() - 1;
        } else {
            notes.set(position, newNote);
        }
        // Move to front
        NoteObject updated = notes.remove(position);
        notes.add(0, updated);
        position = 0;
        // Save to storage
        NoteManager.getInstance().setItemList(notes);
        write(this);
    }

    @Override
    protected void onPause() { //navigation or close app
        super.onPause();
            saveNote();
    }
    public abstract class SimpleTextWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }
}