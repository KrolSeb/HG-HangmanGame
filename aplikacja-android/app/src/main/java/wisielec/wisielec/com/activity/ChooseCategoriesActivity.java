package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import wisielec.wisielec.com.R;

/**
 * Created by sebastian on 12.04.18.
 */

public class ChooseCategoriesActivity extends GameActivityAbstract {

    protected Button firstCategoryButton;
    protected Button secondCategoryButton;
    protected Button thirdCategoryButton;
    protected Button fourthCategoryButton;
    protected Button fifthCategoryButton;
    protected Button changeCategoryButton;
    protected Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_categories);
        firstCategoryButton = findViewById(R.id.firstCategoryButton);
        secondCategoryButton = findViewById(R.id.secondCategoryButton);
        thirdCategoryButton = findViewById(R.id.thirdCategoryButton);
        fourthCategoryButton = findViewById(R.id.fourthCategoryButton);
        fifthCategoryButton = findViewById(R.id.fifthCategoryButton);
        changeCategoryButton = findViewById(R.id.changeCategoryButton);
        startGameButton = findViewById(R.id.startGameButton);
        onClickListeners();
    }

    private void onClickListeners() {
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.startGameButton:
                        Intent intentPlayGame = new Intent(getApplicationContext(), PlayGameActivity.class);
                        startActivity(intentPlayGame);
                        break;
                    default:
                        break;
                }
            }
        };

        firstCategoryButton.setOnClickListener(listener);
        secondCategoryButton.setOnClickListener(listener);
        thirdCategoryButton.setOnClickListener(listener);
        fourthCategoryButton.setOnClickListener(listener);
        fifthCategoryButton.setOnClickListener(listener);
        changeCategoryButton.setOnClickListener(listener);
        startGameButton.setOnClickListener(listener);
    }

}
