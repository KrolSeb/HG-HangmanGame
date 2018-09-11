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

import java.util.ArrayList;

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
    protected ImageView imageView0;
    protected ImageView imageView1;
    protected ImageView imageView2;
    protected ImageView imageView3;
    protected ImageView imageView4;
    protected ImageView imageView5;
    protected ImageView imageView6;
    protected ImageView imageView7;
    protected ImageView imageView8;

    protected View viewDialog;
    protected LayoutInflater inflater;
    protected AlertDialog alertDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser authenticatedUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userReference;

    private ArrayList<String> listOfURLs;
    private String imageURL = "";
    private User user;

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

        listOfURLs = new ArrayList<>();
        addURLToList();

        initializeAccessToDatabase();
        onClickListeners();
    }

    private void addURLToList(){
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

    protected void onClickListeners(){
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.uploadNewAvatarButton:
                        initDialogComponents();
                        getImagesFromURLs();
                        chooseAvatar();
                        break;
                    case R.id.saveButton:
                        confirmChanges();
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
        saveButton.setOnClickListener(listener);
        uploadNewAvatarButton.setOnClickListener(listener);
    }

    private void initializeAccessToDatabase(){
        firebaseAuth = FirebaseAuth.getInstance();
        authenticatedUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userReference = firebaseDatabase.getReference();
    }

    protected void initDialogComponents(){
        inflater = LayoutInflater.from(this);
        viewDialog = inflater.inflate(R.layout.dialog_choose_avatar, null);
    }

    private void getImagesFromURLs(){
        imageView0 = viewDialog.findViewById(R.id.imageView0);
        Picasso.get().load(listOfURLs.get(0)).into(imageView0);

        imageView1 = viewDialog.findViewById(R.id.imageView1);
        Picasso.get().load(listOfURLs.get(1)).into(imageView1);

        imageView2 = viewDialog.findViewById(R.id.imageView2);
        Picasso.get().load(listOfURLs.get(2)).into(imageView2);

        imageView3 = viewDialog.findViewById(R.id.imageView3);
        Picasso.get().load(listOfURLs.get(3)).into(imageView3);

        imageView4 = viewDialog.findViewById(R.id.imageView4);
        Picasso.get().load(listOfURLs.get(4)).into(imageView4);

        imageView5 = viewDialog.findViewById(R.id.imageView5);
        Picasso.get().load(listOfURLs.get(5)).into(imageView5);

        imageView6 = viewDialog.findViewById(R.id.imageView6);
        Picasso.get().load(listOfURLs.get(6)).into(imageView6);

        imageView7 = viewDialog.findViewById(R.id.imageView7);
        Picasso.get().load(listOfURLs.get(7)).into(imageView7);

        imageView8 = viewDialog.findViewById(R.id.imageView8);
        Picasso.get().load(listOfURLs.get(8)).into(imageView8);
    }

    private void chooseAvatar(){
        alertDialog = new AlertDialog.Builder(EditAccountActivity.this).setTitle("Wybierz avatar").setView(viewDialog).create();
        alertDialog.show();

        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.imageView0:
                        imageURL = listOfURLs.get(0);
                        alertDialog.dismiss();
                        break;
                    case R.id.imageView1:
                        imageURL = listOfURLs.get(1);
                        alertDialog.dismiss();
                        break;
                    case R.id.imageView2:
                        imageURL = listOfURLs.get(2);
                        alertDialog.dismiss();
                        break;
                    case R.id.imageView3:
                        imageURL = listOfURLs.get(3);
                        alertDialog.dismiss();
                        break;
                    case R.id.imageView4:
                        imageURL = listOfURLs.get(4);
                        alertDialog.dismiss();
                        break;
                    case R.id.imageView5:
                        imageURL = listOfURLs.get(5);
                        alertDialog.dismiss();
                        break;
                    case R.id.imageView6:
                        imageURL = listOfURLs.get(6);
                        alertDialog.dismiss();
                        break;
                    case R.id.imageView7:
                        imageURL = listOfURLs.get(7);
                        alertDialog.dismiss();
                        break;
                    case R.id.imageView8:
                        imageURL = listOfURLs.get(8);
                        alertDialog.dismiss();
                        break;
                    default:
                        break;
                }
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

        //Dodane w celu mozliwosci ponownego otworzenia AlertDialoga
        viewDialog = null;
    }

    private void confirmChanges() {
        if (!newUsernameEditText.getText().toString().equals("")) {
            updateUserName();
        }
        if (!imageURL.equals("")){
            updateAvatar();
        }
    }

    private void updateUserName() {
        user.setUserName(newUsernameEditText.getText().toString());
        userReference.child("users").child(authenticatedUser.getUid()).setValue(user);
    }

    private void updateAvatar() {
        user.setAvatarURL(imageURL);
        userReference.child("users").child(authenticatedUser.getUid()).setValue(user);
    }
}
