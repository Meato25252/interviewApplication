package com.example.interviewapplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QuestionRepository {
    private Map<String,Question> questionMap;

    public QuestionRepository() {
        questionMap = new HashMap<>();
        loadQuestions();
    }

    private void loadQuestions() {
        questionMap.put("intro", new Question("intro", "自己紹介をお願いします", "text", List.of("intro_reason")));
        questionMap.put("gakuchika", new Question("gakuchika", "学生時代に力を入れたことは？", "text", List.of("gakuchika_result")));
        questionMap.put("strength", new Question("strength", "あなたの強みは何ですか？", "text", List.of("strength_reason")));
        questionMap.put("weakness", new Question("weakness", "あなたの弱みは何ですか？", "text", List.of("weakness_overcome")));

        //今は一つしか次がないがリストにして複数にする。(のちの設定でいい。)
        questionMap.put("intro_reason", new Question("intro_reason", "その自己紹介を選んだ理由は？", "text", List.of()));
        questionMap.put("gakuchika_result", new Question("gakuchika_result", "それによってどんな成果がありましたか？", "text", List.of()));
        questionMap.put("strength_reason", new Question("strength_reason", "その強みを持っていると思ったきっかけは？", "text", List.of()));
        questionMap.put("weakness_overcome", new Question("weakness_overcome", "その弱みをどう克服していますか？", "text", List.of()));
    }

    public Question getQuestion(String id) {
        return questionMap.get(id);
    }
}
