package kr.hnu.android_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText editId, editPw, editName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editId = (EditText) findViewById(R.id.signup_inputID);
        editPw = (EditText) findViewById(R.id.signup_inputPW);
        editName = (EditText) findViewById(R.id.signup_inputName);

        Person person = (Person) getIntent().getSerializableExtra("PersonIn");
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
        switch (v.getId()) {
            case R.id.signup_btn_signup:
                Intent intent = new Intent();
                intent.putExtra("PersonOut", new Person(editId.getText().toString(),
                        editPw.getText().toString(), editName.getText().toString()));
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    public void checkID(View v) {
        Toast.makeText(SignUpActivity.this, "Good ID", Toast.LENGTH_SHORT).show();
    }
}