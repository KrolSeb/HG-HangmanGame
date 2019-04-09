package wisielec.wisielec.com.interfaces;

import java.util.List;
import wisielec.wisielec.com.domain.User;


public interface GetBestThreeUsersCallback {
    void onSuccess(List<User> userList);
}
