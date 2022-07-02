package com.example.notes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Model.Note;
import com.example.notes.R;
import com.example.notes.adapter.NoteAdapter;

import java.util.List;

public class ListNotesFragment extends Fragment {

    public interface ListNotesFragmentListener{
        void onItemClick(Note note);
        void onDeleteClick(Note note);
        void onFavoriteClick(Note note);
    }

    private RecyclerView notesRecyclerView;
    public NoteAdapter adapter = new NoteAdapter(new NoteAdapter.ItemListeners() {
        @Override
        public void onItemClickLinter(Note note) {
            listener.onItemClick(note);
        }

        @Override
        public void onDeleteClickListener(Note note) {
            listener.onDeleteClick(note);
        }

        @Override
        public void onFavoriteClickListener(Note note) {
            listener.onFavoriteClick(note);
        }

    });

    private ListNotesFragmentListener listener;

    public static ListNotesFragment getInstance(ListNotesFragmentListener listener){
        ListNotesFragment fragment = new ListNotesFragment();
        fragment.listener =listener;
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notes,container,false);
//
//        notesRecyclerView = v.findViewById(R.id.rcl_notes);
//        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        notesRecyclerView.setAdapter(adapter);
        return v;
    }

    public void setAdapterData(List<Note> notes){
        adapter.setNotes(notes);
    }
}
