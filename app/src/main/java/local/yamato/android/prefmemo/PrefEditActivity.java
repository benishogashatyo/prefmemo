package local.yamato.android.prefmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class PrefEditActivity extends AppCompatActivity {
    private int _selectedPrefNo = 0;
    private String _selectedPrefName = "";
    private DatabaseHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_edit);

        Intent intent = getIntent();
        _selectedPrefNo = intent.getIntExtra("selectedPrefNo", 0);
        _selectedPrefName = intent.getStringExtra("selectedPrefName");

        _helper = new DatabaseHelper(getApplicationContext());

        TextView tvPref = findViewById(R.id.tvPref);
        tvPref.setText(_selectedPrefName);

        SQLiteDatabase db = _helper.getWritableDatabase();
        Memo memo = DataAccess.findByPK(db, _selectedPrefNo);
        if(memo != null) {
            String content = memo.getContent();
            EditText etContent = findViewById(R.id.etContent);
            etContent.setText(content);
        }
    }
    @Override
    protected void onDestroy() {
        _helper.close();
        super.onDestroy();
    }
    public void onSaveButtonClick(View view) {
        EditText etContent = findViewById(R.id.etContent);
        String content = etContent.getText().toString();
        SQLiteDatabase db = _helper.getWritableDatabase();
        boolean exist = DataAccess.findRowByPK(db, _selectedPrefNo);
        if(exist) {
            DataAccess.update(db, _selectedPrefNo, _selectedPrefName, content);
        }else{
            DataAccess.insert(db, _selectedPrefNo, _selectedPrefName, content);
        }
        finish();
    }
}
