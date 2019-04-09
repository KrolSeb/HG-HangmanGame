package wisielec.wisielec.com.services;

import android.widget.EditText;
import wisielec.wisielec.com.interfaces.Validator;
import static android.util.Patterns.EMAIL_ADDRESS;


public class EmailValidatorService implements Validator {
    public EmailValidatorService() { }

    @Override
    public boolean validate(EditText emailInput) {
        return EMAIL_ADDRESS.matcher(emailInput.getText().toString()).matches() && !emailInput.getText().toString().isEmpty();
    }
}
