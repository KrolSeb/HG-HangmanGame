package wisielec.wisielec.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.Category;

public class RandomizedCategoryAdapter extends BaseAdapter{

    private Context context;
    private List<Category> categoryList;
    private static LayoutInflater inflater = null;

    public RandomizedCategoryAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public RandomizedCategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categoryList = categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) view = inflater.inflate(R.layout.category_single_item, null);

        Button singleCategoryButton = view.findViewById(R.id.singleCategoryButton);
        singleCategoryButton.setText(categoryList.get(position).getCategoryName());


        return view;
    }
}


