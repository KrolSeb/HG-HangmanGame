package wisielec.wisielec.com.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public abstract class AbstractEditAccountActivity extends MainActivity {
    private static final String MESSAGE_GRANTED_READ_EXTERNAL_STORAGE_PERMISSION = "Przyznano uprawnienie do odczytu pamięci zewnętrznej.";
    private static final String DIALOG_TITLE = "Potrzebne uprawnienie";
    private static final String DIALOG_EXTERNAL_STORAGE_PERMISSION_REASON = "Odczyt z pamięci zewnętrznej jest potrzebny do zmiany avatara.";
    private static final String DIALOG_CONFIRM_ANSWER = "OK";
    private static final String DIALOG_DENY_ANSWER = "Cofnij";
    private static final String PERMISSION_STATE_KEY = "permissionState";
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 1;

    private SharedPreferences.Editor sharedPreferencesEditor;

    
    public void grantReadExternalStoragePermission(){
        setSharedPreferencesObjects();
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion <= Build.VERSION_CODES.LOLLIPOP_MR1){
            passPermissionState(true);
        }
        else if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            requestStoragePermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                passPermissionState(true);
                showToast();
            }
            else {
                passPermissionState(false);
            }
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(AbstractEditAccountActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            passPermissionState(false);
            buildAlertDialog().show();
        }
        else {
            ActivityCompat.requestPermissions(AbstractEditAccountActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_CODE);
        }
    }


    @SuppressLint("CommitPrefEdits")
    private void setSharedPreferencesObjects(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    private void passPermissionState(boolean permissionState){
        sharedPreferencesEditor.putBoolean(PERMISSION_STATE_KEY, permissionState);
        sharedPreferencesEditor.apply();
    }

    private AlertDialog buildAlertDialog(){
        return new AlertDialog.Builder(AbstractEditAccountActivity.this).setTitle(DIALOG_TITLE)
                .setMessage(DIALOG_EXTERNAL_STORAGE_PERMISSION_REASON)
                .setPositiveButton(DIALOG_CONFIRM_ANSWER, (dialog, which) ->
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_CODE))
                .setNegativeButton(DIALOG_DENY_ANSWER, (dialog, which) -> dialog.dismiss())
                .create();
    }

    private void showToast(){
        Toast.makeText(AbstractEditAccountActivity.this, MESSAGE_GRANTED_READ_EXTERNAL_STORAGE_PERMISSION, Toast.LENGTH_SHORT).show();
    }

}


