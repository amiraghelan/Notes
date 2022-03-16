package com.example.notes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="note_title")
    private String noteTitle;

    @ColumnInfo(name="note_text")
    private String noteText;

    public Note(String noteText, String noteTitle) {
        this.noteText = noteText;
        this.noteTitle = noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle(){
        return noteTitle;
    }

    public int getId() {
        return id;
    }
}
