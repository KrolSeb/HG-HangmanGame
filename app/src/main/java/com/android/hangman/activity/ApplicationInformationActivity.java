package com.android.hangman.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hangman.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ApplicationInformationActivity extends AppCompatActivity {
    private static final String MAIL_SENDER_TAG = "mailto:";
    private static final String MAIL_DEFAULT_TITLE = "Aplikacja Gra w wisielca";
    private static final String MAIL_INTENT_DIALOG_TITLE = "Wyślij wiadomość używając...";
    private static final String MAIL_INTENT_NO_EMAIL_APPLICATION = "Brak aplikacji do wysyłania e-maili.";

    @BindView(R.id.mainDeveloperTextView)
    protected TextView mainAuthorTextView;
    @BindView(R.id.firstDeveloperTextView)
    protected TextView firstDeveloperTextView;
    @BindView(R.id.secondDeveloperTextView)
    protected TextView secondDeveloperTextView;
    @BindView(R.id.thirdDeveloperTextView)
    protected TextView thirdDeveloperTextView;
    @BindView(R.id.projectDetailsTextView)
    protected TextView projectDetailsTextView;
    @BindView(R.id.contactEmailTextView)
    protected TextView contactEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_information);
        ButterKnife.bind(this);

        activateTextViewLinks();
    }

    private void activateTextViewLinks(){
        mainAuthorTextView.setMovementMethod(LinkMovementMethod.getInstance());
        firstDeveloperTextView.setMovementMethod(LinkMovementMethod.getInstance());
        secondDeveloperTextView.setMovementMethod(LinkMovementMethod.getInstance());
        thirdDeveloperTextView.setMovementMethod(LinkMovementMethod.getInstance());
        projectDetailsTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick(R.id.contactEmailTextView)
    protected void onContactEmailTextViewClick() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(MAIL_SENDER_TAG + getString(R.string.activity_application_information_mail_link)));
        intent.putExtra(Intent.EXTRA_SUBJECT, MAIL_DEFAULT_TITLE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, MAIL_INTENT_DIALOG_TITLE));
        }
        else {
            Toast.makeText(this, MAIL_INTENT_NO_EMAIL_APPLICATION, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intentHomeActivity = new Intent(ApplicationInformationActivity.this, HomeActivity.class);
        startActivity(intentHomeActivity);
        finish();
    }
}
