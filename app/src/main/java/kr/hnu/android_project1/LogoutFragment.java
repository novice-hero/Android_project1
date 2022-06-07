package kr.hnu.android_project1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import kr.hnu.android_project1.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {
    // 로그아웃 프래그먼트
    private FragmentLogoutBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        System.exit(0);

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}