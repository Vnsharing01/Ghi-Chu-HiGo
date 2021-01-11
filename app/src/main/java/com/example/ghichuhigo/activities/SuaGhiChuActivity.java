package com.example.ghichuhigo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ghichuhigo.R;
import com.example.ghichuhigo.database.MySQLOpenHelper;
import com.example.ghichuhigo.object.Note;

import static com.example.ghichuhigo.activities.ChiTietActivity.RESULT_CODE_UPDATE;
import static com.example.ghichuhigo.adapter.NotesAdapter.BUNDLE;
import static com.example.ghichuhigo.adapter.NotesAdapter.INTENT;

public class SuaGhiChuActivity extends AppCompatActivity implements View.OnClickListener {

    private ActionBar bar;
    private MySQLOpenHelper openHelper;

    private EditText edtTitle, edtContent;
    private Button btnCancel, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_ghi_chu);

        openHelper = new MySQLOpenHelper(this);

        bar = getSupportActionBar();
        bar.setTitle(" Higo Notes");
        bar.setDisplayHomeAsUpEnabled(true);
        initWidget();
        nhanIntent();
    }
    private Note nhanIntent(){
        Note mNote = new Note();
        Intent edit = getIntent();
        Bundle bundle = edit.getBundleExtra("update");
        Note note = (Note) bundle.getSerializable("edit");
        mNote = openHelper.selectItemNote(note.getID());

        return mNote;
    }

    private void initWidget() {
        edtTitle = findViewById(R.id.edt_Title);
        edtContent = findViewById(R.id.edt_Content);
        btnCancel = findViewById(R.id.btn_Cancel);
        btnSave = findViewById(R.id.btn_Save);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        // todo: không nên setedt ở trong func nhanIntent() -> tránh trường hợp set text cũ khi update.
        edtTitle.setText(nhanIntent().getTitle());
        edtContent.setText(nhanIntent().getContent());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_Save:
                Note note = nhanIntent();
                note.setID(nhanIntent().getID());
                note.setTitle(String.valueOf(edtTitle.getText()));
                note.setContent(String.valueOf(edtContent.getText()));
                openHelper.updateDataNote(note);
                openHelper.selectItemNote(note.getID());

                Intent abc = new Intent(SuaGhiChuActivity.this, ChiTietActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(BUNDLE,note);
                abc.putExtra(INTENT,bundle);
                Toast.makeText(this,"sửa thành công",Toast.LENGTH_SHORT).show();
                setResult(RESULT_CODE_UPDATE,abc);
                break;
            case R.id.btn_Cancel:
                finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
