package eed.app.controllers;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eed.app.models.DeleteStudent;
import eed.app.models.Student;
import eed.app.services.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(path= "/addStudent", consumes = "application/json")
    public ResponseEntity<?> createStudent(@RequestBody Student studentRequest) throws Exception {

       try {
            Student student =  studentService.addStudents(
                studentRequest.getStudentList(), 
                studentRequest.getSessionId(), 
                studentRequest.getDepartmentName(), 
                studentRequest.getDepartmentCode(), 
                studentRequest.getLevel()
            );

            return ResponseEntity.ok(student);
       } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } 
    }

    @GetMapping(path = "/getStudent")
    public ResponseEntity<?> getStudent(@RequestParam String sessionId, String departmentName, String departmentCode, int level) {

        List result;
        try {
            result = studentService.getAllStudents(sessionId, departmentName, departmentCode, level);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }

    }

    @DeleteMapping(path = "/deleteStudent")
    public ResponseEntity<?> deleteStudent(@RequestBody DeleteStudent deleteStudent){

        try {
            String result = studentService.deleteStudent(deleteStudent);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }

    }
}
