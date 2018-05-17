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

    protected View viewDialog;
    protected LayoutInflater inflater;

    private User user;
    private AlertDialog alertDialog;

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

        inflater = LayoutInflater.from(this);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        if(!user.getAvatarURL().isEmpty()) {
            Picasso.get().load(user.getAvatarURL()).into(avatarImageView);
        }
        usernameTextView.setText(user.getUserName());

        onClickListeners();
    }

    private void getImagesFromURLs(){
        ImageView imageView1 = viewDialog.findViewById(R.id.imageView1);
        Picasso.get().load("https://image.ibb.co/eHX6dy/astronaut.jpg").into(imageView1);

        ImageView imageView2 = viewDialog.findViewById(R.id.imageView2);
        Picasso.get().load("https://image.ibb.co/hEExkd/cat.jpg").into(imageView2);

        ImageView imageView3 = viewDialog.findViewById(R.id.imageView3);
        Picasso.get().load("https://image.ibb.co/cbGA5d/dog.jpg").into(imageView3);

        ImageView imageView4 = viewDialog.findViewById(R.id.imageView4);
        Picasso.get().load("https://image.ibb.co/fD5Rdy/duck.jpg").into(imageView4);

        ImageView imageView5 = viewDialog.findViewById(R.id.imageView5);
        Picasso.get().load("https://image.ibb.co/c9gckd/fish.jpg").into(imageView5);

        ImageView imageView6 = viewDialog.findViewById(R.id.imageView6);
        Picasso.get().load("https://image.ibb.co/cQxTrJ/frog.jpg").into(imageView6);

        ImageView imageView7 = viewDialog.findViewById(R.id.imageView7);
        Picasso.get().load("https://image.ibb.co/fFUxkd/guitar.jpg").into(imageView7);

        ImageView imageView8 = viewDialog.findViewById(R.id.imageView8);
        Picasso.get().load("https://image.ibb.co/gJzxkd/palm_tree.jpg").into(imageView8);

        ImageView imageView9 = viewDialog.findViewById(R.id.imageView9);
        Picasso.get().load("https://image.ibb.co/iVwckd/skater.jpg").into(imageView9);
    }

    protected void onClickListeners(){
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.uploadNewAvatarButton:
                        viewDialog = inflater.inflate(R.layout.dialog_choose_avatar, null);
                        getImagesFromURLs();
                        launchAvatarChoose();
                        viewDialog = null;
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
        alertDialog = new AlertDialog.Builder(EditAccountActivity.this).setTitle("Wybierz avatar").setView(viewDialog).create();
        alertDialog.show();
    }
}
