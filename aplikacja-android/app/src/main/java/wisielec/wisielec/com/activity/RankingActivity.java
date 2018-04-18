package wisielec.wisielec.com.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import wisielec.wisielec.com.R;

/**
 * Created by X on 2018-04-18.
 */

public class RankingActivity extends MainActivity {

    TextView user1;
    TextView user2;
    TextView user3;
    ListView listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        user1 = (TextView) findViewById(R.id.user1);
        user2 = (TextView) findViewById(R.id.user2);
        user3 = (TextView) findViewById(R.id.user3);
        listUser = (ListView) findViewById(R.id.listUser);
    }
}
