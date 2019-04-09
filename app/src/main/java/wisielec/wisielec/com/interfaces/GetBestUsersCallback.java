package wisielec.wisielec.com.interfaces;

import java.util.ArrayList;
import wisielec.wisielec.com.domain.User;


public interface GetBestUsersCallback {
    void onSuccess(ArrayList<User> userList);
}
