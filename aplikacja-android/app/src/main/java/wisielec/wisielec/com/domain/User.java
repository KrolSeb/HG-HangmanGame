package wisielec.wisielec.com.domain;

import java.io.Serializable;

import wisielec.wisielec.com.enums.Rank;

/**
 * Created by sebastian on 12.01.18(Saturday).
 */

public class User implements Serializable {
    private String avatarURL;
    private String email;
    private String id;
    private boolean isAccountActivated;
    private boolean isActuallyLogged;
    private String password;
    private int points;
    private String rank;
    private int rankingPosition;
    private String userName;

    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
        this.userName  = this.email.split("@")[0];
    }

    public User() {
        this.avatarURL = "";
        this.email = "";
        this.id = "";
        this.isAccountActivated = true;
        this.isActuallyLogged = true;
        this.password = "";
        this.points = 0;
        this.rank = Rank.SZEREGOWY.toString();
        this.rankingPosition = 0;
        this.userName = "";
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getIsAccountActivated() {
        return isAccountActivated;
    }

    public void setIsAccountActivated(boolean isAccountActivated) {
        this.isAccountActivated = isAccountActivated;
    }

    public boolean getIsActuallyLogged() {
        return isActuallyLogged;
    }

    public void setIsActuallyLogged(boolean isActuallyLogged) {
        this.isActuallyLogged = isActuallyLogged;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getRankingPosition() {
        return rankingPosition;
    }

    public void setRankingPosition(int rankingPosition) {
        this.rankingPosition = rankingPosition;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "avatarURL='" + avatarURL + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", isAccountActivated=" + isAccountActivated +
                ", isActuallyLogged=" + isActuallyLogged +
                ", password='" + password + '\'' +
                ", points=" + points +
                ", rank='" + rank + '\'' +
                ", rankingPosition=" + rankingPosition +
                ", userName='" + userName + '\'' +
                '}';
    }
}