package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

import wisielec.wisielec.com.R;
import wisielec.wisielec.com.adapters.RandomizedCategoryAdapter;
import wisielec.wisielec.com.domain.Category;
import wisielec.wisielec.com.services.CategoryService;

/**
 * Created by sebastian on 12.04.18.
 */

public class ChooseCategoriesActivity extends GameActivityAbstract {
    protected Button startGameButton;

    @BindView(R.id.categoryListView)
    ListView categoryListView;

    private int categoryCount;
    private ArrayList<Category> randomizedCategoryList = new ArrayList<>();
    private CategoryService categoryService = CategoryService.getInstance();
    private RandomizedCategoryAdapter randomizedCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_categories);
        ButterKnife.bind(this);

        getDataFromIntent();

        randomizedCategoryAdapter = new RandomizedCategoryAdapter(this);
        startGameButton = findViewById(R.id.startGameButton);
        changeCategory();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        categoryCount = (int) intent.getSerializableExtra("categoryNumber");
    }

    @OnClick(R.id.changeCategoryButton)
    public void changeCategory() {
        categoryService.getCategoriesFromDatabase(categoryCount, categoryList -> {
            randomizedCategoryList.clear();
            randomizedCategoryList.addAll(categoryList);
            randomizedCategoryAdapter.setCategoryList(randomizedCategoryList);
            categoryListView.setAdapter(randomizedCategoryAdapter);
        });
    }

    @OnClick(R.id.startGameButton)
    public void changeActivity(){
        Intent intentPlayGame = new Intent(getApplicationContext(), PlayGameActivity.class);
        intentPlayGame.putExtra("categoryCount", categoryCount);
        intentPlayGame.putExtra("categoryList", randomizedCategoryList);
        startActivity(intentPlayGame);
        finish();
    }
}