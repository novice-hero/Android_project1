package kr.hnu.android_project1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> activityResultLauncher;
    String id, pw, name; // 가입 화면에서 보내주는 데이터를 저장할 변수들
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void login(View v) {
        EditText inputid = (EditText) findViewById(R.id.inputID);
        EditText inputpw = (EditText) findViewById(R.id.inputPW);
        if (id == null & pw == null) {
            // id와 pw가 없다면 없다고 toast 메시지 생성
            Toast.makeText(MainActivity.this, "id와 pw가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        else if (inputid.getText().toString().equals(id) & inputpw.getText().toString().equals(pw)) {
            // id와 pw가 확인되면 메시지 함으로 이동 (받아올 데이터가 없으므로 startActivity)
            Intent intent2 = new Intent(this, ReceiveMsgActivity.class);
            startActivity(intent2);
        }
        else if (inputid.getText().toString() != id | inputpw.getText().toString() != pw) {
            // id 또는 pw가 다르면 잘못 입력되었다고 toast 메시지 생성
            Toast.makeText(MainActivity.this, "id 또는 pw가 잘못 입력되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void signup_main(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("PersonIn", new Person(id, pw, name));
        // intent에 PersonIn과 id,pw,name변수를 담은 person객체
        activityResultLauncher.launch(intent); // 받아올 데이터가 있으므로
    }

    void init() {
        activityResultLauncher = registerForActivityResult(new
                ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) { // RESULT_OK가 오면
                // 가입 화면에서 받은 데이터로 person 인스턴스를 만들어줌
                Person person = (Person) result.getData().getSerializableExtra("PersonOut");
                // 변수에 저장
                id = person.getId();
                pw = person.getPw();
            }
        });
    }
}