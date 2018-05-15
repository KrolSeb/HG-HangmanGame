package wisielec.wisielec.com.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wisielec.wisielec.com.R;
import wisielec.wisielec.com.adapters.RankingListAdapter;
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.services.UserService;

/**
 * Created by X on 2018-04-18.
 */

public class RankingActivity extends MainActivity {
    /**
     * RankingActivityElements
     * TextViews
     */
    @BindView(R.id.firstUserPointsRankPositonTextView)
    TextView firstUserPointsTextView;
    @BindView(R.id.secondUserPointsRankPositionTextView)
    TextView secondUserPointsTextView;
    @BindView(R.id.thirdUserPointsRankPositionTextView)
    TextView thirdUserPointsTextView;
    @BindView(R.id.firstUserNameRankPositionTextView)
    TextView firstUserNameTextView;
    @BindView(R.id.secondUserNameRankPositionTextView)
    TextView secondUserNameTextView;
    @BindView(R.id.thirdUserNameRankPositionTextView)
    TextView thirdUserNameTextView;

    /**
     * RankingActivityElements
     * ListView
     */
    @BindView(R.id.infinityUserRankingList)
    ListView infinityUserRankingList;

    private UserService userService = UserService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ButterKnife.bind(this);

        final RankingListAdapter rankingListAdapter = new RankingListAdapter(this);

        userService.getBestUsersFromRanking(new UserService.IBestUserCallback() {
            @Override
            public void onSuccess(List<User> userList) {
                User user;
                String userName;

                user = userList.get(0);
                userName = (user.getUserName().isEmpty()) ? user.getEmail() : user.getUserName();
                firstUserNameTextView.setText(String.valueOf(userName));
                firstUserPointsTextView.setText(String.valueOf(user.getPoints()));

                user = userList.get(1);
                userName = (user.getUserName().isEmpty()) ? user.getEmail() : user.getUserName();
                secondUserNameTextView.setText(String.valueOf(userName));
                secondUserPointsTextView.setText(String.valueOf(user.getPoints()));

                user = userList.get(2);
                userName = (user.getUserName().isEmpty()) ? user.getEmail() : user.getUserName();
                thirdUserNameTextView.setText(String.valueOf(userName));
                thirdUserPointsTextView.setText(String.valueOf(user.getPoints()));

                rankingListAdapter.setUserList(userList);
                infinityUserRankingList.setAdapter(rankingListAdapter);
            }
        });
    }
}