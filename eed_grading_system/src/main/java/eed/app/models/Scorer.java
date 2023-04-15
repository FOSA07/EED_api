package eed.app.models;

public class Scorer {
    private double exam;
    private double test;
    private double practical;
    private double total;
    private String grade;

    public Scorer(double exam, double test, double practical, double total, String grade) {
        this.exam = exam;
        this.test = test;
        this.practical = practical;
        this.total = total;
        this.grade = grade;
    }

    public double getExam() {
        return exam;
    }

    public void setExam(double exam) {
        this.exam = exam;
    }

    public double getTest() {
        return test;
    }

    public void setTest(double test) {
        this.test = test;
    }

    public double getPractical() {
        return practical;
    }

    public void setPractical(double practical) {
        this.practical = practical;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
