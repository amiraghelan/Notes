package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.notes.Model.Note;
import com.example.notes.data.NoteViewModel;
import com.example.notes.fragments.AddEditFragment;
import com.example.notes.fragments.ListNotesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListNotesFragment.ListNotesFragmentListener, AddEditFragment.AddEditFragmentListener {

    public static int ADD_REQ_CODE = 1;
    public static int EDIT_REQ_CODE = 2;

    private NoteViewModel noteViewModel;

    private ListNotesFragment allNotesFragment;
    private ListNotesFragment favoriteNotesFragment;
    private AddEditFragment addEditFragment;

    private BottomNavigationView bottomNav;

//    private NoteAdapter adapter;
//    private RecyclerView recyclerView;
//    private FloatingActionButton fabNewNote;

//    ActivityResultLauncher<Intent> addLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == RESULT_OK) {
//                        Intent data = result.getData();
//                        int requestCode = data.getIntExtra("REQ_CODE",-1);
//                        if (requestCode != -1){
//                            String noteText = data.getStringExtra("NOTE");
//                            String noteTitle = data.getStringExtra("TITLE");
//                            Note note = new Note(noteText,noteTitle);
//                            if(requestCode == ADD_REQ_CODE){
//                                noteViewModel.insert(note);
//                            }else if(requestCode == EDIT_REQ_CODE){
//                                int id = data.getIntExtra("ID",-1);
//                                if(id != -1){
//                                    note.setId(id);
//                                    noteViewModel.update(note);
//                                }
//                            }
//                        }
//
//                    }
//                }
//            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        allNotesFragment = ListNotesFragment.getInstance(this);
        favoriteNotesFragment = ListNotesFragment.getInstance(this);
        addEditFragment = AddEditFragment.getInstance(this);

        bottomNav = findViewById(R.id.bottom_nav);

        NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_all_notes:
                        changeFragment(allNotesFragment);
                        break;
                    case R.id.nav_favorite_notes:
                        changeFragment(favoriteNotesFragment);
                        break;
                    case R.id.nav_add:
                        changeFragment(addEditFragment);
                }
                return true;
            }
        };

        bottomNav.setOnItemSelectedListener(navListener);

        changeFragment(allNotesFragment);


        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                allNotesFragment.setAdapterData(notes);
            }
        });

        noteViewModel.getFavoriteNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                favoriteNotesFragment.setAdapterData(notes);

            }
        });


    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_delete_add:
                noteViewModel.deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onItemClick(Note note) {
        addEditFragment.fillData(note);
        bottomNav.setSelectedItemId(R.id.nav_add);
        changeFragment(addEditFragment);

    }

    @Override
    public void onDeleteClick(Note note) {
        noteViewModel.delete(note);
    }

    @Override
    public void onFavoriteClick(Note note) {
        noteViewModel.update(note);
    }

    @Override
    public void onAddNoteClick(Note note, int id) {
        if (id == -1) {
            noteViewModel.insert(note);
            return;
        }

        note.setId(id);
        noteViewModel.update(note);
    }
}