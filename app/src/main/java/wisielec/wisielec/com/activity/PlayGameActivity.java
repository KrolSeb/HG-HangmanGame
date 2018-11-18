package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.os.Bundle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.Category;
import wisielec.wisielec.com.domain.Subcategory;
import wisielec.wisielec.com.domain.Word;

/**
 * Created by sebastian on 12.04.18.
 */

public class PlayGameActivity extends GameActivityAbstract implements Serializable {
    private Random random = new Random();
    private int randomSubcategoryNumber;
    private int randomWordNumber;
    private int categoryCount;
    private int categoryNumber = 0;

    protected String categoryName;
    protected String subcategoryName;
    protected int difficultyLevel;
    protected String wordName;

    protected Category category;
    protected Subcategory subcategory;
    protected Word word;
    protected ArrayList<Category> categoryList;
    protected ArrayList<Subcategory> subcategoryList = new ArrayList<>();
    protected ArrayList<Word> wordsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        Intent intent = getIntent();
        categoryCount = (int) intent.getSerializableExtra("categoryCount");
        categoryList = (ArrayList<Category>) intent.getSerializableExtra("categoryList");

        loadData(categoryNumber);
    }

    private void loadData(int categoryNumber){
        category = categoryList.get(categoryNumber);

        subcategoryList.addAll(category.getSubcategories());
        randomSubcategoryNumber = random.nextInt(subcategoryList.size());
        subcategory = subcategoryList.get(randomSubcategoryNumber);

        wordsList.addAll(subcategory.getWords());
        randomWordNumber = random.nextInt(wordsList.size());
        word = wordsList.get(randomWordNumber);

        categoryName = category.getCategoryName();
        subcategoryName = subcategory.getSubcategoryName();
        difficultyLevel = word.getDifficultyLevel();
        wordName = word.getName();

        System.out.println(categoryName);
        System.out.println(subcategoryName);
        System.out.println(difficultyLevel);
        System.out.println(wordName);

        setCategoryNumber(++categoryNumber);
    }

    public void setCategoryNumber(int categoryNumber) {
        this.categoryNumber = categoryNumber;
    }
}