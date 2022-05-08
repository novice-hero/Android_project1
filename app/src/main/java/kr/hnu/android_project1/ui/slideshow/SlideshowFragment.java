package kr.hnu.android_project1.ui.slideshow;

import static kr.hnu.android_project1.NavActivity.userID;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import kr.hnu.android_project1.DBHelper;
import kr.hnu.android_project1.NavActivity;
import kr.hnu.android_project1.SignUpActivity;
import kr.hnu.android_project1.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {
    // 개인 정보 설정
    private FragmentSlideshowBinding binding;
    DBHelper dbHelper;
    SQLiteDatabase readableDB, writableDB;
    TextView tv_name;
    EditText et_pw, et_name;
    Button btn_change;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class); // 뷰모델 생성

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHelper = new DBHelper(getActivity());
        readableDB = dbHelper.getReadableDatabase();
        writableDB = dbHelper.getWritableDatabase();
        tv_name = binding.infoTextID; // 아이디를 보여줄 텍스트뷰
        et_pw = binding.infoInputPW;
        et_name = binding.infoInputName;
        btn_change = binding.infoBtnChange;

        slideshowViewModel.setText(userID); // 뷰모델에 유저 id 저장
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), tv_name::setText);
        // 뷰모델에 저장된 유저 id를 가져와서 id 부분 텍스트뷰에 넣어서 보여줌

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues row;
                String tempId, tempPw, tempName;
                row = new ContentValues();

                tempId = tv_name.getText().toString();
                tempPw = et_pw.getText().toString();
                tempName = et_name.getText().toString();
                row.put("password", tempPw);
                row.put("name", tempName);

                // 텍스트뷰에 있는 아이디와 같은 줄의 데이터 중 pw, name 업데이트
                writableDB.update("users", row, "id="+tempId, null);
                Toast.makeText(getActivity(), "변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                et_pw.setText("");
                et_name.setText("");
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