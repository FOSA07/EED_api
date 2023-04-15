package eed.app.models;

import java.util.Map;

public class Score {
    private String sessionId;
    private String departmentName;
    private String departmentCode;
    private int level;
    private String section;
    private String studentId;
    private Map<String, Double> scoreMap;

    public Score(String sessionId, String departmentName, String departmentCode, int level, String section, Map<String, Double> scoreMap, String studentId) {
        this.sessionId = sessionId;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.level = level;
        this.section = section;
        this.scoreMap = scoreMap;
        this.studentId = studentId;
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Map<String, Double> getScoreMap() {
        return scoreMap;
    }

    public void setScoreMap(Map<String, Double> scoreMap) {
        this.scoreMap = scoreMap;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
