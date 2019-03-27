package wisielec.wisielec.com.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import wisielec.wisielec.com.services.UserService;

import com.squareup.picasso.Picasso;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import wisielec.wisielec.com.R;

public class EditAccountActivity extends MainActivity {
    private static final String DIALOG_DELETE_ACCOUNT_HEADER_MESSAGE = "Czy na pewno?";
    private static final String DIALOG_DELETE_ACCOUNT_MAIN_MESSAGE = "Potwierdzenie operacji oznacza usunięcie konta użytkownika wraz z wszystkimi danymi. Czy chcesz kontynuować?";
    private static final String DIALOG_CONFIRM_ANSWER = "OK";
    private static final String DIALOG_DENY_ANSWER = "Odrzuć";
    private static final String DELETE_ACCOUNT_SUCCESS = "Usunięcie konta zakończone sukcesem.";
    private static final String DELETE_ACCOUNT_FAILED = "Nie udało się usunąć konta.";
    private static final String DIALOG_UPDATE_AVATAR_SUCCESS = "Zaktualizowano avatar.";
    private static final String DIALOG_UPDATE_AVATAR_FAILED = "Nie udało się zaktualizować avatara.";
    private static final String UPDATE_USERNAME_SUCCESS = "Zaktualizowano nazwę użytkownika.";
    private static final String UPDATE_USERNAME_FAILED = "Nie udało się zaktualizować nazwy użytkownika.";

    private static final String BUTTON_ENABLED_BACKGROUND_COLOR = "#0D2C4B";
    private static final String BUTTON_ENABLED_TEXT_COLOR = "#F4D170";
    private static final String BUTTON_DISABLED_BACKGROUND_COLOR = "#D1D1D1";
    private static final String BUTTON_DISABLED_TEXT_COLOR = "#EAEAEA";
    private static final int PICK_IMAGE_REQUEST = 1;

    @BindView(R.id.avatarImageView) ImageView avatarImageView;
    @BindView(R.id.usernameTextView) TextView usernameTextView;
    @BindView(R.id.newUsernameEditText) EditText newUsernameEditText;
    @BindView(R.id.changeAvatarButton) Button changeAvatarButton;
    @BindView(R.id.saveButton) Button saveButton;
    @BindView(R.id.deleteAccountButton) Button deleteAccountButton;

    private View dialogAvatarView;
    private AlertDialog alertDialogAvatar;
    private ImageView dialogImageView;
    private Button dialogUploadButton;
    private Button dialogUpdateButton;
    private Uri imageURI;

    private Intent afterRemoveUserIntent;

    private boolean isUsernameUpdated = false;
    private String username = "";

    private UserService userService = UserService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        ButterKnife.bind(this);

