package com.senr.ichra.dom011;


public class MaleFunctions {

    private String problem;
    private String suggestion;

    public MaleFunctions(String problem, String suggestion) {
        this.problem = problem;
        this.suggestion = suggestion;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
