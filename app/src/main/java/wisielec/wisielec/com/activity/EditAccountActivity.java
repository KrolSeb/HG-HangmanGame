package wisielec.wisielec.com.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import wisielec.wisielec.com.services.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import wisielec.wisielec.com.R;

/**
 * Created by sebastian on 24.03.18.
 */

public class EditAccountActivity extends MainActivity {
    private final String DIALOG_DELETE_ACCOUNT_HEADER_MESSAGE = "Czy na pewno?";
    private final String DIALOG_DELETE_ACCOUNT_MAIN_MESSAGE = "Potwierdzenie operacji oznacza usunięcie konta użytkownika wraz z wszystkimi danymi. Czy chcesz kontynuować?";
    private final String DIALOG_CONFIRM_ANSWER = "OK";
    private final String DIALOG_DENY_ANSWER = "Odrzuć";
    private final String DELETE_ACCOUNT_SUCCESS = "Usunięcie konta zakończone sukcesem";
    private final String DELETE_ACCOUNT_FAILED = "Nie udało się usunąć konta";
    private final String UPDATE_DATA_SUCCESS = "Zaktualizowano dane";
    private final String UPDATE_DATA_FAILED = "Nie zaktualizowano danych";
    
    @BindView(R.id.avatarImageView) ImageView avatarImageView;
    @BindView(R.id.usernameTextView) TextView usernameTextView;
    @BindView(R.id.newUsernameEditText) EditText newUsernameEditText;
    @BindView(R.id.chooseNewAvatarButton) Button chooseNewAvatarButton;
    @BindView(R.id.saveButton) Button saveButton;
    @BindView(R.id.deleteAccountButton) Button deleteAccountButton;

    private ImageView imageView0;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private ImageView imageView7;
    private ImageView imageView8;

    private View avatarViewDialog;
    private AlertDialog avatarAlertDialog;

    private ArrayList<String> listOfURLs;
    private String imageURL = "";
    private boolean isAvatarUpdated = false;
    
    private String username = "";
    private boolean isUsernameUpdated = false;

    private UserService userService = UserService.getInstance();
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private Intent afterRemoveUserIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        ButterKnife.bind(this);
        listOfURLs = new ArrayList<>();

