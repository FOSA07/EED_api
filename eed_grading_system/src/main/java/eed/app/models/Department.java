package eed.app.models;

public class Department {
    private String session;
    private String departmentName;
    private String departmentCode;
    private int level;
    private int numberOfStudents;

    public Department(String session, String departmentName, String departmentCode, int level, int numberOfStudents) {
        this.session = session;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.level = level;
        this.numberOfStudents = numberOfStudents;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
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

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
