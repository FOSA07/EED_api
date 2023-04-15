package eed.app.services;

public class DepartmentExistsException extends RuntimeException {

    public DepartmentExistsException(String departmentName, String departmentCode, String session) {
        super("Department " + departmentName + " with code " + departmentCode + " already exists for session " + session);
    }


}