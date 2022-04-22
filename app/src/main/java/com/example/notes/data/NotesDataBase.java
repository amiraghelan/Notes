package com.example.notes.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notes.Model.Note;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class},version = 1, exportSchema = false)
public abstract class NotesDataBase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static volatile NotesDataBase INSTANCE;
    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static NotesDataBase getINSTANCE(final Context context){
        if (INSTANCE == null){
            synchronized (NotesDataBase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotesDataBase.class, "notes_database").build();
                }
            }
        }
        return INSTANCE;
    }

}
