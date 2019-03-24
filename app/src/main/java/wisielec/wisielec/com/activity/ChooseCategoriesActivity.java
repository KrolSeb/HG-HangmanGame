package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import wisielec.wisielec.com.R;
import wisielec.wisielec.com.adapters.CategoriesAdapter;
import wisielec.wisielec.com.domain.Category;
import wisielec.wisielec.com.services.CategoryService;

/**
 * Created by sebastian on 12.04.18.
 */

public class ChooseCategoriesActivity extends GameActivityAbstract {
    @BindView(R.id.startGameButton)
    protected Button startGameButton;

    private RecyclerView recyclerView;
    private CategoriesAdapter categoriesAdapter;

    private int categoryCount;
    private ArrayList<Category> randomizedCategoryList;
    private CategoryService categoryService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_categories);
        ButterKnife.bind(this);

        randomizedCategoryList = new ArrayList<>();
        categoryService = CategoryService.getInstance();

        getDataFromIntent();
        setRecyclerViewProperties();
        changeCategory();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        categoryCount = (int) intent.getSerializableExtra("categoryNumber");
    }

    private void setRecyclerViewProperties() {
        recyclerView = findViewById(R.id.recycler_view_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.changeCategoryButton)
    public void changeCategory() {
        categoryService.getCategoriesFromDatabase(categoryCount, categoryList -> {
            randomizedCategoryList.clear();
            randomizedCategoryList.addAll(categoryList);
            categoriesAdapter = new CategoriesAdapter(ChooseCategoriesActivity.this, randomizedCategoryList);
            recyclerView.setAdapter(categoriesAdapter);
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