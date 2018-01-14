package com.wisielec.wisielec.domain;

/**
 * Created by Emerex on 08/01/2018.
 */
public class User {
    private String userName;
    private String avatarURL;
    private String password;
    private int rankingPosition;
    private long points;
    private String rank;
    private boolean isAccountActivated;
    private boolean isActuallyLogged;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRankingPosition() {
        return rankingPosition;
    }

    public void setRankingPosition(int rankingPosition) {
        this.rankingPosition = rankingPosition;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public boolean isAccountActivated() {
        return isAccountActivated;
    }

    public void setAccountActivated(boolean accountActivated) {
        isAccountActivated = accountActivated;
    }

    public boolean isActuallyLogged() {
        return isActuallyLogged;
    }

    public void setActuallyLogged(boolean actuallyLogged) {
        isActuallyLogged = actuallyLogged;
    }

    public String getAvatarURL() {
        return this.avatarURL;
    }

    public String getUserName() {
        return this.userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", avatarURL='" + avatarURL + '\'' +
                ", rankingPosition=" + rankingPosition +
                ", points=" + points +
                ", rank='" + rank + '\'' +
                ", isAccountActivated=" + isAccountActivated +
                ", isActuallyLogged=" + isActuallyLogged +
                '}';
    }
}
