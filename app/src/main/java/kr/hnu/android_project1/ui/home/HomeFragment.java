package kr.hnu.android_project1.ui.home;

import static kr.hnu.android_project1.MainActivity.loginID;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import kr.hnu.android_project1.R;
import kr.hnu.android_project1.SendMessageRequest;
import kr.hnu.android_project1.UserInfoSettingRequest;
import kr.hnu.android_project1.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    // 메시지 작성
    private FragmentHomeBinding binding;
    private Button btn_cancel, btn_send;
    private EditText et_receiver, et_title, et_content;
    private AlertDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btn_cancel = binding.writeBtnCancel;
        btn_send = binding.writeBtnSend;
        et_receiver = binding.writeEtReceiver;
        et_title = binding.writeEtTitle;
        et_content = binding.writeEtContent;
        btn_cancel.setOnClickListener(new View.OnClickListener() { // 취소 버튼
            @Override // 취소 버튼을 누르면 내용들을 전부 빈칸으로 초기화
            public void onClick(View v) {
                et_receiver.setText("");
                et_title.setText("");
                et_content.setText("");
                Toast.makeText(getActivity(), "메시지 전송이 취소되었습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() { // 보내기 버튼
            @Override
            public void onClick(View v) {
                // 입력한 내용 저장
                String str_receiver = et_receiver.getText().toString();
                String str_title = et_title.getText().toString();
                String str_content = et_content.getText().toString();

                // 현재 시간을 저장
                SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                temp.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                String str_date = temp.format(date);

                if ( str_receiver.equals("") | str_title.equals("") | str_content.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                dialog = builder.setMessage("메시지를 보냈습니다.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create();
                                dialog.show();
                            } else {
                                Log.e("SendMessageFragment", response);
                            }
                        } catch (JSONException e) {
                            Log.e("anyText",response);
                            e.printStackTrace();
                        }
                    }
                };
                SendMessageRequest sendMessageRequest = new SendMessageRequest(loginID, str_receiver, str_title, str_content, str_date, responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
                requestQueue.add(sendMessageRequest);
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
