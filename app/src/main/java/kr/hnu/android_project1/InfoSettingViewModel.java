package kr.hnu.android_project1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InfoSettingViewModel extends ViewModel {
    // 개인 정보 설정 뷰모델
    private final MutableLiveData<String> mText;
    public InfoSettingViewModel() {
        mText = new MutableLiveData<>();
    }
    public LiveData<String> getText() {
        return mText;
    }
    public void setText(String s) {
        mText.setValue(s);
    }
}
