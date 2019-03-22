package wisielec.wisielec.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.User;

public class UsersRankingAdapter extends RecyclerView.Adapter<UsersViewHolder> {
    private Context context;
    private ArrayList<User> users;

    public UsersRankingAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsersViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_user_data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder usersViewHolder, int position) {
        usersViewHolder.getUsernameTextView().setText(String.valueOf(users.get(position).getUserName()));
        usersViewHolder.getPointsTextView().setText(String.valueOf(users.get(position).getPoints() +  " pkt"));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}

