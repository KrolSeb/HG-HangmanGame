package wisielec.wisielec.com.domain;

import java.io.Serializable;

/**
 * Created by sebastian on 13.01.18.
 */

public class DifficultyLevel implements Serializable {
    private String level;

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}
