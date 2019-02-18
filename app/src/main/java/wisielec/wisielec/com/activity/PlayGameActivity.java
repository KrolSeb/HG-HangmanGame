package wisielec.wisielec.com.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
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
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.Category;
import wisielec.wisielec.com.domain.Subcategory;
import wisielec.wisielec.com.domain.Word;
import wisielec.wisielec.com.enums.Difficulty;

/**
 * Created by sebastian on 12.04.18.
 */

public class PlayGameActivity extends GameActivityAbstract implements Serializable {
    private final static int HANGMAN_ELEMENTS = 5;
    
    private final static String WIN_COLOR = "#2E8E3D";
    private final static String LOSE_COLOR = "#F44336";
    private final static String BUTTON_ENABLED_BACKGROUND_COLOR = "#0D2C4B";
    private final static String BUTTON_ENABLED_TEXT_COLOR = "#F4D170";
    private final static String BUTTON_DISABLED_BACKGROUND_COLOR = "#D1D1D1";
    private final static String BUTTON_DISABLED_TEXT_COLOR = "#EAEAEA";

    private final static int EASY_LEVEL_WORD_GUESS_POINTS = 5;
    private final static int MEDIUM_LEVEL_WORD_GUESS_POINTS = 10;
    private final static int HARD_LEVEL_WORD_GUESS_POINTS = 15;

    private final static int EASY_LEVEL_WORD_MISS_POINTS = 2;
    private final static int MEDIUM_LEVEL_WORD_MISS_POINTS = 4;
    private final static int HARD_LEVEL_WORD_MISS_POINTS = 6;

    private final static int THREE_ROUNDS_BONUS_POINTS = 16;
    private final static int FIVE_ROUNDS_BONUS_POINTS = 32;
    private final static int SEVEN_ROUNDS_BONUS_POINTS = 64;

    private final static int THREE_ROUNDS_BREAK_GAME_POINTS = 9;
    private final static int FIVE_ROUNDS_BREAK_GAME_POINTS = 15;
    private final static int SEVEN_ROUNDS_BREAK_GAME_POINTS = 21;

    protected int fullScore = 0;
    protected int missCounter = 0;
    protected int scoredPointsInRound = 0;

    protected ArrayList<Category> categoryList;
    protected ArrayList<Subcategory> subcategoryList;
    protected ArrayList<Word> wordsList;
    protected Category category;
    protected Subcategory subcategory;
    protected Word word;
    protected Difficulty difficulty;

    protected Random random = new Random();
    protected int randomSubcategoryNumber;
    protected int randomWordNumber;
    protected int categoryCount;
    protected int categoryNumber = 0;
    protected int difficultyLevel;

    protected String categoryName;
    protected String subcategoryName;
    protected String wordName;
    protected TextView [] letterTextViews;

    protected Button chosenButton;
    protected boolean isLetterExists;
    protected int guessLetterCounter = 0;
    protected int drawElementsCounter = 0;

    protected LayerDrawable underlinesDrawable;
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

    @BindView(R.id.mainLayout)
    ConstraintLayout mainLayout;

    @BindView(R.id.wordLettersLayout)
    LinearLayout wordLettersLayout;

    @BindView(R.id.categoryTextView)
    TextView categoryNameTextView;

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

        getDataFromIntent();
        initializeLists();

