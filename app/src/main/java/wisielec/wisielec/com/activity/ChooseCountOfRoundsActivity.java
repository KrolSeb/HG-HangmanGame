package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wisielec.wisielec.com.R;


public class ChooseCountOfRoundsActivity extends MainActivity{
    private static final String INTENT_KEY_CATEGORY_COUNT = "categoryCount";
    private static final int THREE_ROUNDS = 3;
    private static final int FIVE_ROUNDS = 5;
    private static final int SEVEN_ROUNDS = 7;

    @BindView(R.id.threeRoundsButton)
    protected Button threeRoundsButton;
    @BindView(R.id.fiveRoundsButton)
    protected Button fiveRoundsButton;
    @BindView(R.id.sevenRoundsButton)
    protected Button sevenRoundsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_count_of_rounds);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.threeRoundsButton)
    public void onThreeRoundsButtonClick(){
        Intent intentChooseCategories = new Intent(ChooseCountOfRoundsActivity.this, ChooseCategoriesActivity.class);
        intentChooseCategories.putExtra(INTENT_KEY_CATEGORY_COUNT, THREE_ROUNDS);
        startActivity(intentChooseCategories);
    }

    @OnClick(R.id.fiveRoundsButton)
    public void onFiveRoundsButtonClick(){
        Intent intentChooseCategories = new Intent(ChooseCountOfRoundsActivity.this, ChooseCategoriesActivity.class);
        intentChooseCategories.putExtra(INTENT_KEY_CATEGORY_COUNT, FIVE_ROUNDS);
        startActivity(intentChooseCategories);
    }

    @OnClick(R.id.sevenRoundsButton)
    public void onSevenRoundsButtonClick(){
        Intent intentChooseCategories = new Intent(ChooseCountOfRoundsActivity.this, ChooseCategoriesActivity.class);
        intentChooseCategories.putExtra(INTENT_KEY_CATEGORY_COUNT, SEVEN_ROUNDS);
        startActivity(intentChooseCategories);
    }

}
