package com.android.hangman.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public abstract class MainActivity extends AppCompatActivity {
    private static final String DIALOG_TITLE = "Brak połączenia internetowego.";
    private static final String DIALOG_MESSAGE = "Połącz się z internetem, aby kontynuować";
    private static final String DIALOG_CLOSE = "Zamknij";
    private AlertDialog noConnectivityDialog;
    private boolean isAlertDialogOpened = false;

    private BroadcastReceiver broadcastReceiverConnectionChanged = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                boolean isConnectivityNotExists = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

                if (isConnectivityNotExists) {
                    showDialog();
                    changeDialogPositiveButtonState(false);
                    setIsAlertDialogOpened(true);
                }
                else {
                    if (getIsAlertDialogOpened()){
                        changeDialogPositiveButtonState(true);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiverConnectionChanged, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiverConnectionChanged);
    }

    private void changeDialogPositiveButtonState(boolean state){
        noConnectivityDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(state);
    }

    private void showDialog() {
        noConnectivityDialog = new AlertDialog.Builder(this)
                .setTitle(DIALOG_TITLE)
                .setMessage(DIALOG_MESSAGE)
                .setPositiveButton(DIALOG_CLOSE, (dialog, which) -> dialog.dismiss()).create();
        noConnectivityDialog.setCanceledOnTouchOutside(false);
        noConnectivityDialog.show();
    }

    private void setIsAlertDialogOpened(boolean isAlertDialogOpened) {
        this.isAlertDialogOpened = isAlertDialogOpened;
    }

    private boolean getIsAlertDialogOpened() {
        return isAlertDialogOpened;
    }

}