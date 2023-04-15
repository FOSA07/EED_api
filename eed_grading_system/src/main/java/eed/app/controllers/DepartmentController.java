package eed.app.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eed.app.models.Department;
import eed.app.models.Session;
import eed.app.services.DepartmentExistsException;
import eed.app.services.DepartmentService;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    // @Autowired
    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping(path= "/addDepartment", consumes = "application/json")
    public ResponseEntity<?> createDepartment(@RequestBody Department departmentRequest) {
        try {
            Department department = departmentService.createDepartment(
                departmentRequest.getSession(), 
                departmentRequest.getDepartmentName(), 
                departmentRequest.getDepartmentCode(), 
                departmentRequest.getLevel(), 
                departmentRequest.getNumberOfStudents()
            );
            return ResponseEntity.ok(department);
        } catch (DepartmentExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "/getSession")
    public ResponseEntity<?> getSession(){

        try {
            List<Session> session = departmentService.getAllSessions();

            return ResponseEntity.ok(session);
        } catch (DepartmentExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "/getDepartment")
    public ResponseEntity<?> getDepartment(@RequestParam String session){

        try {
            List<Department> department = departmentService.getAllDepartmentsForSession(session);

            return ResponseEntity.ok(department);
        } catch (DepartmentExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping(path = "/deleteSession")
    public ResponseEntity<?> deleteSession(@RequestParam String session){

        try {
            String result = departmentService.deleteSession(session);

            return ResponseEntity.ok(result);
        } catch (DepartmentExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }

    }

    @DeleteMapping(path = "/deleteDepartment")
    public ResponseEntity<?> deleteDepartment(@RequestParam String sessionId,String departmentName, String departmentCode){

        try {
            String result = departmentService.deleteDepartment(sessionId, departmentName, departmentCode);

            return ResponseEntity.ok(result);
        } catch (DepartmentExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }

    }
}
