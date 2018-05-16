package wisielec.wisielec.com.services;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import wisielec.wisielec.com.domain.Category;

public class CategoryService {

    private static final String TAG = "CategoryService";
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private static CategoryService instance = null;

    private CategoryService() {
    }

    public static CategoryService getInstance() {
        if (instance == null) instance = new CategoryService();
        return instance;
    }


    public void getCategoriesFromDatabase(final int count, final ICategoryCallback callback) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("categories");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> categoryList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Category category = ds.getValue(Category.class);
                    categoryList.add(category);
                }

                List<Category> randomizedCategoryList = new ArrayList<>();
                List<Integer> arrayWithRadomizedNumbers = getRandomArray(0, categoryList.size()-1);

                int i = 0;
                for(Integer value: arrayWithRadomizedNumbers){
                    if(i++ < count){
                        randomizedCategoryList.add(categoryList.get(value));
                    }else{
                        break;
                    }
                }
                callback.onSuccess(randomizedCategoryList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private List<Integer> getRandomArray(int min, int max){
        List<Integer> A = new ArrayList<>();
        while (max >= min){
            A.add(max--);
        }

        A.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer int1, Integer int2) {
                Random random = new Random();
                return 1 - random.nextInt(2);
            }
        });

        return A;
    }


    public interface ICategoryCallback {
        void onSuccess(List<Category> categoryList);
    }

}
