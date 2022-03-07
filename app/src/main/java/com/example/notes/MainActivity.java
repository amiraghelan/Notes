package com.example.notes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static int ADD_REQ_CODE = 1;
    public static int EDIT_REQ_CODE = 2;

    private NoteViewModel noteViewModel;
    private NoteAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fabNewNote;

    ActivityResultLauncher<Intent> addLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        int requestCode = data.getIntExtra("REQ_CODE",-1);
                        if (requestCode != -1){
                            if(requestCode == ADD_REQ_CODE){
                                Note note = new Note(data.getStringExtra("NOTE"));
                                noteViewModel.insert(note);
                            }else if(requestCode == EDIT_REQ_CODE){
                                int id = data.getIntExtra("ID",-1);
                                if(id != -1){
                                    Note note = new Note(data.getStringExtra("NOTE"));
                                    note.setId(id);
                                    noteViewModel.update(note);
                                }
                            }
                        }

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.rcl_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteAdapter(new NoteAdapter.ItemListeners() {
            @Override
            public void onItemClickLinter(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("REQ_CODE",EDIT_REQ_CODE);
                intent.putExtra("NOTE",note.getNoteText());
                intent.putExtra("ID",note.getId());
                addLauncher.launch(intent);
            }

            @Override
            public void onDeleteClickListener(Note note) {
                noteViewModel.delete(note);
            }
        });
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
            }
        });

        fabNewNote = findViewById(R.id.fab_add_note);
        fabNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("REQ_CODE",ADD_REQ_CODE);
                addLauncher.launch(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_delete_add:
                noteViewModel.deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}