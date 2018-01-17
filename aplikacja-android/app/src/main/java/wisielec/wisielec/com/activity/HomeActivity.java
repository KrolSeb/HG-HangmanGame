package wisielec.wisielec.com.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import wisielec.wisielec.com.R;

/**
 * Created by X on 2018-01-17.
 */

public class HomeActivity extends MainActivity {

    Button buttonLogin;
    Button buttonRegistration;
    Button buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRegistration = (Button) findViewById(R.id.buttonRegistration);
        buttonExit = (Button) findViewById(R.id.buttonExit);
        OnClickListeners();
    }

    protected void OnClickListeners() {
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

}
