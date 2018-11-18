package wisielec.wisielec.com.domain;

import java.io.Serializable;

/**
 * Created by sebastian on 13.01.18.
 */

public class Rank implements Serializable {
    private String rank;

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }
}
