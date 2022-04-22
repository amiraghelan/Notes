package com.example.notes.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notes.Model.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private final LiveData<List<Note>> allNotes;
    private final LiveData<List<Note>> favoriteNotes;
    private NoteRepository repository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
        favoriteNotes = repository.getFavoriteNotes();

    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
    public LiveData<List<Note>> getFavoriteNotes() {
        return favoriteNotes;
    }

    public void insert(Note note){
        repository.insert(note);
    }
    public void delete(Note note){
        repository.delete(note);
    }
    public void update(Note note){
        repository.update(note);
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
