package wisielec.wisielec.com.interfaces;

public interface UserRegisterCallback {
    void onSuccess();
    void onFailed(String firebaseExceptionMessage);
}
