package wisielec.wisielec.com.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import wisielec.wisielec.com.R;

public class UsersViewHolder extends RecyclerView.ViewHolder {
    private TextView usernameTextView;
    private TextView pointsTextView;

    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);
        usernameTextView= itemView.findViewById(R.id.usernameTextView);
        pointsTextView = itemView.findViewById(R.id.pointsTextView);
    }

    public TextView getUsernameTextView() {
        return usernameTextView;
    }

    public TextView getPointsTextView() {
        return pointsTextView;
    }

}
