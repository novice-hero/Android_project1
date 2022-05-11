package kr.hnu.android_project1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase readableDB, writableDB;
    String userID; // 내비게이션 액티비티로 로그인한 유저 아이디를 전달해줄 변수
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        readableDB = dbHelper.getReadableDatabase();
        writableDB = dbHelper.getWritableDatabase();
        userID = ""; // 로그인 화면이 실행될 때 마다 초기화
    }

    public void login(View v) {
        EditText inputid = findViewById(R.id.inputID);
        EditText inputpw = findViewById(R.id.inputPW);
        
        Cursor cursor = readableDB.rawQuery("SELECT id, password FROM users", null);
        String inputIdTemp = inputid.getText().toString(); // 로그인 할 id, pw를 저장할 변수
        String inputPwTemp = inputpw.getText().toString();
        String tempId = ""; // db에 저장된 id, pw를 입력한 id, pw와 같으면 저장할 변수
        String tempPw = "";

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String pw = cursor.getString(1);
            if (id.equals(inputIdTemp) & pw.equals(inputPwTemp)) { 
                // users 테이블에서 입력된 id,pw와 같은 데이터를 찾았다면 변수에 저장
                tempId = id;
                tempPw = pw;
            }
        }
        // 로그인 확인
        if (inputIdTemp.equals("") | inputPwTemp.equals("")) {
            Toast.makeText(MainActivity.this, "ID 또는 Password를 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        else if (inputIdTemp.equals(tempId) & inputPwTemp.equals(tempPw)) {
            Toast.makeText(MainActivity.this, "환영합니다!", Toast.LENGTH_SHORT).show();
            userID = tempId; // 로그인한 유저의 아이디를 저장해서 인텐트를 사용해서 다음 액티비티로 넘겨줌
            Intent intent2 = new Intent(this, NavActivity.class);
            intent2.putExtra("login", userID);
            startActivity(intent2);
        }
        else if (inputid.getText().toString() != tempId | inputpw.getText().toString() != tempPw) {
            Toast.makeText(MainActivity.this, "ID 또는 Password가 잘못 입력되었습니다.", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    public void signup_main(View v) { // 계정 생성 화면으로 넘어감
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}