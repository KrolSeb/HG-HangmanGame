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
    private final String GREEN_COLOR_HEX_CODE = "#0ABC30";
    private final String ORANGE_COLOR_HEX_CODE = "#F8AA12";
    private final String RED_COLOR_HEX_CODE = "#F42020";

    private Context context;
    private Random random;
    private ArrayList<Category> categories;
    private final ArrayList<String> buttonColors;

    public CategoriesAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
        random = new Random();
        buttonColors = new ArrayList<>();
        setButtonColorsCodes();
    }

    private void setButtonColorsCodes(){
        buttonColors.add(GREEN_COLOR_HEX_CODE);
        buttonColors.add(ORANGE_COLOR_HEX_CODE);
        buttonColors.add(RED_COLOR_HEX_CODE);
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