        hideHangmanImageViews();
        loadData(categoryNumber);
        showCategory();
        showLetterUnderlines();
        preparePopups();
    }

    private void loadData(int categoryNumber) {
        category = getCategory(categoryNumber);
        subcategory = getSubcategory(category);
        word = getWord(subcategory);
        setWordAttributes();
        setDifficultyLevel(difficultyLevel);

        System.out.println(categoryName);
        System.out.println(subcategoryName);
        System.out.println(difficultyLevel);
        System.out.println(wordName);

        clearLists();
    }

    private void setDifficultyLevel(int difficultyLevel) {
        switch (difficultyLevel){
            case 1:
                difficulty = Difficulty.EASY;
                break;
            case 2:
                difficulty = Difficulty.MEDIUM;
                break;
            case 3:
                difficulty = Difficulty.HARD;
                break;
            default:
                break;
        }
    }


    private void showLetterUnderlines() {
        letterTextViews = new TextView[wordName.length()];
        wordLettersLayout.removeAllViews();

        for (int i = 0; i < wordName.length(); i++) {
            letterTextViews[i] = new TextView(this);
            letterTextViews[i].setText(String.valueOf(wordName.charAt(i)));
            setLettersAppearance(i);
            drawUnderlines(i);
            wordLettersLayout.addView(letterTextViews[i]);
        }
    }

    @OnClick({R.id.buttonA, R.id.buttonB, R.id.buttonC, R.id.buttonD, R.id.buttonE, R.id.buttonF, R.id.buttonG, R.id.buttonH, R.id.buttonI,
            R.id.buttonJ, R.id.buttonK, R.id.buttonL, R.id.buttonM, R.id.buttonN, R.id.buttonO, R.id.buttonP, R.id.buttonQ, R.id.buttonR, R.id.buttonS,
            R.id.buttonT, R.id.buttonU, R.id.buttonV, R.id.buttonW, R.id.buttonX, R.id.buttonY, R.id.buttonZ, R.id.buttonPolishZ1, R.id.buttonPolishZ2,
            R.id.buttonPolishA, R.id.buttonPolishC, R.id.buttonPolishE, R.id.buttonPolishL, R.id.buttonPolishN, R.id.buttonPolishO, R.id.buttonPolishS})
    protected void onLetterClick(View view) {
        chosenButton = (Button) view;
        checkIfLetterExistsInWord(chosenButton.getText().toString());
        disableLetterButton();
    }

    private void checkIfLetterExistsInWord(String letter) {
        isLetterExists = false;

        for (TextView letterTextView : letterTextViews) {
            if (letterTextView.getText().toString().equals(letter)) {
                guessLetterCounter++;
                isLetterExists = true;
                letterTextView.setTextColor(Color.parseColor(difficulty.getColor()));
            }
        }

        processLetterCheckResult();
    }

    protected void processLetterCheckResult() {
        if (isLetterExists) {
            if (guessLetterCounter == wordName.replace(" ", "").length()) {
                guessLetterCounter = 0;
                drawElementsCounter = 0;
                sumPoints();
                showWinPopup(getScoredPointsInRound());

            }
        }
        else if (drawElementsCounter < HANGMAN_ELEMENTS) {
            drawHangman(drawElementsCounter);
            drawElementsCounter++;

            if (drawElementsCounter == HANGMAN_ELEMENTS) {
                drawElementsCounter = 0;
                guessLetterCounter = 0;
                substractPoints();
                showLosePopup(getScoredPointsInRound());
            }
        }
    }

    protected void sumPoints(){
        switch (difficulty){
            case EASY:
                fullScore += EASY_LEVEL_WORD_GUESS_POINTS;
                setScoredPointsInRound(EASY_LEVEL_WORD_GUESS_POINTS);
                break;
            case MEDIUM:
                fullScore += MEDIUM_LEVEL_WORD_GUESS_POINTS;
                setScoredPointsInRound(MEDIUM_LEVEL_WORD_GUESS_POINTS);
                break;
            case HARD:
                fullScore += HARD_LEVEL_WORD_GUESS_POINTS;
                setScoredPointsInRound(HARD_LEVEL_WORD_GUESS_POINTS);
                break;
            default:
                break;
        }
    }

    protected void substractPoints(){
        switch (difficulty){
            case EASY:
                fullScore -= EASY_LEVEL_WORD_MISS_POINTS;
                setScoredPointsInRound(EASY_LEVEL_WORD_MISS_POINTS);
                break;
            case MEDIUM:
                fullScore -= MEDIUM_LEVEL_WORD_MISS_POINTS;
                setScoredPointsInRound(MEDIUM_LEVEL_WORD_MISS_POINTS);
                break;
            case HARD:
                fullScore -= HARD_LEVEL_WORD_MISS_POINTS;
                setScoredPointsInRound(HARD_LEVEL_WORD_MISS_POINTS);
                break;
            default:
                break;
        }
    }

    private void preparePopups(){
        setDialogProperties();
        setDialogWindowProperties();
        initializeDialogComponents();
    }

    private void showWinPopup(int scoredPointsInRound) {
        setWinPopupAttributes(scoredPointsInRound);
        dialog.show();

        goFurtherButton.setOnClickListener(v -> {
            onGoFurtherButtonClick();
            dialog.dismiss();
        });
    }

    private void showLosePopup(int scoredPointsInRound) {
        setLosePopupAttributes(scoredPointsInRound);
        dialog.show();

        goFurtherButton.setOnClickListener(v -> {
            onGoFurtherButtonClick();
            dialog.dismiss();
        });
    }

    private void onGoFurtherButtonClick(){
        categoryNumber++;
        if (categoryNumber < categoryCount) {
            loadNextRound();
        }
        else if (categoryNumber == categoryCount){
            goToSummaryActivity();
        }
    }

    private void loadNextRound(){
        setCategoryNumber(categoryNumber);
        enableLetterButtons();
        hideHangmanImageViews();
        loadData(categoryNumber);
        showCategory();
        showLetterUnderlines();
    }

    private void goToSummaryActivity(){
        PlayGameActivity.this.finish();
        Intent intentSummary = new Intent(getApplicationContext(), SummaryActivity.class);
        startActivity(intentSummary);
    }

    private void getDataFromIntent(){
        Intent intent = getIntent();
        categoryCount = (int) intent.getSerializableExtra("categoryCount");
        categoryList = (ArrayList<Category>) intent.getSerializableExtra("categoryList");
    }

    private void initializeLists(){
        subcategoryList = new ArrayList<>();
        wordsList = new ArrayList<>();
    }

    protected Category getCategory(int categoryNumber){
        return categoryList.get(categoryNumber);
    }

    protected Subcategory getSubcategory(Category category){
        subcategoryList.addAll(category.getSubcategories());
        randomSubcategoryNumber = random.nextInt(subcategoryList.size());
        return subcategoryList.get(randomSubcategoryNumber);
    }

    protected Word getWord(Subcategory subcategory){
        wordsList.addAll(subcategory.getWords());
        randomWordNumber = random.nextInt(wordsList.size());
        return wordsList.get(randomWordNumber);
    }

    protected void setWordAttributes(){
        categoryName = category.getCategoryName();
        subcategoryName = subcategory.getSubcategoryName();
        difficultyLevel = word.getDifficultyLevel();
        wordName = word.getName();
    }

    private void clearLists(){
        subcategoryList.clear();
        wordsList.clear();
    }

    private void hideHangmanImageViews() {
        for (ImageView hangmanImageView : hangmanImageViews) {
            hangmanImageView.setVisibility(View.GONE);
        }
    }

    private void enableLetterButtons(){
        for (Button button : letterButtons) {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor((BUTTON_ENABLED_BACKGROUND_COLOR))));
            button.setTextColor(Color.parseColor(BUTTON_ENABLED_TEXT_COLOR));
            button.setEnabled(true);
        }
    }

    private void disableLetterButton(){
        chosenButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor((BUTTON_DISABLED_BACKGROUND_COLOR))));
        chosenButton.setTextColor(Color.parseColor(BUTTON_DISABLED_TEXT_COLOR));
        chosenButton.setEnabled(false);
    }

    private void drawHangman(int drawElementsCounter) {
        hangmanImageViews[drawElementsCounter].setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.buttonEndGame)
    protected void onEndGameButtonClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Czy chcesz przerwać rozgrywkę? Dotychczasowy postęp zostanie utracony. Z Twojego konta zostanie odebranych " +  "punktów.")
                .setCancelable(false)
                .setPositiveButton("Tak", (dialog, id) -> {
                    PlayGameActivity.this.finish();
                    Intent intentSummary = new Intent(getApplicationContext(), SummaryActivity.class);
                    startActivity(intentSummary);
                })
                .setNegativeButton("Nie", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void setLettersAppearance(int number){
        letterTextViews[number].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        letterTextViews[number].setGravity(Gravity.CENTER);
        letterTextViews[number].setTextColor(Color.TRANSPARENT);
        letterTextViews[number].setTextSize(20);

        if (letterTextViews[number].getText().equals(" ")) {
            letterTextViews[number].setLetterSpacing((float) 0.5);
        }
    }

    private void setUnderlinesStyle(){
        underlinesDrawable = (LayerDrawable) ContextCompat.getDrawable(this,R.drawable.letter_underline);
        underlinesDrawable.setTint(Color.parseColor(difficulty.getColor()));
    }

    private void drawUnderlines(int number){
        if (!letterTextViews[number].getText().equals(" ")) {
            setUnderlinesStyle();
            letterTextViews[number].setBackground(underlinesDrawable);
        }
    }

    private void showCategory() {
        categoryNameTextView.setText("Kategoria: " + categoryName);
    }

    private void initializeDialogComponents(){
        statusTextView = dialogView.findViewById(R.id.statusTextView);
        statusIcon = dialogView.findViewById(R.id.statusIcon);
        pointsTextView = dialogView.findViewById(R.id.pointsTextView);
        pointsAmountTextView = dialogView.findViewById(R.id.pointsAmountTextView);
        goFurtherButton = dialogView.findViewById(R.id.goFurtherButton);
    }

    private void setDialogProperties(){
        dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        inflater = this.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.game_popup, null);
        dialog.setContentView(dialogView);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    private void setDialogWindowProperties(){
        window = dialog.getWindow();
        windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
    }

    private void setWinPopupAttributes(int points){
        statusTextView.setTextColor(Color.parseColor(WIN_COLOR));
        statusTextView.setText("Wygrana!");
        statusIcon.setImageResource(R.drawable.icon_win);
        pointsTextView.setText("Ilość zdobytych punktów");
        pointsAmountTextView.setText(String.valueOf(points));
    }

    private void setLosePopupAttributes(int points){
        statusTextView.setTextColor(Color.parseColor(LOSE_COLOR));
        statusTextView.setText("Porażka :(");
        statusIcon.setImageResource(R.drawable.icon_lose);
        pointsTextView.setText("Ilość straconych punktów");
        pointsAmountTextView.setText(String.valueOf(points));
    }

    private void setCategoryNumber(int categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public void setScoredPointsInRound(int scoredPointsInRound) {
        this.scoredPointsInRound = scoredPointsInRound;
    }
    
    public int getScoredPointsInRound() {
        return scoredPointsInRound;
    }

    @Override
    public void onBackPressed() { }
}