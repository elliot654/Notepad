package com.example.notes;


import static com.example.notes.FileController.read;
import static com.example.notes.FileController.write;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.databinding.ListItemBinding;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.NoteViewHolder> {
    private Context context;
    private List<NoteObject> values;
    public CustomAdapter(Context context) {
        this.context = context;
        this.values = NoteManager.getInstance().getItemList();
    }
    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemBinding binding = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        NoteObject note = values.get(position);

        if (note.title == null || note.title.isEmpty()) {
            holder.binding.noteTitle.setVisibility(View.GONE);
        } else {
            holder.binding.noteTitle.setVisibility(View.VISIBLE);
            holder.binding.noteTitle.setText(note.title);
        }
        holder.binding.noteDetail.setText(note.content);

        // Click listener for opening note
        holder.itemView.setOnClickListener(v -> {
            Intent myIntent = new Intent(context, NoteActivity.class);
            myIntent.putExtra("Source", position);
            context.startActivity(myIntent);
        });

        // Long click listener for delete confirmation
        holder.itemView.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete this note?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        values.remove(position);
                        write(context);
                        read(context);
                        NoteManager.getInstance().setItemList(values);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(context, "Delete cancelled", Toast.LENGTH_SHORT).show());
            AlertDialog alert = builder.create();
            alert.show();
            return true; // consume event
        });
    }
    @Override
    public int getItemCount() {
        return values.size();
    }
    // ViewHolder class
    static class NoteViewHolder extends RecyclerView.ViewHolder {
        final ListItemBinding binding;
        NoteViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}