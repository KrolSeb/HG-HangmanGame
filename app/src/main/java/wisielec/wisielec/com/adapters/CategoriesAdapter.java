package wisielec.wisielec.com.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.Category;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {
    private static final String HEX_COLOR_GREEN = "#0ABC30";
    private static final String HEX_COLOR_ORANGE = "#F8AA12";
    private static final String HEX_COLOR_RED = "#F42020";
    private static final String HEX_COLOR_GOLD = "#FDDC54";
    private static final String HEX_COLOR_BLUE = "#0082B2";
    private static final String HEX_COLOR_BEIGE = "#DEAD78";
    private static final String HEX_COLOR_VIOLET = "#5B507A";

    private final ArrayList<String> buttonColors;
    private ArrayList<Category> categories;

    private Random random;
    private Context context;


    public CategoriesAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
        random = new Random();
        buttonColors = new ArrayList<>();
        setButtonColors();
    }

    private void setButtonColors(){
        buttonColors.add(HEX_COLOR_GREEN);
        buttonColors.add(HEX_COLOR_ORANGE);
        buttonColors.add(HEX_COLOR_RED);
        buttonColors.add(HEX_COLOR_GOLD);
        buttonColors.add(HEX_COLOR_BEIGE);
        buttonColors.add(HEX_COLOR_BLUE);
        buttonColors.add(HEX_COLOR_VIOLET);
    }
    
    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoriesViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder categoriesViewHolder, int position) {
        categoriesViewHolder.getSingleCategoryButton().setText(categories.get(position).getCategoryName());
        setButtonBackgroundColor(categoriesViewHolder);
    }

    private void setButtonBackgroundColor(@NonNull CategoriesViewHolder categoriesViewHolder) {
        int randomNumber = random.nextInt(buttonColors.size());
        categoriesViewHolder.getSingleCategoryButton().setBackgroundColor(Color.parseColor(buttonColors.get(randomNumber)));
    }

    public int getItemCount() {
        return categories.size();
    }

}