package wisielec.wisielec.com.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.User;

/**
 * Created by sebastian on 24.03.18.
 */

public class EditAccountActivity extends MainActivity {
    protected ImageView avatarImageView;
    protected TextView usernameTextView;
    protected EditText newUsernameEditText;
    protected Button uploadNewAvatarButton;
    protected Button saveButton;
    protected Button deleteAccountButton;

    private User user;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        avatarImageView = findViewById(R.id.avatarImageView);
        usernameTextView = findViewById(R.id.usernameTextView);
        newUsernameEditText = findViewById(R.id.newUsernameEditText);
        uploadNewAvatarButton = findViewById(R.id.uploadNewAvatarButton);
        saveButton = findViewById(R.id.saveButton);
        deleteAccountButton = findViewById(R.id.deleteAccountButton);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        if(!user.getAvatarURL().isEmpty()) {
            Picasso.get().load(user.getAvatarURL()).into(avatarImageView);
        }
        usernameTextView.setText(user.getUserName());

        onClickListeners();
    }

    protected void onClickListeners(){
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.uploadNewAvatarButton:
                        launchAvatarChoose();
                        break;
                    case R.id.saveButton:
                        confirmChanges();
                        break;
                    default:
                        break;
                }
            }
        };
        saveButton.setOnClickListener(listener);
        uploadNewAvatarButton.setOnClickListener(listener);
    }


    private void confirmChanges() {
        if (!newUsernameEditText.getText().toString().equals("")) {
            updateUserName();
        }
    }


    private void updateUserName() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser authenticatedUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference userReference = firebaseDatabase.getReference();
        user.setUserName(newUsernameEditText.getText().toString());
        userReference.child("users").child(authenticatedUser.getUid()).setValue(user);
    }

    public void launchAvatarChoose(){
        AlertDialog alertDialog = new AlertDialog.Builder(EditAccountActivity.this).setTitle("Wybierz avatar").setView(R.layout.dialog_choose_avatar).create();
        alertDialog.show();
    }

    private void updateAvatar(){

    }

}
