package kr.hnu.android_project1.ui.gallery;

import static kr.hnu.android_project1.NavActivity.userID;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.hnu.android_project1.MessageDBHelper;
import kr.hnu.android_project1.Messages;
import kr.hnu.android_project1.MyRecyclerViewAdapter;
import kr.hnu.android_project1.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {
    // 받은 메시지 함
    private FragmentGalleryBinding binding;
    MessageDBHelper messageDBHelper;
    SQLiteDatabase readableDB, writableDB;
    ArrayList<Messages> arrayList;
    MyRecyclerViewAdapter adapter;
    Cursor cursor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        messageDBHelper = new MessageDBHelper(getActivity());
        readableDB = messageDBHelper.getReadableDatabase();
        writableDB = messageDBHelper.getWritableDatabase();

        RecyclerView recyclerView = binding.receiveRecycler;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        adapter = new MyRecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        setListFromDB();

        return root;
    }

    private void setListFromDB() {
        arrayList.clear();
        cursor = messageDBHelper.getCursorMsg(); // 메시지 DB의 테이블을 선택함
        while (cursor.moveToNext()) { // 커서가 없을때까지
            if (cursor.getString(cursor.getColumnIndexOrThrow("receiver")).equals(userID)){
                // 로그인한 유저의 id와 받는이가 동일할 경우에 어레이리스트에 데이터들을 추가함
                arrayList.add(new Messages(cursor.getString(cursor.getColumnIndexOrThrow("sender")),
                        cursor.getString(cursor.getColumnIndexOrThrow("receiver")),
                        cursor.getString(cursor.getColumnIndexOrThrow("title")),
                        cursor.getString(cursor.getColumnIndexOrThrow("content")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date"))));
            }
        }
        cursor.close();
        adapter.notifyDataSetChanged(); // 정보가 변했다고 알려줌
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}