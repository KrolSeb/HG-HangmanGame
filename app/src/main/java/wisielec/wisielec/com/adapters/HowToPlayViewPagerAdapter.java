package wisielec.wisielec.com.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import wisielec.wisielec.com.R;


public class HowToPlayViewPagerAdapter extends PagerAdapter {
    private static final String IMAGE_DESCRIPTION_1 = "Na początku wybierz liczbę rund - 3,5 lub 7.";
    private static final String IMAGE_DESCRIPTION_2 = "Kategorie gry zostały wylosowane.";
    private static final String IMAGE_DESCRIPTION_3 = "Możesz je zmienić lub przejść bezpośrednio do rozgrywki.";
    private static final String IMAGE_DESCRIPTION_4 = "Widok gry - kolor podkreśleń liter hasła oznacza jego trudność. Zielone - hasło łatwe.";
    private static final String IMAGE_DESCRIPTION_5 = "Żółte podkreślenia - hasło o umiarkowanej trudności.";
    private static final String IMAGE_DESCRIPTION_6 = "Czerwone podkreślenia - hasło trudne.";
    private static final String IMAGE_DESCRIPTION_7 = "Wybierz literę, aby rozpocząć zgadywanie hasła. Nietrafiony wybór - rysowanie szubienicy.";
    private static final String IMAGE_DESCRIPTION_8 = "Gdy cała szubienica zostanie narysowana - otrzymasz informację o przegranej rundzie.";
    private static final String IMAGE_DESCRIPTION_9 = "Jeśli litera znajduje się w haśle, pojawia się na ekranie.";
    private static final String IMAGE_DESCRIPTION_10 = "Gdy udało Ci się odgadnąć hasło, otrzymasz informację o wygranej rundzie.";
    private static final String IMAGE_DESCRIPTION_11 = "Na koniec gry wyświetlane jest podsumowanie - m.in zdobyte punkty i ranga.";

    private Map<String,Integer> imagesMap;
    private Context context;


    public HowToPlayViewPagerAdapter(Context context) {
        this.context = context;
        imagesMap = new LinkedHashMap<>();
        addDrawableToImagesMap();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.view_pager_item, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(imagesMap.get(Objects.requireNonNull(imagesMap.keySet().toArray())[position]));

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    private void addDrawableToImagesMap() {
        imagesMap.put(IMAGE_DESCRIPTION_1, R.drawable.view_pager_image_1);
        imagesMap.put(IMAGE_DESCRIPTION_2, R.drawable.view_pager_image_2);
        imagesMap.put(IMAGE_DESCRIPTION_3, R.drawable.view_pager_image_3);
        imagesMap.put(IMAGE_DESCRIPTION_4, R.drawable.view_pager_image_4);
        imagesMap.put(IMAGE_DESCRIPTION_5, R.drawable.view_pager_image_5);
        imagesMap.put(IMAGE_DESCRIPTION_6, R.drawable.view_pager_image_6);
        imagesMap.put(IMAGE_DESCRIPTION_7, R.drawable.view_pager_image_7);
        imagesMap.put(IMAGE_DESCRIPTION_8, R.drawable.view_pager_image_8);
        imagesMap.put(IMAGE_DESCRIPTION_9, R.drawable.view_pager_image_9);
        imagesMap.put(IMAGE_DESCRIPTION_10, R.drawable.view_pager_image_10);
        imagesMap.put(IMAGE_DESCRIPTION_11, R.drawable.view_pager_image_11);
    }

    public List<String> getDescriptions(){
        return new ArrayList<>(imagesMap.keySet());
    }

    @Override
    public int getCount() {
        return imagesMap.size();
    }
}