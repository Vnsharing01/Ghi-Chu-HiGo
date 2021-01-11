package com.example.ghichuhigo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.ghichuhigo.R;
import com.example.ghichuhigo.adapter.NotesAdapter;
import com.example.ghichuhigo.database.MySQLOpenHelper;
import com.example.ghichuhigo.object.Note;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD = 100;
    public static final int RESULT_CODE_SAVE = 0;
    public static final int RESULT_CODE_CANEL = 112;

    private Note note;

    private RecyclerView rlvListNote;
    private MySQLOpenHelper db;
    private List<Note> noteList;
    private ActionBar bar;
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rlvListNote = findViewById(R.id.rlv_Notes);

        bar = getSupportActionBar();
        bar.setTitle(" Higo Notes");


        db = new MySQLOpenHelper(this);
        note = new Note();

        initwwidget();
    }

    private void initwwidget(){
        noteList = db.selectDataNote();
        adapter = new NotesAdapter(noteList,this);
        noteList.clear();
        noteList.addAll(db.selectDataNote());

        rlvListNote.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager =  new LinearLayoutManager(this);
        rlvListNote.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_add:
                Intent intent = new Intent(MainActivity.this, GhiChuActivity.class);
                startActivityForResult(intent,REQUEST_CODE_ADD);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD){
            switch (resultCode){
                case RESULT_CODE_SAVE:
                    Note saveNote = (Note) data.getSerializableExtra("data");
                    db.insertDataNote(saveNote);
                    Toast.makeText(this,"Lưu thành công",Toast.LENGTH_SHORT).show();
                    noteList.clear();
                    noteList.addAll(db.selectDataNote());
        //          noteList.add(note);
                    adapter.notifyDataSetChanged();
                    break;
                case RESULT_CODE_CANEL:
                    noteList.clear();
                    noteList.addAll(db.selectDataNote());
                    //          noteList.add(note);
                    adapter.notifyDataSetChanged();
                    Log.d("tag", "onActivityResult: ĐÃ HUỶ!");
                    break;
            }
        }

    }
}
