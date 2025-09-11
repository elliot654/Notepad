package com.example.notes;


import static com.example.notes.FileController.read;
import static com.example.notes.FileController.write;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomAdapter extends ArrayAdapter{
    Context context;
    List<String> values;
    public CustomAdapter(Context context, List<String> values){
        super(context, R.layout.list_item, values);
        this.context = context;
        this.values = NoteManager.getInstance().getItemList();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);

            convertView.setOnClickListener(v -> {
                Intent myIntent = new Intent(context, NoteActivity.class);
                myIntent.putExtra("Source", position);
                context.startActivity(myIntent);
            });

            convertView.setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete this note?").setPositiveButton("delete", (dialog, which) -> {
                    values.remove(position);
                    NoteManager.getInstance().setItemList(values);
                    write(context);
                    notifyDataSetChanged();
                    read(context);
                    Toast.makeText(context,"item deleted",Toast.LENGTH_SHORT).show();
                }).setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(context,"delete cancelled",Toast.LENGTH_SHORT).show());
                AlertDialog alert = builder.create();
                alert.show();
                return false;
            });

        }
        TextView titleView = convertView.findViewById(R.id.note_title);
        titleView.setText(values.get(position));
        TextView noteView = convertView.findViewById(R.id.note_detail);
        noteView.setText(values.get(position));
        return convertView;
    }

}

