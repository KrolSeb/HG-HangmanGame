package wisielec.wisielec.com.activity;

import android.os.Bundle;
import android.widget.TextView;

import wisielec.wisielec.com.R;

/**
 * Created by X on 2018-04-18.
 */

public class RankingActivity extends MainActivity {
    TextView firstUserPointsTextView;
    TextView secondUserPointsTextView;
    TextView thirdUserPointsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        firstUserPointsTextView = (TextView) findViewById(R.id.firstUserPointsRankPositonTextView);
        secondUserPointsTextView = (TextView) findViewById(R.id.secondUserPointsRankPositionTextView);
        thirdUserPointsTextView = (TextView) findViewById(R.id.thirdUserPointsRankPositionTextView);
    }
}
