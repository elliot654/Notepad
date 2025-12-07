package com.example.notes;
public class NoteObject {
    String title;
    String content;
    int wordCount;
    String accessed;
    public NoteObject(String title, String content, int wordCount,  String accessed){
        this.title = title;
        this.content = content;
        this.wordCount = wordCount;
        this.accessed = accessed;
    }
}
