package wisielec.wisielec.com.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wisielec.wisielec.com.R;
import wisielec.wisielec.com.interfaces.UpdateUserAvatarCallback;
import wisielec.wisielec.com.interfaces.UpdateUsernameCallback;
import wisielec.wisielec.com.interfaces.UserRemoveCallback;
import wisielec.wisielec.com.services.UserService;


public class EditAccountActivity extends AbstractEditAccountActivity {
    private static final String MESSAGE_DENIED_READ_EXTERNAL_STORAGE_PERMISSION = "Odmówiono przyznania uprawnień.";

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

    private static final String INTENT_FILE_TYPES = "image/*";
    private static final String INTENT_FILE_TYPE_JPEG = "image/jpeg";
    private static final String INTENT_FILE_TYPE_PNG = "image/jpeg";
    private static final String PERMISSION_STATE_KEY = "permissionState";
    private String[] mimeTypes = {INTENT_FILE_TYPE_JPEG, INTENT_FILE_TYPE_PNG};


    @BindView(R.id.avatarImageView)
    ImageView avatarImageView;
    @BindView(R.id.usernameTextView)
    TextView usernameTextView;
    @BindView(R.id.newUsernameEditText)
    EditText newUsernameEditText;
    @BindView(R.id.changeAvatarButton)
    Button changeAvatarButton;
    @BindView(R.id.saveButton)
    Button saveButton;
    @BindView(R.id.deleteAccountButton)
    Button deleteAccountButton;

    private View dialogAvatarView;
    private AlertDialog alertDialogAvatar;
    private ImageView dialogImageView;
    private Button dialogUploadButton;
    private Button dialogUpdateButton;
    private Uri imageURI;

    private Intent afterRemoveUserIntent;
    private UserService userService = UserService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        ButterKnife.bind(this);

        grantReadExternalStoragePermission();
        updateLayoutTextViews();
    }

    private void updateLayoutTextViews() {
        userService.getUserData(user -> {
            if (!user.getAvatarURL().isEmpty()) {
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
    }

    private void updateUserName() {
        String username = newUsernameEditText.getText().toString();
        if (!username.equals("")) {
            userService.updateUserName(username, new UpdateUsernameCallback() {
                @Override
                public void onSuccess() {
                    showToast(UPDATE_USERNAME_SUCCESS, Toast.LENGTH_SHORT);
                }

                @Override
                public void onFailed() {
                    showToast(UPDATE_USERNAME_FAILED, Toast.LENGTH_SHORT);
                }
            });
        }
    }

    private void updateAvatar() {
        if (imageURI != null) {
            userService.updateAvatar(imageURI, new UpdateUserAvatarCallback() {
                @Override
                public void onSuccess() {
                    showToast(DIALOG_UPDATE_AVATAR_SUCCESS, Toast.LENGTH_SHORT);
                }

                @Override
                public void onFailed() {
                    showToast(DIALOG_UPDATE_AVATAR_FAILED, Toast.LENGTH_SHORT);
                }
            });
        }

        alertDialogAvatar.dismiss();
    }

    @OnClick(R.id.changeAvatarButton)
    protected void onChangeAvatarButtonClick() {
        buildChangeAvatarDialog();
        disableUpdateButton();
        showCurrentAvatar();

        onDialogUploadButtonClick();
        onDialogUpdateButtonClick();

        alertDialogAvatar.show();
        dialogAvatarView = null;
    }

    @SuppressLint("InflateParams")
    private void buildChangeAvatarDialog() {
        LayoutInflater avatarInflater = LayoutInflater.from(this);
        dialogAvatarView = avatarInflater.inflate(R.layout.dialog_choose_avatar,null);

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

    private void disableUploadButton() {
        dialogUploadButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor((BUTTON_DISABLED_BACKGROUND_COLOR))));
        dialogUploadButton.setTextColor(Color.parseColor(BUTTON_DISABLED_TEXT_COLOR));
        dialogUploadButton.setEnabled(false);
    }

    private void enableUpdateButton() {
        dialogUpdateButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor((BUTTON_ENABLED_BACKGROUND_COLOR))));
        dialogUpdateButton.setTextColor(Color.parseColor(BUTTON_ENABLED_TEXT_COLOR));
        dialogUpdateButton.setEnabled(true);
    }

    private void enableUploadButton() {
        dialogUploadButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor((BUTTON_ENABLED_BACKGROUND_COLOR))));
        dialogUploadButton.setTextColor(Color.parseColor(BUTTON_ENABLED_TEXT_COLOR));
        dialogUploadButton.setEnabled(true);
    }

    private void showCurrentAvatar() {
        userService.getUserData(user -> {
            if (!user.getAvatarURL().isEmpty()) {
                Picasso.get().load(user.getAvatarURL()).into(dialogImageView);
            }
            else {
                avatarImageView.setImageResource(R.drawable.avatar);
            }
        });
    }

    protected void onDialogUploadButtonClick() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean permission = prefs.getBoolean(PERMISSION_STATE_KEY, false);
        if (permission) {
            enableUploadButton();
            dialogUploadButton.setOnClickListener(v -> openFileChooser());
        }
        else {
            disableUploadButton();
            showToast(MESSAGE_DENIED_READ_EXTERNAL_STORAGE_PERMISSION, Toast.LENGTH_LONG);
        }
    }

    protected void onDialogUpdateButtonClick() {
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
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(INTENT_FILE_TYPES);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    private void showToast(String message, int length) {
        Toast.makeText(EditAccountActivity.this, message, length).show();
    }

    @OnClick(R.id.deleteAccountButton)
    protected void onDeleteAccountButtonClick() {
        showDeleteAccountDialog();
    }

    private void showDeleteAccountDialog() {
        AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(EditAccountActivity.this);
        deleteDialogBuilder.setTitle(DIALOG_DELETE_ACCOUNT_HEADER_MESSAGE);
        deleteDialogBuilder.setMessage(DIALOG_DELETE_ACCOUNT_MAIN_MESSAGE);

        prepareIntentAfterRemoveAccount();
        deleteDialogBuilder.setPositiveButton(DIALOG_CONFIRM_ANSWER, (dialog, which) -> userService.removeUserAccount(new UserRemoveCallback() {
            @Override
            public void onSuccess() {
                startActivity(afterRemoveUserIntent);
                userService.logOut(() -> finish());
                showToast(DELETE_ACCOUNT_SUCCESS,Toast.LENGTH_LONG);
            }

            @Override
            public void onFailed() {
                showToast(DELETE_ACCOUNT_FAILED,Toast.LENGTH_LONG);
            }
        }));

        deleteDialogBuilder.setNegativeButton(DIALOG_DENY_ANSWER, (dialog, which) -> dialog.dismiss());
        AlertDialog deleteDialog = deleteDialogBuilder.create();
        deleteDialog.show();
    }

    private void prepareIntentAfterRemoveAccount() {
        afterRemoveUserIntent = new Intent(EditAccountActivity.this, SignUpActivity.class);
        afterRemoveUserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        afterRemoveUserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}
