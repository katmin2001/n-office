package com.fis.crm.service.dto;

import java.util.List;

public class QuestionsAndAnswersDTO {
    private String question;
    private List<AnswersDTO> answers;
    private String startContent;
    private String endContent;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswersDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswersDTO> answers) {
        this.answers = answers;
    }

    public String getStartContent() {
        return startContent;
    }

    public void setStartContent(String startContent) {
        this.startContent = startContent;
    }

    public String getEndContent() {
        return endContent;
    }

    public void setEndContent(String endContent) {
        this.endContent = endContent;
    }
}
