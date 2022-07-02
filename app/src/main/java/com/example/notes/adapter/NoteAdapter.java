package com.example.notes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Model.Note;
import com.example.notes.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {


    private List<Note> notes = new ArrayList<>();
    private  NoteAdapter.ItemListeners listeners;

    public NoteAdapter(NoteAdapter.ItemListeners listeners){
        this.listeners = listeners;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.tvNote.setText(note.getNoteTitle());
        if(note.getTagCode()==1){
            holder.cBoxFavorite.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNote;
        ImageView imgDelete;
        CheckBox cBoxFavorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNote = itemView.findViewById(R.id.tv_note_text);
            imgDelete = itemView.findViewById(R.id.img_delete);
            cBoxFavorite = itemView.findViewById(R.id.cbox_favorite);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listeners.onItemClickLinter(notes.get(getAdapterPosition()));
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listeners.onDeleteClickListener(notes.get(getAdapterPosition()));
                }
            });

            cBoxFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tagCode = cBoxFavorite.isChecked()?1:0;
                    Note note = notes.get(getAdapterPosition());
                    note.setTagCode(tagCode);
                    listeners.onFavoriteClickListener(note);
                }
            });
        }
    }

    public interface ItemListeners{
        void onItemClickLinter(Note note);
        void onDeleteClickListener(Note note);
        void onFavoriteClickListener(Note note);
    }
}
