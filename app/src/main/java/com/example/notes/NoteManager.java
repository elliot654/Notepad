package com.example.notes;

import java.util.ArrayList;
import java.util.List;

public class NoteManager {
    private static NoteManager instance;
    private List<String> itemList = new ArrayList<>();

    String title;
    String content;
    int position;

    private NoteManager(){
    }
    public static NoteManager getInstance(){
        if(instance == null){
            instance = new NoteManager();
        }
        return instance;
    }
    public List<String> getItemList(){
        return itemList;
    }
    public void setItemList(List<String> itemList){
        this.itemList = itemList;
    }

}
