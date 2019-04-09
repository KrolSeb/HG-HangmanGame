package wisielec.wisielec.com.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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


public class PlayGameActivity extends MainActivity {
    private static final int HANGMAN_ELEMENTS = 5;
    private static final int OPEN_POPUP_DELAY = 600;
    private static final int CLOSE_POPUP_DELAY = 400;

    private static final String SPACE = " ";
    private static final String EMPTY_STRING = "";
    private static final String INTENT_KEY_CATEGORY_COUNT = "categoryCount";
    private static final String INTENT_KEY_CATEGORY_LIST = "categoryList";
    private static final String INTENT_KEY_SCORE = "score";
    private static final String INTENT_KEY_BONUS_POINTS = "bonusPoints";

    private static final String CATEGORY = "Kategoria:";
    private static final String DIALOG_MESSAGE_WIN = "Wygrana!";
    private static final String DIALOG_MESSAGE_LOSE = "Porażka :(";
    private static final String DIALOG_MESSAGE_BREAK_GAME = "Czy chcesz przerwać rozgrywkę? Dotychczasowy postęp zostanie utracony. Z Twojego konta zostanie odebranych";
    private static final String DIALOG_MESSAGE_BREAK_GAME_POINTS = "punktów.";
    private static final String DIALOG_POSITIVE_ANSWER = "Tak";
    private static final String DIALOG_NEGATIVE_ANSWER = "Nie";

    private static final String WIN_COLOR = "#2E8E3D";
    private static final String LOSE_COLOR = "#E51616";
    private static final String BUTTON_ENABLED_BACKGROUND_COLOR = "#0D2C4B";
    private static final String BUTTON_ENABLED_TEXT_COLOR = "#F4D170";
    private static final String BUTTON_DISABLED_BACKGROUND_COLOR = "#D1D1D1";
    private static final String BUTTON_DISABLED_TEXT_COLOR = "#EAEAEA";

    private static final int EASY_LEVEL_WORD_GUESS_POINTS = 5;
    private static final int MEDIUM_LEVEL_WORD_GUESS_POINTS = 10;
    private static final int HARD_LEVEL_WORD_GUESS_POINTS = 15;

    private static final int EASY_LEVEL_WORD_MISS_POINTS = 2;
    private static final int MEDIUM_LEVEL_WORD_MISS_POINTS = 4;
    private static final int HARD_LEVEL_WORD_MISS_POINTS = 6;

    private static final int THREE_ROUNDS_BONUS_POINTS = 16;
    private static final int FIVE_ROUNDS_BONUS_POINTS = 32;
    private static final int SEVEN_ROUNDS_BONUS_POINTS = 48;

    private static final int THREE_ROUNDS_BREAK_GAME_POINTS = -18;
    private static final int FIVE_ROUNDS_BREAK_GAME_POINTS = -30;
    private static final int SEVEN_ROUNDS_BREAK_GAME_POINTS = -42;

    private int missCounter = 0;
    private int score = 0;
    private int bonusPoints = 0;

    private ArrayList<Category> categoryList;
    private ArrayList<Subcategory> subcategoryList;
    private ArrayList<Word> wordsList;
    private Category category;
    private Word word;
    private Difficulty difficulty;

    private Handler handler;
    private Random random;
    private int categoryCount;
    private int categoryNumber = 0;
    private int difficultyLevel;

    private String categoryName;
    private String wordName;
    private TextView[] letterTextViews;

    private Button chosenButton;
    private boolean isLetterExists;
    private int guessLetterCounter = 0;
    private int drawElementsCounter = 0;

    private LayerDrawable underlinesDrawable;
    private Dialog dialog;
    private View dialogView;

    private TextView statusTextView;
    private ImageView statusIcon;
    private Button goFurtherButton;

    @BindView(R.id.gameConstraintLayout)
    protected ConstraintLayout mainLayout;

    @BindView(R.id.wordLettersLayout)
    protected LinearLayout wordLettersLayout;

    @BindView(R.id.categoryTextView)
    protected TextView categoryNameTextView;

