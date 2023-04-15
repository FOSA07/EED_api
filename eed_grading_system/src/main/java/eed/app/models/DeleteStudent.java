package eed.app.models;

public class DeleteStudent {
    private String sessionId;
    private String departmentId;
    private int level;
    private String studentId;
    
    public DeleteStudent() {
        // Default constructor required for Firebase
    }
    
    public DeleteStudent(String sessionId, String departmentId, int level, String studentId) {
        this.sessionId = sessionId;
        this.departmentId = departmentId;
        this.level = level;
        this.studentId = studentId;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public String getDepartmentId() {
        return departmentId;
    }
    
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
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
}