        updateLayoutTextViews();
    }

    private void updateLayoutTextViews() {
        userService.getUserData(user -> {
            if(!user.getAvatarURL().isEmpty()) {
                Picasso.get().load(user.getAvatarURL()).into(avatarImageView);
            }
            else {
                avatarImageView.setImageResource(R.drawable.avatar);
            }

            usernameTextView.setText(user.getUserName());
        });
    }

    @OnClick(R.id.saveButton)
    protected void onSaveButtonClick() {
        updateUserName();
        notifyAboutUsernameChange();
    }

    private void updateUserName() {
        username = newUsernameEditText.getText().toString();
        if (!username.equals("")){
            userService.updateUserName(username);
            isUsernameUpdated = true;
        }
    }

    private void notifyAboutUsernameChange(){
        if(isUsernameUpdated) {
            Toast.makeText(EditAccountActivity.this, UPDATE_USERNAME_SUCCESS,Toast.LENGTH_SHORT).show();
            isUsernameUpdated = false;
            username = "";
        }
        else {
            Toast.makeText(EditAccountActivity.this, UPDATE_USERNAME_FAILED,Toast.LENGTH_SHORT).show();
        }
    }

    private void updateAvatar() {
        if (imageURI != null){
            userService.updateAvatar(imageURI, result -> Toast.makeText(EditAccountActivity.this, DIALOG_UPDATE_AVATAR_SUCCESS,Toast.LENGTH_SHORT).show());
        }
        else {
            Toast.makeText(EditAccountActivity.this, DIALOG_UPDATE_AVATAR_FAILED,Toast.LENGTH_SHORT).show();
        }

        alertDialogAvatar.dismiss();
    }

    @OnClick(R.id.changeAvatarButton)
    protected void onChangeAvatarButtonClick(){
        buildChangeAvatarDialog();
        disableUpdateButton();
        showCurrentAvatar();

        onDialogUploadButtonClick();
        onDialogUpdateButtonClick();

        alertDialogAvatar.show();
        dialogAvatarView = null;
    }

    private void buildChangeAvatarDialog(){
        LayoutInflater avatarInflater = LayoutInflater.from(this);
        dialogAvatarView = avatarInflater.inflate(R.layout.dialog_choose_avatar, null);

        dialogImageView = dialogAvatarView.findViewById(R.id.dialogImageView);
        dialogUploadButton = dialogAvatarView.findViewById(R.id.uploadAvatar);
        dialogUpdateButton = dialogAvatarView.findViewById(R.id.updateAvatar);

        alertDialogAvatar = new AlertDialog.Builder(EditAccountActivity.this).setView(dialogAvatarView).create();
    }

    private void disableUpdateButton() {
        dialogUpdateButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor((BUTTON_DISABLED_BACKGROUND_COLOR))));
        dialogUpdateButton.setTextColor(Color.parseColor(BUTTON_DISABLED_TEXT_COLOR));
        dialogUpdateButton.setEnabled(false);
    }

    private void enableUpdateButton(){
        dialogUpdateButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor((BUTTON_ENABLED_BACKGROUND_COLOR))));
        dialogUpdateButton.setTextColor(Color.parseColor(BUTTON_ENABLED_TEXT_COLOR));
        dialogUpdateButton.setEnabled(true);
    }

    private void showCurrentAvatar(){
        userService.getUserData(user -> {
            if(!user.getAvatarURL().isEmpty()) {
                Picasso.get().load(user.getAvatarURL()).into(dialogImageView);
            }
            else {
                avatarImageView.setImageResource(R.drawable.avatar);
            }
        });
    }

    protected void onDialogUploadButtonClick(){
        dialogUploadButton.setOnClickListener(v -> openFileChooser());
    }

    protected void onDialogUpdateButtonClick(){
        dialogUpdateButton.setOnClickListener(v -> updateAvatar());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageURI = data.getData();
            Picasso.get().load(imageURI).into(dialogImageView);
            enableUpdateButton();
        }
    }

    private void openFileChooser() {
        String[] mimeTypes = {"image/jpeg", "image/png"};
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }


    @OnClick(R.id.deleteAccountButton)
    protected void onDeleteAccountButtonClick() {
        showDeleteAccountDialog();
    }

    private void showDeleteAccountDialog() {
        AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(EditAccountActivity.this);
        deleteDialogBuilder.setTitle(DIALOG_DELETE_ACCOUNT_HEADER_MESSAGE);
        deleteDialogBuilder.setMessage(DIALOG_DELETE_ACCOUNT_MAIN_MESSAGE);

        deleteDialogBuilder.setPositiveButton(DIALOG_CONFIRM_ANSWER, (dialog, which) -> userService.removeUserAccount(new UserService.IRemoveUserCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(EditAccountActivity.this, DELETE_ACCOUNT_SUCCESS,Toast.LENGTH_SHORT).show();
                userService.logOut(() -> finish());
                prepareIntentAfterRemoveAccount();
                startActivity(afterRemoveUserIntent);
            }
            @Override
            public void onFailed() {
                Toast.makeText(EditAccountActivity.this, DELETE_ACCOUNT_FAILED,Toast.LENGTH_SHORT).show();
            }
        }));

        deleteDialogBuilder.setNegativeButton(DIALOG_DENY_ANSWER, (dialog, which) -> dialog.dismiss());

        AlertDialog deleteDialog = deleteDialogBuilder.create();
        deleteDialog.show();
    }

    private void prepareIntentAfterRemoveAccount(){
        afterRemoveUserIntent = new Intent(EditAccountActivity.this,SignInActivity.class);
        afterRemoveUserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        afterRemoveUserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}
