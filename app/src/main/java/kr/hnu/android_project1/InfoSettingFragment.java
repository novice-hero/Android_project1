package kr.hnu.android_project1;

import static kr.hnu.android_project1.MainActivity.loginID;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import kr.hnu.android_project1.databinding.FragmentInfoSettingBinding;

public class InfoSettingFragment extends Fragment {
    private FragmentInfoSettingBinding binding;
    private TextView tv_name;
    private EditText et_pw, et_name;
    private Button btn_change;
    private Spinner spinner;
    private String departmentName;
    private AlertDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_setting, container, false);

        InfoSettingViewModel infoSettingViewModel = new ViewModelProvider(this).get(InfoSettingViewModel.class);
        // 뷰모델 생성
        binding = FragmentInfoSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tv_name = binding.infoTextID; // 아이디를 보여줄 텍스트뷰
        et_pw = binding.infoInputPW;
        et_name = binding.infoInputName;
        btn_change = binding.infoBtnChange;
        spinner = binding.infoDepartment;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
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
        infoSettingViewModel.setText(loginID); // 뷰모델에 유저 id 저장
        infoSettingViewModel.getText().observe(getViewLifecycleOwner(), tv_name::setText);
        // 뷰모델에 저장된 유저 id를 가져와서 id 부분 텍스트뷰에 넣어서 보여줌
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPassword = et_pw.getText().toString();
                String userName = et_name.getText().toString();
                String userDepartment = spinner.getSelectedItem().toString();
                if (userPassword.equals("") | userName.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    dialog = builder.setMessage("변경하려는 암호 또는 이름을 입력하세요.").setNegativeButton("Retry", null).create();
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                dialog = builder.setMessage("변경이 완료되었습니다.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create();
                                dialog.show();
                            } else {
                                Log.e("InfoSettingFragment", "failed");
                            }
                        } catch (JSONException e) {
                            Log.e("anyText",response);
                            e.printStackTrace();
                        }
                    }
                };
                UserInfoSettingRequest userInfoSettingRequest = new UserInfoSettingRequest(userPassword, userName, userDepartment, loginID, responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
                requestQueue.add(userInfoSettingRequest);
            }
        });
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}