package com.example.notes.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.notes.Model.Note;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Note>> favoriteNotes;

    NoteRepository(Application application) {
        NotesDataBase db = NotesDataBase.getINSTANCE(application);
        noteDao = db.noteDao();
        allNotes = noteDao.getAllNotes();
        favoriteNotes = noteDao.getFavoriteNotes();
    }

    LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    void insert(Note note) {
        NotesDataBase.databaseWriteExecutor.execute(() -> {
            noteDao.insert(note);
        });
    }

    void update(Note note) {
        NotesDataBase.databaseWriteExecutor.execute(() -> {
            noteDao.update(note);
        });
    }

    void delete(Note note) {
        NotesDataBase.databaseWriteExecutor.execute(() -> {
            noteDao.delete(note);
        });
    }

    void deleteAll() {
        NotesDataBase.databaseWriteExecutor.execute(() -> {
            noteDao.deleteAll();
        });
    }

    LiveData<List<Note>> getFavoriteNotes() {
        return favoriteNotes;
    }

}
