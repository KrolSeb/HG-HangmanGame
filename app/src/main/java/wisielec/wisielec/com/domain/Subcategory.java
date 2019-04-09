package wisielec.wisielec.com.domain;

import java.io.Serializable;
import java.util.List;

public class Subcategory implements Serializable {
    private String subcategoryName;
    private List<Word> words;

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public List<Word> getWords() {
        return words;
    }

    @Override
    public String toString() {
        return "Subcategory{" + "subcategoryName='" + subcategoryName + '\'' + ", words=" + words + '}';
    }
}
