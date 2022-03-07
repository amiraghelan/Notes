package com.example.notes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="note_text")
    private String noteText;

    public Note(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle(){
        if(noteText.length()>30){
            return noteText.substring(0,30);
        }
        return noteText;
    }

    public int getId() {
        return id;
    }
}
