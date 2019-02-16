package wisielec.wisielec.com.services;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
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
                HashSet<Integer> arrayWithRandomizedNumbers = getRandomArray(0, categoryList.size()-1, count);

                for(Integer value : arrayWithRandomizedNumbers){
                    randomizedCategoryList.add(categoryList.get(value));
                }
                callback.onSuccess(randomizedCategoryList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private HashSet<Integer> getRandomArray(int min, int max, int numOfElements){
        HashSet<Integer> A = new HashSet<>();
        Random random = new Random();

        int i = 0;
        while(A.size() < numOfElements) {
            A.add(random.nextInt((max - min) + 1) + min);
        }

        return A;
    }


    public interface ICategoryCallback {
        void onSuccess(List<Category> categoryList);
    }

}
