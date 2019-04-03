package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import wisielec.wisielec.com.interfaces.GetUserDataCallback;
import wisielec.wisielec.com.services.UserService;


public abstract class AbstractAccessActivity extends MainActivity {
    private static final String MESSAGE_AN_ERROR_OCCURED = "Wystąpił błąd: ";
    private UserService userService = UserService.getInstance();

    public void changeToAfterLoginActivity() {
        userService.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), new GetUserDataCallback() {
            @Override
            public void onStart() { }

            @Override
            public void onSuccess(DataSnapshot data) {
                Intent intent = new Intent(AbstractAccessActivity.this, AfterLoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(AbstractAccessActivity.this, MESSAGE_AN_ERROR_OCCURED + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
