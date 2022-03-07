package com.example.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private final LiveData<List<Note>> allNotes;
    private NoteRepository repository;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    void insert(Note note){
        repository.insert(note);
    }
    void delete(Note note){
        repository.delete(note);
    }
    void update(Note note){
        repository.update(note);
    }

    void deleteAll(){
        repository.deleteAll();
    }
}
