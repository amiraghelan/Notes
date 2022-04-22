package com.example.notes.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "note_title")
    private String noteTitle;

    @ColumnInfo(name = "note_text")
    private String noteText;

    @ColumnInfo(name = "tag_code")
    private int tagCode;

    public Note(String noteText, String noteTitle, int tagCode) {
        this.noteText = noteText;
        this.noteTitle = noteTitle;
        this.tagCode = tagCode;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public int getTagCode() {
        return tagCode;
    }

    public void setTagCode(int tagCode) {
        this.tagCode = tagCode;
    }

    public int getId() {
        return id;
    }
}
