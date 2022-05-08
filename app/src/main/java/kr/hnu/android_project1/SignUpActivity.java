package kr.hnu.android_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText editId, editPw, editName; // edittext 변수들 생성
    DBHelper dbHelper;
    SQLiteDatabase readableDB, writableDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // 각 edittext 변수들 마다 id 찾아서 배정
        editId = findViewById(R.id.signup_inputID);
        editPw = findViewById(R.id.signup_inputPW);
        editName = findViewById(R.id.signup_inputName);

        dbHelper = new DBHelper(this);
        readableDB = dbHelper.getReadableDatabase();
        writableDB = dbHelper.getWritableDatabase();
    }

    public void onClick(View v) {
        ContentValues row;
        switch (v.getId()) {
            case R.id.signup_btn_check: // ID 중복 확인 버튼
                Cursor cursor = readableDB.rawQuery("SELECT id FROM users", null);
                String temp = "abc";
                String editIdTemp = editId.getText().toString();
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0); // id 저장
                    if (id.equals(editIdTemp)) temp = id; // 입력한 id와 저장되어있는 id 중 같은게 있다면 저장
                }
                if (editIdTemp.length() == 0) // 아무것도 입력 안했으면
                    Toast.makeText(SignUpActivity.this, "ID를 입력해주세요.", Toast.LENGTH_SHORT).show();
                else if (editIdTemp.equals(temp)) // 중복이라면
                    Toast.makeText(SignUpActivity.this, "이미 존재하는 ID 입니다.", Toast.LENGTH_SHORT).show();
                else // 사용가능
                    Toast.makeText(SignUpActivity.this, "사용 가능한 ID 입니다.", Toast.LENGTH_SHORT).show();
                cursor.close();
                break;

            case R.id.signup_btn_signup: // 생성 버튼
                row = new ContentValues();
                row.put("id", editId.getText().toString());
                row.put("password", editPw.getText().toString());
                row.put("name", editName.getText().toString());
                writableDB.insert("users", null, row);
                Toast.makeText(SignUpActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}