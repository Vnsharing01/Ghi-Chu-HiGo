package com.example.ghichuhigo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ghichuhigo.R;
import com.example.ghichuhigo.database.MySQLOpenHelper;
import com.example.ghichuhigo.object.Note;

import static com.example.ghichuhigo.adapter.NotesAdapter.BUNDLE;
import static com.example.ghichuhigo.adapter.NotesAdapter.INTENT;

public class ChiTietActivity extends AppCompatActivity {
    public static final int  REQUEST_CODE_EDIT = 10;
    public static final int RESULT_CODE_UPDATE = 110;

    private TextView tvTitle, tvContent;
    private MySQLOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ghi Chú Higo");
        actionBar.setDisplayHomeAsUpEnabled(true);

        openHelper = new MySQLOpenHelper(this);

        initWidget();
        nhanIntent();

    }

    // nhận thông tin của note về
    private Note nhanIntent(){
        Note mNote = new Note();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(INTENT);
        Note note = (Note) bundle.getSerializable(BUNDLE);
        mNote.setID(note.getID());
        tvTitle.setText(note.getTitle());
        tvContent.setText(note.getContent());
        mNote = openHelper.selectItemNote(mNote.getID());
        return mNote;
    }

    private void initWidget() {
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu_chi_tiet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_update:
                Intent intent = new Intent(ChiTietActivity.this, SuaGhiChuActivity.class);
                Bundle bundle = new Bundle();
                Note note = new Note();
                note.setID(nhanIntent().getID());
                note.setTitle((String) tvTitle.getText());
                note.setContent((String) tvContent.getText());
                bundle.putSerializable("edit",note);
                intent.putExtra("update",bundle);
                startActivityForResult(intent, REQUEST_CODE_EDIT);
                break;
            case android.R.id.home:  // id mặc định cho nút quay về trên actionBar
                onBackPressed();    // func bắt sự kiện quay về trang trước đó.
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
