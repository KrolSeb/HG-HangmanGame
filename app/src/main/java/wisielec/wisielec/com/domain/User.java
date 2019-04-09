package wisielec.wisielec.com.domain;

import com.google.firebase.database.IgnoreExtraProperties;
import wisielec.wisielec.com.enums.Rank;


@IgnoreExtraProperties
public class User {
    private final String DEFAULT_AVATAR = "https://firebasestorage.googleapis.com/v0/b/gra-w-wisielca.appspot.com/o/avatar.png?alt=media&token=829e201b-98ec-4a66-9064-2d6405bc8dc5";

    private String id;
    private String email;
    private String userName;
    private String password;
    private String avatarURL;
    private String rank;
    private boolean isAccountActivated;
    private boolean isActuallyLogged;
    private int points;


    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
        this.userName  = this.email.split("@")[0];
    }

    public User() {
        this.avatarURL = DEFAULT_AVATAR;
        this.email = "";
        this.id = "";
        this.isAccountActivated = true;
        this.isActuallyLogged = true;
        this.password = "";
        this.points = 0;
        this.rank = Rank.PRIVATE.toString();
        this.userName = "";
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsAccountActivated(boolean isAccountActivated) {
        this.isAccountActivated = isAccountActivated;
    }

    public void setIsActuallyLogged(boolean isActuallyLogged) {
        this.isActuallyLogged = isActuallyLogged;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public boolean getIsAccountActivated() {
        return isAccountActivated;
    }

    public boolean getIsActuallyLogged() {
        return isActuallyLogged;
    }

    public String getPassword() {
        return password;
    }

    public int getPoints() {
        return points;
    }

    public String getRank() {
        return rank;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "User{" + "avatarURL='" + avatarURL + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", isAccountActivated=" + isAccountActivated +
                ", isActuallyLogged=" + isActuallyLogged +
                ", password='" + password + '\'' +
                ", points=" + points +
                ", rank='" + rank + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}