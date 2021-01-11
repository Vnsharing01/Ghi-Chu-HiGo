package com.example.ghichuhigo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ghichuhigo.R;
import com.example.ghichuhigo.database.MySQLOpenHelper;
import com.example.ghichuhigo.object.Note;

public class GhiChuActivity extends AppCompatActivity implements View.OnClickListener {

    private ActionBar bar;
    private EditText edtTitle, edtContent;
    private Button btnCancel, btnSave;

    private MySQLOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghi_chu);
        openHelper = new MySQLOpenHelper(this);

        bar = getSupportActionBar();
        bar.setTitle(" Higo Notes");
        bar.setDisplayHomeAsUpEnabled(true);

        initWidget();
    }

    private void initWidget() {
        edtTitle = findViewById(R.id.edt_Title);
        edtContent = findViewById(R.id.edt_Content);
        btnCancel = findViewById(R.id.btn_Cancel);
        btnSave = findViewById(R.id.btn_Save);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_Save:
                Note note = new Note();
                note.setTitle(String.valueOf(edtTitle.getText()));
                note.setContent(String.valueOf(edtContent.getText()));

                Intent abc = new Intent(GhiChuActivity.this, MainActivity.class);
                abc.putExtra("data",note);
                setResult(MainActivity.RESULT_CODE_SAVE,abc);
                finish();
                break;
            case R.id.btn_Cancel:
                setResult(MainActivity.RESULT_CODE_CANEL);
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
