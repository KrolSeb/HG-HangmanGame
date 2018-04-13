package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import wisielec.wisielec.com.R;

/**
 * Created by sebastian on 12.04.18.
 */

public class ChooseCountOfRoundsActivity extends GameActivityAbstract{
    protected Button threeRoundsButton;
    protected Button fiveRoundsButton;
    protected Button sevenRoundsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_count_of_rounds);

        threeRoundsButton = findViewById(R.id.threeRoundsButton);
        fiveRoundsButton = findViewById(R.id.fiveRoundsButton);
        sevenRoundsButton = findViewById(R.id.sevenRoundsButton);
        onClickListeners();
    }

    //Parametr dotyczący liczby kategorii - dodać
    private void onClickListeners() {
        final View.OnClickListener listener = new View.OnClickListener() {
            Intent intentChooseCategories;
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.threeRoundsButton:
                        intentChooseCategories = new Intent(getApplicationContext(), ChooseCategoriesActivity.class);
                        startActivity(intentChooseCategories);
                        break;
                    case R.id.fiveRoundsButton:
                        intentChooseCategories = new Intent(getApplicationContext(), ChooseCategoriesActivity.class);
                        startActivity(intentChooseCategories);
                        break;
                    case R.id.sevenRoundsButton:
                        intentChooseCategories = new Intent(getApplicationContext(), ChooseCategoriesActivity.class);
                        startActivity(intentChooseCategories);
                        break;
                    default:
                        break;
                }
            }
        };
        threeRoundsButton.setOnClickListener(listener);
        fiveRoundsButton.setOnClickListener(listener);
        sevenRoundsButton.setOnClickListener(listener);
    }


}
