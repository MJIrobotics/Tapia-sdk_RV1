package com.mji.tapia.tapiasdksample;
import java.util.List;

//ワードクラス
public class Word {

    private String category ;
    private List<String> keyword;
    private List<String> answer ;

    public Word(String id, List<String> keyword, List<String> answer){
        this.category = category;
        this.keyword = keyword;
        this.answer = answer;
    }

    public String getCategory() {
        return category;
    }

    public void setId(String category) {
        this.category = category;
    }

    public  List<String> getKeyword() {
        return keyword;
    }

    public List<String> getAnswer() {
        return answer;
    }
}
