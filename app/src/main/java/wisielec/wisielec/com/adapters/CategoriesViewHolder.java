package wisielec.wisielec.com.adapters;

import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import wisielec.wisielec.com.R;

public class CategoriesViewHolder extends RecyclerView.ViewHolder  {
    private Button singleCategoryButton;

    public CategoriesViewHolder(@NonNull View itemView) {
        super(itemView);
        singleCategoryButton= itemView.findViewById(R.id.singleCategoryButton);
    }

    public Button getSingleCategoryButton() {
        return singleCategoryButton;
    }

}
