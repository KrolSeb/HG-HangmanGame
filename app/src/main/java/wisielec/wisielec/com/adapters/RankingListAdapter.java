package wisielec.wisielec.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.User;

public class RankingListAdapter extends BaseAdapter{
    private Context context;
    private List<User> users;
    private static LayoutInflater inflater = null;

    public RankingListAdapter(Context context){
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public RankingListAdapter(Context context, List<User> users) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.users = users;
    }

    public void setUserList(List<User> userList){
        this.users = userList;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) view = inflater.inflate(R.layout.ranking_single_item, null);

        TextView positionTextView = view.findViewById(R.id.position);
        TextView userNameTextView = view.findViewById(R.id.userName);
        TextView userPointsTextView = view.findViewById(R.id.points);

        positionTextView.setText(String.valueOf(position+1));
        userNameTextView.setText(String.valueOf(users.get(position).getUserName()));
        userPointsTextView.setText(String.valueOf(users.get(position).getPoints())+" pkt");

        return view;
    }
}

