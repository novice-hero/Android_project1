package kr.hnu.android_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText editId, editPw, editName; // edittext 변수들 생성
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // 각 edittext 변수들 마다 id 찾아서 배정
        editId = (EditText) findViewById(R.id.signup_inputID);
        editPw = (EditText) findViewById(R.id.signup_inputPW);
        editName = (EditText) findViewById(R.id.signup_inputName);

        // person 인스턴스 생성 (PersonIn인 intent)
        Person person = (Person) getIntent().getSerializableExtra("PersonIn");
        // 각각 person의 id, pw, name이 저장되있다면 edittext 변수에 저장
        if (person.getId() != null) {
            editId.setText(person.getId());
        }
        if (person.getPw() != null) {
            editPw.setText(person.getPw());
        }
        if (person.getName() != null) {
            editPw.setText(person.getName());
        }

    }
    public void signUp(View v) {
        Intent intent = new Intent();
        // intent에 PersonOut과 id, pw, name을 가진 Person객체를 넣어줌
        intent.putExtra("PersonOut", new Person(editId.getText().toString(),
                editPw.getText().toString(), editName.getText().toString()));
        setResult(RESULT_OK,intent); // RESULT_OK와 intent를 Home화면으로 보냄
        finish();
    }

    public void checkID(View v) {
        Toast.makeText(SignUpActivity.this, "Good ID", Toast.LENGTH_SHORT).show();
    }
}