        initializeDatabaseVariables();
        updateLayoutTextViews();
        addImagesURLToList();
    }

    private void initializeDatabaseVariables() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users");
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
        updateAvatar();
        notifyAboutChanges();
    }

    private void updateUserName() {
        username = newUsernameEditText.getText().toString();
        if (!username.equals("")){
            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("userName").setValue(username);
            isUsernameUpdated = true;
        }
    }

    private void updateAvatar() {
        if (!imageURL.equals("")){
            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("avatarURL").setValue(imageURL);
            isAvatarUpdated = true;
        }
    }

    private void notifyAboutChanges(){
        if(isUsernameUpdated || isAvatarUpdated) {
            Toast.makeText(EditAccountActivity.this, UPDATE_DATA_SUCCESS,Toast.LENGTH_SHORT).show();
            isUsernameUpdated = false;
            isAvatarUpdated = false;
            username = "";
            imageURL = "";
        }
        else {
            Toast.makeText(EditAccountActivity.this, UPDATE_DATA_FAILED,Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.chooseNewAvatarButton)
    protected void onChooseNewAvatarButtonClick(){
        initDialogComponents();
        getImagesFromURLs();
        avatarAlertDialog = new AlertDialog.Builder(EditAccountActivity.this).setTitle("Wybierz avatar").setView(avatarViewDialog).create();
        avatarAlertDialog.show();

        final View.OnClickListener listener = view -> {
            switch (view.getId()){
                case R.id.imageView0:
                    imageURL = listOfURLs.get(0);
                    avatarAlertDialog.dismiss();
                    break;
                case R.id.imageView1:
                    imageURL = listOfURLs.get(1);
                    avatarAlertDialog.dismiss();
                    break;
                case R.id.imageView2:
                    imageURL = listOfURLs.get(2);
                    avatarAlertDialog.dismiss();
                    break;
                case R.id.imageView3:
                    imageURL = listOfURLs.get(3);
                    avatarAlertDialog.dismiss();
                    break;
                case R.id.imageView4:
                    imageURL = listOfURLs.get(4);
                    avatarAlertDialog.dismiss();
                    break;
                case R.id.imageView5:
                    imageURL = listOfURLs.get(5);
                    avatarAlertDialog.dismiss();
                    break;
                case R.id.imageView6:
                    imageURL = listOfURLs.get(6);
                    avatarAlertDialog.dismiss();
                    break;
                case R.id.imageView7:
                    imageURL = listOfURLs.get(7);
                    avatarAlertDialog.dismiss();
                    break;
                case R.id.imageView8:
                    imageURL = listOfURLs.get(8);
                    avatarAlertDialog.dismiss();
                    break;
                default:
                    break;
            }
        };

        imageView0.setOnClickListener(listener);
        imageView1.setOnClickListener(listener);
        imageView2.setOnClickListener(listener);
        imageView3.setOnClickListener(listener);
        imageView4.setOnClickListener(listener);
        imageView5.setOnClickListener(listener);
        imageView6.setOnClickListener(listener);
        imageView7.setOnClickListener(listener);
        imageView8.setOnClickListener(listener);

        avatarViewDialog = null;
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
                prepareAfterRemoveAccountIntent();
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

    private void prepareAfterRemoveAccountIntent(){
        afterRemoveUserIntent = new Intent(EditAccountActivity.this,SignInActivity.class);
        afterRemoveUserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        afterRemoveUserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }


    private void initDialogComponents(){
        LayoutInflater avatarInflater = LayoutInflater.from(this);
        avatarViewDialog = avatarInflater.inflate(R.layout.dialog_choose_avatar, null);
        imageView0 = avatarViewDialog.findViewById(R.id.imageView0);
        imageView1 = avatarViewDialog.findViewById(R.id.imageView1);
        imageView2 = avatarViewDialog.findViewById(R.id.imageView2);
        imageView3 = avatarViewDialog.findViewById(R.id.imageView3);
        imageView4 = avatarViewDialog.findViewById(R.id.imageView4);
        imageView5 = avatarViewDialog.findViewById(R.id.imageView5);
        imageView6 = avatarViewDialog.findViewById(R.id.imageView6);
        imageView7 = avatarViewDialog.findViewById(R.id.imageView7);
        imageView8 = avatarViewDialog.findViewById(R.id.imageView8);
    }

    private void getImagesFromURLs(){
        Picasso.get().load(listOfURLs.get(0)).into(imageView0);
        Picasso.get().load(listOfURLs.get(1)).into(imageView1);
        Picasso.get().load(listOfURLs.get(2)).into(imageView2);
        Picasso.get().load(listOfURLs.get(3)).into(imageView3);
        Picasso.get().load(listOfURLs.get(4)).into(imageView4);
        Picasso.get().load(listOfURLs.get(5)).into(imageView5);
        Picasso.get().load(listOfURLs.get(6)).into(imageView6);
        Picasso.get().load(listOfURLs.get(7)).into(imageView7);
        Picasso.get().load(listOfURLs.get(8)).into(imageView8);
    }

    private void addImagesURLToList(){
        listOfURLs.add("https://image.ibb.co/knWNJy/astronaut.png");
        listOfURLs.add("https://image.ibb.co/fo8PWJ/cat.png");
        listOfURLs.add("https://image.ibb.co/jWcUyy/chess.png");
        listOfURLs.add("https://image.ibb.co/n3hUyy/dog.png");
        listOfURLs.add("https://image.ibb.co/gegpyy/fish.png");
        listOfURLs.add("https://image.ibb.co/mazbdy/guitar.png");
        listOfURLs.add("https://image.ibb.co/dK6pyy/launch.png");
        listOfURLs.add("https://image.ibb.co/i6D8rJ/palm_tree.png");
        listOfURLs.add("https://image.ibb.co/fAHZWJ/skater.png");
    }
}