    @BindViews({R.id.hangmanOne, R.id.hangmanTwo, R.id.hangmanThree, R.id.hangmanFour, R.id.hangmanFive})
    protected ImageView[] hangmanImageViews;

    @BindViews({R.id.buttonA, R.id.buttonB, R.id.buttonC, R.id.buttonD, R.id.buttonE, R.id.buttonF, R.id.buttonG, R.id.buttonH, R.id.buttonI,
            R.id.buttonJ, R.id.buttonK, R.id.buttonL, R.id.buttonM, R.id.buttonN, R.id.buttonO, R.id.buttonP, R.id.buttonQ, R.id.buttonR, R.id.buttonS,
            R.id.buttonT, R.id.buttonU, R.id.buttonV, R.id.buttonW, R.id.buttonX, R.id.buttonY, R.id.buttonZ, R.id.buttonPolishZ1, R.id.buttonPolishZ2,
            R.id.buttonPolishA, R.id.buttonPolishC, R.id.buttonPolishE, R.id.buttonPolishL, R.id.buttonPolishN, R.id.buttonPolishO, R.id.buttonPolishS})
    protected List<Button> letterButtons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        ButterKnife.bind(this);
        random = new Random();
        handler = new Handler();

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
        Subcategory subcategory = getSubcategory(category);
        word = getWord(subcategory);
        setWordAttributes();
        setDifficultyLevel(difficultyLevel);
        clearLists();
    }

    private void setDifficultyLevel(int difficultyLevel) {
        switch (difficultyLevel) {
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
    protected void onLetterButtonClick(View view) {
        chosenButton = (Button) view;
        checkIfLetterExistsInWord(chosenButton.getText().toString());
        disableOneLetterButton();
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

    private void processLetterCheckResult() {
        if (isLetterExists) {
            if (guessLetterCounter == wordName.replace(SPACE, EMPTY_STRING).length()) {
                guessLetterCounter = 0;
                drawElementsCounter = 0;
                disableLetterButtons();
                sumPoints();
                delayAndShowWinPopup();
            }
        }
        else if (drawElementsCounter < HANGMAN_ELEMENTS) {
            drawHangman(drawElementsCounter);
            drawElementsCounter++;
            if (drawElementsCounter == HANGMAN_ELEMENTS) {
                drawElementsCounter = 0;
                guessLetterCounter = 0;
                missCounter++;
                disableLetterButtons();
                substractPoints();
                delayAndShowLosePopup();
            }
        }
    }

    private void delayAndShowWinPopup() {
        handler.postDelayed(this::showWinPopup, OPEN_POPUP_DELAY);
    }

    private void delayAndShowLosePopup() {
        handler.postDelayed(this::showLosePopup, OPEN_POPUP_DELAY);
    }

    private void delayAndClosePopup() {
        handler.postDelayed(() -> dialog.dismiss(), CLOSE_POPUP_DELAY);
    }

    private void sumPoints() {
        switch (difficulty) {
            case EASY:
                score += EASY_LEVEL_WORD_GUESS_POINTS;
                break;
            case MEDIUM:
                score += MEDIUM_LEVEL_WORD_GUESS_POINTS;
                break;
            case HARD:
                score += HARD_LEVEL_WORD_GUESS_POINTS;
                break;
            default:
                break;
        }
    }

    private void substractPoints() {
        switch (difficulty) {
            case EASY:
                score -= EASY_LEVEL_WORD_MISS_POINTS;
                break;
            case MEDIUM:
                score -= MEDIUM_LEVEL_WORD_MISS_POINTS;
                break;
            case HARD:
                score -= HARD_LEVEL_WORD_MISS_POINTS;
                break;
            default:
                break;
        }
    }

    private void preparePopups() {
        setDialogProperties();
        setDialogWindowProperties();
        initializeDialogComponents();
    }

    private void removePreviousRoundContent() {
        removeWordLettersFromView();
        removeCategoryFromView();
        hideHangmanImageViews();
        enableLetterButtons();
    }

    private void removeWordLettersFromView() {
        wordLettersLayout.removeAllViews();
    }

    private void removeCategoryFromView() {
        categoryNameTextView.setText(EMPTY_STRING);
    }

    private void showWinPopup() {
        setWinPopupAttributes();
        showDialog();
        goFurtherButton.setOnClickListener(v -> onGoFurtherButtonClick());
    }

    private void showLosePopup() {
        setLosePopupAttributes();
        showDialog();
        goFurtherButton.setOnClickListener(v -> onGoFurtherButtonClick());
    }

    private void showDialog() {
        dialog.show();
    }

    private void onGoFurtherButtonClick() {
        categoryNumber++;
        if (categoryNumber < categoryCount) {
            removePreviousRoundContent();
            loadNextRound();
            delayAndClosePopup();
        }
        else if (categoryNumber == categoryCount) {
            calculateExtraPoints();
            goToSummaryActivity();
        }
    }

    private void calculateExtraPoints() {
        if (missCounter == 0) {
            switch (categoryCount) {
                case 3:
                    bonusPoints = THREE_ROUNDS_BONUS_POINTS;
                    break;
                case 5:
                    bonusPoints = FIVE_ROUNDS_BONUS_POINTS;
                    break;
                case 7:
                    bonusPoints = SEVEN_ROUNDS_BONUS_POINTS;
                    break;
                default:
                    break;
            }
        }
    }

    private void calculateBreakGamePoints() {
        switch (categoryCount) {
            case 3:
                score = THREE_ROUNDS_BREAK_GAME_POINTS;
                break;
            case 5:
                score = FIVE_ROUNDS_BREAK_GAME_POINTS;
                break;
            case 7:
                score = SEVEN_ROUNDS_BREAK_GAME_POINTS;
                break;
            default:
                break;
        }
    }

    private void goToSummaryActivity() {
        Intent intentSummary = new Intent(PlayGameActivity.this, SummaryActivity.class);
        intentSummary.putExtra(INTENT_KEY_SCORE, score);
        intentSummary.putExtra(INTENT_KEY_BONUS_POINTS, bonusPoints);
        startActivity(intentSummary);
        finish();
    }

    private void loadNextRound() {
        setCategoryNumber(categoryNumber);
        loadData(categoryNumber);
        showLetterUnderlines();
        showCategory();
    }

    @SuppressWarnings("unchecked")
    private void getDataFromIntent() {
        Intent intent = getIntent();
        categoryCount = (int) intent.getSerializableExtra(INTENT_KEY_CATEGORY_COUNT);
        categoryList = (ArrayList<Category>) intent.getSerializableExtra(INTENT_KEY_CATEGORY_LIST);
    }

    private void initializeLists() {
        subcategoryList = new ArrayList<>();
        wordsList = new ArrayList<>();
    }

    private Category getCategory(int categoryNumber) {
        return categoryList.get(categoryNumber);
    }

    private Subcategory getSubcategory(Category category) {
        subcategoryList.addAll(category.getSubcategories());
        int randomSubcategoryNumber = random.nextInt(subcategoryList.size());
        return subcategoryList.get(randomSubcategoryNumber);
    }

    private Word getWord(Subcategory subcategory) {
        wordsList.addAll(subcategory.getWords());
        int randomWordNumber = random.nextInt(wordsList.size());
        return wordsList.get(randomWordNumber);
    }

    private void setWordAttributes() {
        categoryName = category.getCategoryName();
        //String subcategoryName = subcategory.getSubcategoryName();
        difficultyLevel = word.getDifficultyLevel();
        wordName = word.getName();
    }

    private void clearLists() {
        subcategoryList.clear();
        wordsList.clear();
    }

    private void hideHangmanImageViews() {
        for (ImageView hangmanImageView : hangmanImageViews) {
            hangmanImageView.setVisibility(View.GONE);
        }
    }

    private void enableLetterButtons() {
        for (Button button : letterButtons) {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor((BUTTON_ENABLED_BACKGROUND_COLOR))));
            button.setTextColor(Color.parseColor(BUTTON_ENABLED_TEXT_COLOR));
            button.setEnabled(true);
        }
    }

    private void disableLetterButtons() {
        for (Button button : letterButtons) {
            button.setEnabled(false);
        }
    }

    private void disableOneLetterButton() {
        chosenButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor((BUTTON_DISABLED_BACKGROUND_COLOR))));
        chosenButton.setTextColor(Color.parseColor(BUTTON_DISABLED_TEXT_COLOR));
        chosenButton.setEnabled(false);
    }

    private void drawHangman(int drawElementsCounter) {
        hangmanImageViews[drawElementsCounter].setVisibility(View.VISIBLE);
    }

    private void drawUnderlines(int number) {
        if (!letterTextViews[number].getText().equals(SPACE)) {
            setUnderlinesStyle();
            letterTextViews[number].setBackground(underlinesDrawable);
        }
    }

    private void setLettersAppearance(int number) {
        letterTextViews[number].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        letterTextViews[number].setGravity(Gravity.CENTER);
        letterTextViews[number].setTextColor(Color.TRANSPARENT);
        letterTextViews[number].setTextSize(22);

        if (letterTextViews[number].getText().equals(SPACE)) {
            letterTextViews[number].setLetterSpacing((float) 0.5);
        }
    }

    private void setUnderlinesStyle() {
        underlinesDrawable = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.style_letter_underline);
        underlinesDrawable.setTint(Color.parseColor(difficulty.getColor()));
    }

    private void showCategory() {
        String fullCategory = CATEGORY + SPACE + categoryName;
        categoryNameTextView.setText(fullCategory);
    }

    private void initializeDialogComponents() {
        statusTextView = dialogView.findViewById(R.id.statusTextView);
        statusIcon = dialogView.findViewById(R.id.statusIcon);
        goFurtherButton = dialogView.findViewById(R.id.goFurtherButton);
    }

    @SuppressLint("InflateParams")
    private void setDialogProperties() {
        dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = this.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_game_popup, null);
        dialog.setContentView(dialogView);

        Objects.requireNonNull(dialog.getWindow()).setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    private void setDialogWindowProperties() {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowAttributes = Objects.requireNonNull(window).getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
    }

    private void setWinPopupAttributes() {
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.parseColor(WIN_COLOR)));
        statusTextView.setText(DIALOG_MESSAGE_WIN);
        statusIcon.setImageResource(R.drawable.icon_win);
        goFurtherButton.setTextColor(Color.parseColor(WIN_COLOR));
    }

    private void setLosePopupAttributes() {
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.parseColor(LOSE_COLOR)));
        statusTextView.setText(DIALOG_MESSAGE_LOSE);
        statusIcon.setImageResource(R.drawable.icon_lose);
        goFurtherButton.setTextColor(Color.parseColor(LOSE_COLOR));
    }

    private void setCategoryNumber(int categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(DIALOG_MESSAGE_BREAK_GAME + SPACE + getBrakeGamePoints() + SPACE + DIALOG_MESSAGE_BREAK_GAME_POINTS)
                .setCancelable(false)
                .setPositiveButton(DIALOG_POSITIVE_ANSWER, (dialog, id) -> {
                    calculateBreakGamePoints();
                    Intent intentSummary = new Intent(PlayGameActivity.this, SummaryActivity.class);
                    intentSummary.putExtra(INTENT_KEY_SCORE, score);
                    intentSummary.putExtra(INTENT_KEY_BONUS_POINTS, bonusPoints);
                    finish();
                    startActivity(intentSummary);
                })
                .setNegativeButton(DIALOG_NEGATIVE_ANSWER, (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private int getBrakeGamePoints() {
        int lostPoints = 0;
        switch (categoryCount) {
            case 3:
                lostPoints = THREE_ROUNDS_BREAK_GAME_POINTS;
                break;
            case 5:
                lostPoints = FIVE_ROUNDS_BREAK_GAME_POINTS;
                break;
            case 7:
                lostPoints = SEVEN_ROUNDS_BREAK_GAME_POINTS;
                break;
            default:
                break;
        }
        return -lostPoints;
    }

}