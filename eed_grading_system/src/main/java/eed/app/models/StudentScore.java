package eed.app.models;

import java.util.HashMap;

public class StudentScore {
    private String matric;
    private String sessionId;
    private String departmentName;
    private String departmentCode;
    private int level;
    private String studentId;
    private HashMap<String, Scorer> first;
    private HashMap<String, Scorer> second;

    public StudentScore(String matric, String sessionId, String departmentName, String departmentCode, int level, String studentId, HashMap<String, Scorer> first, HashMap<String, Scorer> second) {
        this.matric = matric;
        this.sessionId = sessionId;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.level = level;
        this.studentId = studentId;
        this.first = first;
        this.second = second;
    }

    public String getMatric() {
        return matric;
    }

    public void setMatric(String matric) {
        this.matric = matric;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public HashMap<String, Scorer> getFirst() {
        return first;
    }

    public void setFirst(HashMap<String, Scorer> first) {
        this.first = first;
    }

    public HashMap<String, Scorer> getSecond() {
        return second;
    }

    public void setSecond(HashMap<String, Scorer> second) {
        this.second = second;
    }
}
