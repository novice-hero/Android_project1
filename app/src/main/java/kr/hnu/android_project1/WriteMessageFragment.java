package kr.hnu.android_project1;

import static kr.hnu.android_project1.MainActivity.loginID;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import kr.hnu.android_project1.databinding.FragmentWriteMessageBinding;

public class WriteMessageFragment extends Fragment {
    // 메시지 작성 프래그먼트
    private FragmentWriteMessageBinding binding;
    private Button btn_cancel, btn_send;
    private EditText et_receiver, et_title, et_content;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_message, container, false);

        binding = FragmentWriteMessageBinding.inflate(inflater, container, false);
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
                Toast.makeText(getActivity(), "메시지 전송이 취소되었습니다.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean isSuccess = jsonResponse.getBoolean("success");
                            if (isSuccess) {
                                Toast.makeText(getActivity(), "메시지를 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
                                et_receiver.setText("");
                                et_title.setText("");
                                et_content.setText("");
                            } else {
                                Log.e("WriteMessageFragment", response);
                            }
                        } catch (JSONException e) {
                            Log.e("anyText",response);
                            e.printStackTrace();
                        }
                    }
                };
                WriteMessageRequest writeMessageRequest = new WriteMessageRequest(loginID, str_receiver, str_title, str_content, str_date, responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
                requestQueue.add(writeMessageRequest);
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