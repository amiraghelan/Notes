package com.example.notes.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.Model.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Update
    void update(Note note);

    @Query("SELECT * FROM notes_table")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM notes_table WHERE tag_code==1")
    LiveData<List<Note>> getFavoriteNotes();

    @Query("DELETE FROM notes_table")
    void deleteAll();
}
