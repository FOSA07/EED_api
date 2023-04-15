package eed.app.models;

import java.util.List;

public class Student {
    private List<String> studentList;
    private String sessionId;
    private String departmentName;
    private String departmentCode;
    private int level;

    public Student(List<String> studentList, String sessionId, String departmentName, String departmentCode,
            int level) {
        this.studentList = studentList;
        this.sessionId = sessionId;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.level = level;
    }

    public List<String> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<String> studentList) {
        this.studentList = studentList;
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
}

