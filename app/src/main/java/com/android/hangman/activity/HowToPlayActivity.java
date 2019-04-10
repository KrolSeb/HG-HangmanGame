package com.android.hangman.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hangman.R;
import com.android.hangman.adapters.HowToPlayViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;


public class HowToPlayActivity extends MainActivity {
    private static final int ZERO = 0;
    private static final int DEFAULT_MARGIN = 8;

    private HowToPlayViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private LinearLayout sliderDotsLayout;
    private List<ImageView> dots;
    private List<String> descriptions;
    private TextView descriptionTextView;

    private int dotsCount;
    private int currentPosition;
    private int scrollState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        setViewPagerProperties();
        createViewPagerDots();
        setStartupActiveDot();
        changeActiveDotOnPageSelected();
    }

    private void changeActiveDotOnPageSelected() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                for (int i = 0; i < dotsCount; i++) {
                    dots.get(i).setImageDrawable(ContextCompat.getDrawable(HowToPlayActivity.this, R.drawable.style_dot_non_active));
                }
                dots.get(position).setImageDrawable(ContextCompat.getDrawable(HowToPlayActivity.this, R.drawable.style_dot_active));
                descriptionTextView.setText(descriptions.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                handleScrollState(state);
                scrollState = state;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
        });
    }

    private void handleScrollState(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            setNextItemIfNeeded();
        }
    }

    private void setNextItemIfNeeded() {
        if (!isScrollStateSettling()) {
            handleSetNextItem();
        }
    }

    private boolean isScrollStateSettling() {
        return scrollState == ViewPager.SCROLL_STATE_SETTLING;
    }

    private void handleSetNextItem() {
        final int lastPosition = dotsCount - 1;
        if(currentPosition == 0) {
            viewPager.setCurrentItem(lastPosition, false);
        }
        else if(currentPosition == lastPosition) {
            viewPager.setCurrentItem(0, false);
        }
    }

    private void setStartupActiveDot() {
        dots.get(ZERO).setImageDrawable(ContextCompat.getDrawable(HowToPlayActivity.this, R.drawable.style_dot_active));
        descriptionTextView.setText(descriptions.get(ZERO));
    }

    private void createViewPagerDots() {
        dotsCount = viewPagerAdapter.getCount();
        for (int i = 0; i < dotsCount; i++) {
            dots.add(new ImageView(this));
            dots.get(i).setImageDrawable(ContextCompat.getDrawable(HowToPlayActivity.this, R.drawable.style_dot_non_active));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(DEFAULT_MARGIN, ZERO, DEFAULT_MARGIN, ZERO);
            sliderDotsLayout.addView(dots.get(i), params);
        }
    }

    private void setViewPagerProperties(){
        viewPager =  findViewById(R.id.imageViewPager);
        viewPagerAdapter = new HowToPlayViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        sliderDotsLayout =  findViewById(R.id.sliderDotsLayout);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        dots = new ArrayList<>();
        descriptions = new ArrayList<>();
        descriptions = viewPagerAdapter.getDescriptions();
    }
}
