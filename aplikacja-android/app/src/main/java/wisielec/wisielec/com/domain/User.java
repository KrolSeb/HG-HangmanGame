package wisielec.wisielec.com.domain;

/**
 * Created by sebastian on 12.01.18(Saturday).
 */

public class User {
    private String avatarURL;
    private String email;
    private String id;
    private String isAccountActivated;
    private String isActuallyLogged;
    private String password;
    private int points;
    private String rank;
    private int rankingPosition;
    private String userName;

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

    public String getIsAccountActivated() {
        return isAccountActivated;
    }

    public void setIsAccountActivated(String isAccountActivated) {
        this.isAccountActivated = isAccountActivated;
    }

    public String getIsActuallyLogged() {
        return isActuallyLogged;
    }

    public void setIsActuallyLogged(String isActuallyLogged) {
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
}