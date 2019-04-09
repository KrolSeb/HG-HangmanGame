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


public class ChooseCategoriesActivity extends MainActivity {
    private static final String INTENT_KEY_CATEGORY_COUNT = "categoryCount";
    private static final String INTENT_KEY_CATEGORY_LIST = "categoryList";

    @BindView(R.id.startGameButton)
    protected Button startGameButton;

    private RecyclerView recyclerView;
    private CategoriesAdapter categoriesAdapter;

    private ArrayList<Category> randomizedCategoryList;
    private CategoryService categoryService;
    private int categoryCount;


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
        categoryCount = (int) intent.getSerializableExtra(INTENT_KEY_CATEGORY_COUNT);
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
        intentPlayGame.putExtra(INTENT_KEY_CATEGORY_COUNT, categoryCount);
        intentPlayGame.putExtra(INTENT_KEY_CATEGORY_LIST, randomizedCategoryList);
        startActivity(intentPlayGame);
        finish();
    }
}