package kr.hnu.android_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {
    private EditText editId, editPw, editName;
    private Button buttonCheck;
    private Spinner spinner;
    private String departmentName;
    private AlertDialog dialog;
    private boolean validate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editId = findViewById(R.id.signup_inputID);
        editPw = findViewById(R.id.signup_inputPW);
        editName = findViewById(R.id.signup_inputName);
        buttonCheck = findViewById(R.id.signup_btn_check);
        spinner = findViewById(R.id.signup_department);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departmentName = (String) spinner.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    public void onClickCheck(View v) {
        String userID = editId.getText().toString();
        if (userID.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            dialog = builder.setMessage("ID is empty.").setNegativeButton("Retry", null).create();
            dialog.show();
            return;
        }
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean isSuccess = jsonResponse.getBoolean("success");
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    if (isSuccess) {
                        dialog = builder.setMessage("Good ID").setPositiveButton("OK", null).create();
                        dialog.show();
                        editId.setEnabled(false);
                        buttonCheck.setEnabled(false);
                        validate = true;
                    } else {
                        dialog = builder.setMessage("ID is already used").setNegativeButton("Retry", null).create();
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        requestQueue.add(validateRequest);
    }
    public void onClickRegister(View v) {
        if (!validate) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            dialog = builder.setMessage("Press Check").setNegativeButton("Retry", null).create();
            dialog.show();
            return;
        }
        String userID = editId.getText().toString();
        String userPassword = editPw.getText().toString();
        String userName = editName.getText().toString();
        String userDepartment = spinner.getSelectedItem().toString();
        if (userID.equals("") | userPassword.equals("") | userDepartment.equals("") | userName.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            dialog = builder.setMessage("Field is empty.").setNegativeButton("Retry", null).create();
            dialog.show();
            return;
        }
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean isSuccess = jsonResponse.getBoolean("success");
                    if (isSuccess) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                        dialog = builder.setMessage("Created.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        }).create();
                        dialog.show();
                    } else {
                        Log.e("SignUpActivity", "register failed");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userDepartment, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        requestQueue.add(registerRequest);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}