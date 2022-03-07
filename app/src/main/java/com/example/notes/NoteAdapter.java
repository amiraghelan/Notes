package com.example.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.tvNote.setText(note.getTitle());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNote;
        ImageView imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNote = itemView.findViewById(R.id.tv_note_text);
            imgDelete = itemView.findViewById(R.id.img_delete);
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
        }
    }

    public interface ItemListeners{
        void onItemClickLinter(Note note);
        void onDeleteClickListener(Note note);
    }
}
