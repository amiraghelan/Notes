package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEditActivity extends AppCompatActivity {

    private EditText edtNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        edtNote = findViewById(R.id.edt_note);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        int requestCode = getIntent().getIntExtra("REQ_CODE",-1);

        if(requestCode == MainActivity.ADD_REQ_CODE){
            setTitle("Add note");
        }else{
            setTitle("Edit Note");
            edtNote.setText(getIntent().getStringExtra("NOTE"));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String noteText = edtNote.getText().toString();

        if(noteText.trim().isEmpty()){
            Toast.makeText(this,"note is empty",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("NOTE",noteText);
        int requestCode = getIntent().getIntExtra("REQ_CODE",-1);

        if(requestCode == MainActivity.EDIT_REQ_CODE){
            intent.putExtra("REQ_CODE",MainActivity.EDIT_REQ_CODE);
            intent.putExtra("ID",getIntent().getIntExtra("ID",-1));
        }else{
            intent.putExtra("REQ_CODE",MainActivity.ADD_REQ_CODE);
        }
        setResult(RESULT_OK,intent);
        finish();
    }
}