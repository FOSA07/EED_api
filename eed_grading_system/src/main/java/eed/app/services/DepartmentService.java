package eed.app.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteBatch;
import com.google.firebase.cloud.FirestoreClient;

import appdev.firebase.config.CustomExceptionHandler;
import eed.app.models.Department;
import eed.app.models.Session;


@Service
public class DepartmentService {

    private final Firestore firestore = FirestoreClient.getFirestore();

    public Department createDepartment(String session, String departmentName, String departmentCode, int level, int numberOfStudents) throws ExecutionException, InterruptedException {
        WriteBatch batch = firestore.batch();
        String sessionId = getSessionId(session);
        String departmentId = getDepartmentId(departmentName, departmentCode);

        // Check if the session exists, if not, create it
        if (!sessionExists(sessionId)) {
            createSession(sessionId);
        }

        // Check if the department exists for the session
        if (departmentExists(sessionId, departmentId)) {
            throw new DepartmentExistsException(departmentName, departmentCode, session);
        }

        // Create the new department document
        HashMap<String, Object> department = new HashMap<String, Object>();
        department.put("departmentName", departmentName);
        department.put("departmentCode", departmentCode);
        department.put("level", level);
        department.put("numberOfStudents", 0);
        
        String path= "sessions/"+sessionId+"/departments/";
        batch.set(firestore.collection(path).document(departmentId), department);
        String lev = "";

        if (level == 0) {
            lev = "nd";
        } else if (level == 1) {
            lev = "hnd";
        } 

        path = path+departmentId+"/"+lev+"/";
        
        for (int i = 1; i <= numberOfStudents; i++) {
            String index = String.format("%04d", i);
            String matric = departmentCode + "*" + index;
                    
            HashMap<String, String> matriculation = new HashMap<String, String>();
            matriculation.put("matric", matric);
            batch.set(firestore.collection(path).document(matric), matriculation);

            HashMap<String, Object> score= new HashMap<String, Object>();
            score.put("exam", 0.0);
            score.put("test", 0.0);
            score.put("practical", 0.0);
            score.put("total", 0.0);
            score.put("grade", "F9");

            String scorePath = path+matric+"/score/";
            String section="first";
            for(int bb=0; bb<2; bb++){
                if(bb==0){
                    section = "first";
                }else{
                    section = "second";
                }
                batch.set(firestore.collection(scorePath).document(section), score);
            }

        }

        batch.commit();

        return null;
    }

    private String getSessionId(String session) {
        return session.replaceAll("/", "-");
    }

    private String getDepartmentId(String departmentName, String departmentCode) {
        return departmentName + "-" + departmentCode;
    }

    private boolean sessionExists(String sessionId) throws ExecutionException, InterruptedException {
        DocumentReference sessionRef = firestore.collection("sessions").document(sessionId);
        return sessionRef.get().get().exists();
    }

    private void createSession(String sessionId) throws ExecutionException, InterruptedException {
        HashMap<String, Object> session = new HashMap<String, Object>();
        session.put("createdAt", new Date());

        firestore.collection("sessions").document(sessionId).set(session).get();
    }

    private boolean departmentExists(String sessionId, String departmentId) throws ExecutionException, InterruptedException {
        String[] parts = departmentId.split("-");
        String before = parts[0];
        String after = parts[1];
                
        CollectionReference collectionRef = firestore.collection("sessions").document(sessionId).collection("departments");

        ApiFuture<QuerySnapshot> querySnapshot = collectionRef.get();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            System.out.println(document.getId() + " => " + document.getData());

            if(document.getId().contains(after) || document.getId().contains(before)){
                System.out.println(document.getId());
                System.out.println("it already exists");
                return true;
            }
        }
        return false;
    }

    // public CustomExceptionHandler customExceptionHandler() {
    //     return new CustomExceptionHandler();
    // }
    public CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();

    public List<Session> getAllSessions() throws ExecutionException, InterruptedException {
        CollectionReference sessionsRef = firestore.collection("sessions");
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = sessionsRef.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();
        List<Session> sessions = new ArrayList<>();
    
        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            Session session = new Session(document.getId());
            sessions.add(session);
        }
    
        return sessions;
    }

    public List<Department> getAllDepartmentsForSession(String session) throws ExecutionException, InterruptedException {
        String sessionId = getSessionId(session);

        String path = "sessions/"+session+"/departments/";
        CollectionReference departmentsRef = firestore.collection(path);
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = departmentsRef.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();
        List<Department> departments = new ArrayList<>();
    
        System.out.println(querySnapshot.size());
        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            Department department = new Department(
                    session,
                    document.getString("departmentName"),
                    document.getString("departmentCode"),
                    document.getLong("level").intValue(),
                    document.getLong("numberOfStudents").intValue()
            );
            departments.add(department);
            // departments.add(session);
        }
    
        return departments;
    }
    

    public String deleteSession(String session) throws ExecutionException, InterruptedException, Exception{

        try{
            String sessionId = getSessionId(session);

            firestore.collection("sessions").document(sessionId).delete();
        } catch (Exception e) {
            customExceptionHandler.customExceptionHandler(e);
            // customExceptionHandler(e);
          }

        return "Session "+session+" deleted successfully";
    }

    public String deleteDepartment(String sessionId,String departmentName, String departmentCode) throws ExecutionException, InterruptedException, Exception{

        try{
            String departmentId = getDepartmentId(departmentName, departmentCode);

            System.out.println(departmentId);
            String path= "sessions/"+sessionId+"/departments/";

            firestore.collection(path).document(departmentId).delete();

        } catch (Exception e) {
            customExceptionHandler.customExceptionHandler(e);
            // customExceptionHandler(e);
          }

        return "Department "+departmentName+" deleted successfully";
    }
    
}



