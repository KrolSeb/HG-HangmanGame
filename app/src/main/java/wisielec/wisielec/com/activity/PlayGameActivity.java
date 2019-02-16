package wisielec.wisielec.com.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.Category;
import wisielec.wisielec.com.domain.Subcategory;
import wisielec.wisielec.com.domain.Word;

/**
 * Created by sebastian on 12.04.18.
 */

public class PlayGameActivity extends GameActivityAbstract implements Serializable {
    private final static int HANGMAN_ELEMENTS = 5;

    protected Random random = new Random();
    protected int randomSubcategoryNumber;
    protected int randomWordNumber;
    protected int categoryCount;
    protected int categoryNumber = 0;

    protected int difficultyLevel;
    protected String categoryName;
    protected String subcategoryName;
    protected String wordName;

    protected ArrayList<Category> categoryList;
    protected ArrayList<Subcategory> subcategoryList;
    protected ArrayList<Word> wordsList;

    protected LinearLayout wordLettersLayout;
    protected ConstraintLayout mainLayout;
    protected TextView categoryNameTextView;
    protected TextView [] letterTextViews;

    protected Button chosenButton;
    protected String letter;
    protected int guessLetterCounter = 0;
    protected int drawElementsCounter = 0;
    protected boolean isLetterExists;

    protected Dialog dialog;
    protected Window window;
    protected WindowManager.LayoutParams windowAttributes;
    protected LayoutInflater inflater;
    protected View dialogView;
    protected TextView statusTextView;
    protected ImageView statusIcon;
    protected TextView pointsTextView;
    protected TextView pointsAmountTextView;
    protected Button goFurtherButton;

    @BindViews({R.id.hangmanOne, R.id.hangmanTwo, R.id.hangmanThree, R.id.hangmanFour, R.id.hangmanFive})
    ImageView[] hangmanImageViews;

    @BindViews({R.id.buttonA, R.id.buttonB, R.id.buttonC, R.id.buttonD, R.id.buttonE, R.id.buttonF, R.id.buttonG, R.id.buttonH, R.id.buttonI,
            R.id.buttonJ, R.id.buttonK, R.id.buttonL, R.id.buttonM, R.id.buttonN, R.id.buttonO, R.id.buttonP, R.id.buttonQ, R.id.buttonR, R.id.buttonS,
            R.id.buttonT, R.id.buttonU, R.id.buttonV, R.id.buttonW, R.id.buttonX, R.id.buttonY, R.id.buttonZ, R.id.buttonPolishZ1, R.id.buttonPolishZ2,
            R.id.buttonPolishA, R.id.buttonPolishC, R.id.buttonPolishE, R.id.buttonPolishL, R.id.buttonPolishN, R.id.buttonPolishO, R.id.buttonPolishS})
    List<Button> letterButtons;

    @BindView(R.id.buttonEndGame)
    Button endGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        ButterKnife.bind(this);

        categoryNameTextView = findViewById(R.id.categoryTextView);
        mainLayout = findViewById(R.id.mainLayout);
        wordLettersLayout = findViewById(R.id.wordLettersLayout);

        Intent intent = getIntent();
        categoryCount = (int) intent.getSerializableExtra("categoryCount");
        categoryList = (ArrayList<Category>) intent.getSerializableExtra("categoryList");
        subcategoryList = new ArrayList<>();
        wordsList = new ArrayList<>();

