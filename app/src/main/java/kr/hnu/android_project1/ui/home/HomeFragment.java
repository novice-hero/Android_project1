package kr.hnu.android_project1.ui.home;

import static kr.hnu.android_project1.NavActivity.userID;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import kr.hnu.android_project1.MessageDBHelper;
import kr.hnu.android_project1.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    // 메시지 작성
    private FragmentHomeBinding binding;
    MessageDBHelper msgDBHelper;
    SQLiteDatabase readableDB, writableDB;
    Button btn_cancel, btn_send;
    EditText et_receiver, et_title, et_content;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        msgDBHelper = new MessageDBHelper(getActivity());
        readableDB = msgDBHelper.getReadableDatabase();
        writableDB = msgDBHelper.getWritableDatabase();

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
                
                // 데이터들을 row에 각각 저장해서 message DB에 저장
                ContentValues row = new ContentValues();
                row.put("sender", userID);
                row.put("receiver", str_receiver);
                row.put("title", str_title);
                row.put("content", str_content);
                row.put("date", str_date);
                writableDB.insert("message", null, row);
                Toast.makeText(getActivity(), "메시지 전송이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                et_receiver.setText(""); // 입력 칸 초기화
                et_title.setText("");
                et_content.setText("");
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