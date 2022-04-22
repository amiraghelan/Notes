package com.example.notes.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.Model.Note;
import com.example.notes.R;

public class AddEditFragment extends Fragment {

    public interface AddEditFragmentListener{
        void onAddNoteClick(Note note, int id);
    }

    AddEditFragmentListener listener;

    private EditText edtTitle;
    private EditText edtNote;
    private Button btnAddNote;

    private String noteText;
    private String noteTitle;

    private int id = -1;

    public static AddEditFragment getInstance(AddEditFragmentListener listener) {
        AddEditFragment addEditFragment = new AddEditFragment();
        addEditFragment.listener = listener;
        return addEditFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_edit, container, false);
        edtNote = v.findViewById(R.id.edt_note);
        edtTitle = v.findViewById(R.id.edt_title);
        btnAddNote = v.findViewById(R.id.btn_save_note);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteText = edtNote.getText().toString();
                String noteTitle = edtTitle.getText().toString();

                if(noteText.trim().isEmpty() || noteTitle.trim().isEmpty()){
                    Toast.makeText(getActivity(),"please fill both note and title",Toast.LENGTH_SHORT).show();
                    return;
                }

                Note note = new Note(noteText,noteTitle,0);

                listener.onAddNoteClick(note, id);

                resetData();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if(id != -1){
            edtNote.setText(noteText);
            edtTitle.setText(noteTitle);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        resetData();
    }

    public void fillData(Note note){
        noteText = note.getNoteText();
        noteTitle = note.getNoteTitle();
        id = note.getId();
        Log.d("filldata", "fillData: called" + note.getNoteTitle());
    }

    private void resetData(){
        id = -1;
        edtNote.setText("");
        edtTitle.setText("");
        noteTitle = "";
        noteText = "";
    }

}
