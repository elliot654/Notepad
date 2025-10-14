package com.example.notes;

import java.util.ArrayList;
import java.util.List;

public class NoteManager {
    private static NoteManager instance;
    private List<NoteObject> itemList = new ArrayList<>();
    private NoteManager(){
    }
    public static NoteManager getInstance(){
        if(instance == null){
            instance = new NoteManager();
        }
        return instance;
    }
    public List<NoteObject> getItemList(){
        return itemList;
    }
    public void setItemList(List<NoteObject> itemList){
        this.itemList = itemList;
    }
}
