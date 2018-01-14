package wisielec.wisielec.com.domain;

/**
 * Created by sebastian on 12.01.18(Saturday).
 */

public class User {
    private String id;
    private String username;
    private String password;
    private String avatarURL;
    private int rankingPosition;
    private long points;
    private String rank;
    private boolean isAccountActivated;
    private boolean isActuallyLogged;

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public void setRankingPosition(int rankingPosition) {
        this.rankingPosition = rankingPosition;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setAccountActivated(boolean accountActivated) {
        isAccountActivated = accountActivated;
    }

    public void setActuallyLogged(boolean actuallyLogged) {
        isActuallyLogged = actuallyLogged;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public int getRankingPosition() {
        return rankingPosition;
    }

    public long getPoints() {
        return points;
    }

    public String getRank() {
        return rank;
    }

    public boolean isAccountActivated() {
        return isAccountActivated;
    }

    public boolean isActuallyLogged() {
        return isActuallyLogged;
    }

    @Override
    public String toString(){
        return "User{" +
                "userName='" + username + '\'' +
                ", avatarURL='" + avatarURL + '\'' +
                ", rankingPosition=" + rankingPosition + '\'' +
                ", points=" + points +
                ", rank='" + rank + '\'' +
                ", isAccountActivated=" + isAccountActivated +
                ", isActuallyLogged=" + isActuallyLogged +
                '}';
    }
}