        hideHangmanImageViews();
        loadData(categoryNumber);
        showCategory();
        showLetterUnderlines();
        preparePopups();
    }

    private void onGoFurtherButtonClick(){
        categoryNumber++;
        //Do metody przeładowującej hasło po zakończeniu partii gry
        if (categoryNumber < categoryCount) {
            setCategoryNumber(categoryNumber);
            enableLetterButtons();
            hideHangmanImageViews();
            loadData(categoryNumber);
            showCategory();
            showLetterUnderlines();
        }
        else if (categoryNumber == categoryCount){
            PlayGameActivity.this.finish();
            Intent intentSummary = new Intent(getApplicationContext(), SummaryActivity.class);
            startActivity(intentSummary);
        }
    }

    private void loadData(int categoryNumber) {
        Category category;
        Subcategory subcategory;
        Word word;

        //Pobranie jednej kategorii z listy
        category = categoryList.get(categoryNumber);

        //Pobranie podkategorii dla kategorii i wylosowanie jednej
        subcategoryList.addAll(category.getSubcategories());
        randomSubcategoryNumber = random.nextInt(subcategoryList.size());
        subcategory = subcategoryList.get(randomSubcategoryNumber);

        //Pobranie słów dla podkategorii i wylosowanie tylko jednego słowa
        wordsList.addAll(subcategory.getWords());
        randomWordNumber = random.nextInt(wordsList.size());
        word = wordsList.get(randomWordNumber);

        //Przekazanie wylosowanej kategorii, podkategorii, poziomu trudności hasła i samego hasła do zmiennych
        categoryName = category.getCategoryName();
        subcategoryName = subcategory.getSubcategoryName();
        difficultyLevel = word.getDifficultyLevel();
        wordName = word.getName();

        System.out.println(categoryNumber);
        System.out.println(categoryName);
        System.out.println(subcategoryName);
        System.out.println(difficultyLevel);
        System.out.println(wordName);

        //Czyszczenie używanych list, aby uniknąć losowania błędnych haseł dla wylosowanej kategorii
        subcategoryList.clear();
        wordsList.clear();

        //Zwiększenie numeru kategorii, aby można było wylosować następną

    }


    @OnClick(R.id.buttonEndGame)
    protected void onEndGameButtonClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PlayGameActivity.this.finish();
                        Intent intentSummary = new Intent(getApplicationContext(), SummaryActivity.class);
                        startActivity(intentSummary);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void preparePopups(){
        dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        inflater = this.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.game_popup, null);

        statusTextView = dialogView.findViewById(R.id.statusTextView);
        statusIcon = dialogView.findViewById(R.id.statusIcon);
        pointsTextView = dialogView.findViewById(R.id.pointsTextView);
        pointsAmountTextView = dialogView.findViewById(R.id.pointsAmountTextView);
        goFurtherButton = dialogView.findViewById(R.id.goFurtherButton);

        dialog.setContentView(dialogView);
        window = dialog.getWindow();
        windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    private void showWinPopup() {
        statusTextView.setTextColor(Color.parseColor("#3AB54E"));
        statusTextView.setText("Wygrana!");
        statusIcon.setImageResource(R.drawable.icon_win);
        pointsTextView.setText("Ilość zdobytych punktów");
        pointsAmountTextView.setText("10");
        dialog.show();

        goFurtherButton.setOnClickListener(v -> {
            onGoFurtherButtonClick();
            dialog.dismiss();
        });
    }

    private void showLosePopup() {
        statusTextView.setTextColor(Color.parseColor("#F44336"));
        statusTextView.setText("Porażka :(");
        statusIcon.setImageResource(R.drawable.icon_lose);
        pointsTextView.setText("Ilość straconych punktów");
        pointsAmountTextView.setText("10");
        dialog.show();

        goFurtherButton.setOnClickListener(v -> {
            onGoFurtherButtonClick();
            dialog.dismiss();
        });
    }


    private void hideHangmanImageViews() {
        for (ImageView hangmanImageView : hangmanImageViews) {
            hangmanImageView.setVisibility(View.GONE);
        }
    }

    private void enableLetterButtons(){
        for (Button button : letterButtons) {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(("#0D2C4B"))));
            button.setTextColor(Color.parseColor("#F4D170"));
            button.setEnabled(true);
        }
    }


    private void checkIfLetterExistsInWord(String letter) {
        isLetterExists = false;

        for (TextView letterTextView : letterTextViews) {
            if (letterTextView.getText().toString().equals(letter)) {
                guessLetterCounter++;
                System.out.println(guessLetterCounter);
                isLetterExists = true;
                letterTextView.setTextColor(Color.parseColor("#F4D170"));
            }
        }

        if (isLetterExists) {
            if (guessLetterCounter == wordName.replace(" ", "").length()) {
                guessLetterCounter = 0;
                drawElementsCounter = 0;
                showWinPopup();
                //Przeładuj aktywność - potrzebny będzie licznik - porównujący ilość rund odbytych i maksymalnych
            }
        }
        else if (drawElementsCounter < HANGMAN_ELEMENTS) {
            drawHangman(drawElementsCounter);
            drawElementsCounter++;

            if (drawElementsCounter == HANGMAN_ELEMENTS) {
                drawElementsCounter = 0;
                guessLetterCounter = 0;
                showLosePopup();
                //Przeładuj aktywność - potrzebny będzie licznik - porównujący ilość rund odbytych i maksymalnych
            }
        }
    }

    private void drawHangman(int drawElementsCounter) {
        hangmanImageViews[drawElementsCounter].setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.buttonA, R.id.buttonB, R.id.buttonC, R.id.buttonD, R.id.buttonE, R.id.buttonF, R.id.buttonG, R.id.buttonH, R.id.buttonI,
            R.id.buttonJ, R.id.buttonK, R.id.buttonL, R.id.buttonM, R.id.buttonN, R.id.buttonO, R.id.buttonP, R.id.buttonQ, R.id.buttonR, R.id.buttonS,
            R.id.buttonT, R.id.buttonU, R.id.buttonV, R.id.buttonW, R.id.buttonX, R.id.buttonY, R.id.buttonZ, R.id.buttonPolishZ1, R.id.buttonPolishZ2,
            R.id.buttonPolishA, R.id.buttonPolishC, R.id.buttonPolishE, R.id.buttonPolishL, R.id.buttonPolishN, R.id.buttonPolishO, R.id.buttonPolishS})
    protected void onLetterClick(View view) {
        chosenButton = (Button) view;
        letter = chosenButton.getText().toString();
        checkIfLetterExistsInWord(letter);

        chosenButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(("#D1D1D1"))));
        chosenButton.setTextColor(Color.parseColor("#EAEAEA"));
        chosenButton.setEnabled(false);
    }

    private void showLetterUnderlines() {
        letterTextViews = new TextView[wordName.length()];
        wordLettersLayout.removeAllViews();

        for (int i = 0; i < wordName.length(); i++) {
            letterTextViews[i] = new TextView(this);
            letterTextViews[i].setText("" + wordName.charAt(i));
            letterTextViews[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            letterTextViews[i].setGravity(Gravity.CENTER);
            letterTextViews[i].setTextColor(Color.TRANSPARENT);
            letterTextViews[i].setTextSize(20);

            if (!letterTextViews[i].getText().equals(" ")) {
                letterTextViews[i].setBackgroundResource(R.drawable.letter_underline);
            }

            if (letterTextViews[i].getText().equals(" ")) {
                letterTextViews[i].setLetterSpacing((float) 0.5);
            }

            wordLettersLayout.addView(letterTextViews[i]);
        }
    }

    private void showCategory() {
        categoryNameTextView.setText("Kategoria: " + categoryName);
    }

    private void setCategoryNumber(int categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    @Override
    public void onBackPressed() { }
}