package wisielec.wisielec.com.domain;

import java.util.List;

public class Subcategory {
    private String subcategoryName;
    private List<Word> words;

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "Subcategory{" +
                "subcategoryName='" + subcategoryName + '\'' +
                ", words=" + words +
                '}';
    }
}
