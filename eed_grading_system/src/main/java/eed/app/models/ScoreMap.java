package eed.app.models;

public class ScoreMap {
    private double exam;
    private double test;
    private double practical;

    public ScoreMap() {
        // Default constructor required by Firestore
    }

    public ScoreMap(double exam, double test, double practical) {
        this.exam = exam;
        this.test = test;
        this.practical = practical;
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
}

