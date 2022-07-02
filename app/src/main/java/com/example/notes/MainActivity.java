package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDestinationBuilder;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

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

    private NoteViewModel noteViewModel;

    private ListNotesFragment allNotesFragment;
    private ListNotesFragment favoriteNotesFragment;
    private AddEditFragment addEditFragment;

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        allNotesFragment = ListNotesFragment.getInstance(this);
        favoriteNotesFragment = ListNotesFragment.getInstance(this);
        addEditFragment = AddEditFragment.getInstance(this);

        bottomNav = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNav,navController);




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