package com.example.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<NotesModel> notesList;
    DbHelper db;
    MainActivity main = new MainActivity();
    RecyclerAdapter(Context context, ArrayList<NotesModel> notesList) {
        this.context = context;
        this.notesList = notesList;
        db = new DbHelper(context);
    }
    RecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notes_layout, parent, false);
        ViewHolder view = new ViewHolder(v);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        holder.txtTitle.setText((notesList.get(pos)).title);
        holder.txtContent.setText(notesList.get(pos).content);
        holder.modifiedOn.setText(convertTimestampToDate(notesList.get(pos).getModifiedOn()));
        //holder.modifiedOn.setText(String.valueOf(notesList.get(pos).getModifiedOn()));

        String toPassTitle = holder.txtTitle.getText().toString();
        String toPassContent = holder.txtContent.getText().toString();

        // For Updating a note;
        holder.notesRow.setOnClickListener(v -> {
//            Toast.makeText(context, "Position " + pos + " is clicked", Toast.LENGTH_SHORT).show();
            startAnimation(v);
            // String todayDate = getCurrentDate();
            String createdOn = notesList.get(pos).getCreatedOn();

            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra("title", toPassTitle);
            intent.putExtra("content", toPassContent);
            intent.putExtra("authority", "update");
            intent.putExtra("currentDate", createdOn);
            context.startActivity(intent);
            if(context instanceof MainActivity) ((MainActivity) context).finish();
        });

        // For Deleting a note;
        holder.delete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Delete Note")
                    .setMessage("Are you sure about this?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        notesList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(position, notesList.size());
                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        db.deleteNote(toPassTitle);
//                        if(db.isTableEmpty()){
//                            Intent intent = new Intent(context, MainActivity.class);
//                            context.startActivity(intent);
//                            ((MainActivity) context).finish();
//                        }
                    })
                    .setNegativeButton("No", (dialogInterface, i) -> {

                    });
            builder.show();
        });

        holder.notesRow.setOnLongClickListener(v -> {

            startAnimation(v);

            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Delete Note")
                    .setMessage("Are you sure about this?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        notesList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(position, notesList.size());
                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        db.deleteNote(toPassTitle);
//                        if(db.isTableEmpty()){
//                            Intent intent = new Intent(context, MainActivity.class);
//                            context.startActivity(intent);
//                            ((MainActivity) context).finish();
//                        }
                    })
                    .setNegativeButton("No", (dialogInterface, i) -> {

                    });
            builder.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void startAnimation(View v){
        v.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(50)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    v.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .setInterpolator(new DecelerateInterpolator())
                            .start();
                }).start();
    }
    private String convertTimestampToDate(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return "Modified on " + sdf.format(date);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtContent, modifiedOn;
        CardView notesRow;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.title);
            txtContent = itemView.findViewById(R.id.content);
            notesRow = itemView.findViewById(R.id.notesRow);
            delete = itemView.findViewById(R.id.delete);
            modifiedOn = itemView.findViewById(R.id.modifiedOn);
        }
    }
}
