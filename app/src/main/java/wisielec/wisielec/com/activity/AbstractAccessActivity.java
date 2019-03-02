package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import wisielec.wisielec.com.interfaces.OnGetDataListener;
import wisielec.wisielec.com.services.UserService;

/**
 * Created by sebastian on 13.04.18.
 */

public abstract class AbstractAccessActivity extends MainActivity {
    public UserService userService = UserService.getInstance();

    public boolean isFormValid(EditText emailInput) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.getText().toString()).matches() && !emailInput.getText().toString().isEmpty();
    }

    public void changeToAfterLoginActivity() {
        userService.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), new OnGetDataListener() {
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
                Toast.makeText(AbstractAccessActivity.this, "Wystąpił błąd: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
