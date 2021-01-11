package com.example.ghichuhigo.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.ghichuhigo.R;
import com.example.ghichuhigo.activities.ChiTietActivity;
import com.example.ghichuhigo.database.MySQLOpenHelper;
import com.example.ghichuhigo.object.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<Note> noteList;
    private Context mContext;
    private MySQLOpenHelper openHelper;

    public static final String BUNDLE = "data";
    public static final String INTENT = "info";
    public NotesAdapter(List<Note> noteList, Context mContext) {
        this.noteList = noteList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // tạo view cho item trong rlv.
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.row_list_ghi_chu,parent,false);
        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        // lấy dữ liệu đối tượng ra view
        final Note note = noteList.get(position);
        openHelper = new MySQLOpenHelper(mContext);
        holder.tvTile.setText(note.getTitle());
        holder.tvContent.setText(note.getContent());

        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info = new Intent(mContext, ChiTietActivity.class);
                Bundle bundle = new Bundle();
                Note mNote = new Note();
                mNote.setID(note.getID());
                mNote.setTitle(holder.tvTile.getText().toString());
                mNote.setContent(holder.tvContent.getText().toString());
                bundle.putSerializable(BUNDLE, mNote);
                info.putExtra(INTENT, bundle);
                mContext.startActivity(info);
            }
        });
        holder.mItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xoá Note").setMessage("bạn thực sự muốn xoá Note này!");

                builder.setCancelable(true);
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openHelper.deleteDataNote(note.getID());
                        noteList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(mContext,"đã xoá",Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size(); // số lượng item trong view.
    }

    /**
     * Lớp nắm giữ cấu trúc view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mItemView;
        TextView tvTile, tvContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            tvContent = mItemView.findViewById(R.id.tv_content);
            tvTile = mItemView.findViewById(R.id.tv_title);
        }
    }
}
