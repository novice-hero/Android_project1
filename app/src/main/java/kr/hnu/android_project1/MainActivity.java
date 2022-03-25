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
    String id, pw, name;
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
            Toast.makeText(MainActivity.this, "id와 pw가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        else if (inputid.getText().toString().equals(id) & inputpw.getText().toString().equals(pw)) {
            Intent intent2 = new Intent(this, ReceiveMsgActivity.class);
            startActivity(intent2);
            //Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
        }
        else if (inputid.getText().toString() != id | inputpw.getText().toString() != pw) {
            Toast.makeText(MainActivity.this, "id 또는 pw가 잘못 입력되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void signup_main(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("PersonIn", new Person(id, pw, name));
        activityResultLauncher.launch(intent);
    }

    void init() {
        activityResultLauncher = registerForActivityResult(new
                ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Person person = (Person) result.getData().getSerializableExtra("PersonOut");
                id = person.getId();
                pw = person.getPw();
                //name = person.getName();
            }
        });
    }
}