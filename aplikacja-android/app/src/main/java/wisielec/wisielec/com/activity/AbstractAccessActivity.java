package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.interfaces.OnGetDataListener;
import wisielec.wisielec.com.repository.UserRepository;

/**
 * Created by sebastian on 13.04.18.
 */

public abstract class AbstractAccessActivity extends MainActivity {
    public UserRepository userRepository = new UserRepository();

    public boolean isFormValid(EditText emailInput) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.getText().toString()).matches() && !emailInput.getText().toString().isEmpty();
    }

    public void changeToAfterLoginActivity() {
        userRepository.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), new OnGetDataListener() {
            @Override
            public void onStart() {
                //Do nothing
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                Intent intent = new Intent(AbstractAccessActivity.this, AfterLoginActivity.class);
                intent.putExtra("user", data.getValue(User.class));
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
