package eed.app.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteBatch;
import com.google.firebase.cloud.FirestoreClient;

import appdev.firebase.config.CustomExceptionHandler;
import eed.app.models.DeleteStudent;
import eed.app.models.Scorer;
import eed.app.models.Student;
import eed.app.models.StudentScore;

@Service
public class StudentService {
    
    private final Firestore firestore = FirestoreClient.getFirestore();
    public CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
    
    public Student addStudents(List<String> studentList, String sessionId, String departmentName, String departmentCode, int level) throws InterruptedException, ExecutionException{
        try{
            WriteBatch batch = firestore.batch();

            String section = "nd";
            if(level==0){
                section="nd";
            }else if(level==1){
                section="hnd";
            }

            String departmentId = getDepartmentId(departmentName, departmentCode);
            String[] parts = departmentId.split("-");
            String after = parts[1];
                    
            String path = "sessions/"+sessionId+"/departments/"+departmentId+"/"+section+"/";

            for (int i = 0; i < studentList.size(); i++) {
                String matric = after + "*" + String.format("%04d", Integer.parseInt(studentList.get(i)));
                        
                HashMap<String, String> matriculation = new HashMap<String, String>();
                matriculation.put("matric", matric);

                if(!checkIfStudentExists(path, matric)){

                    batch.set(firestore.collection(path).document(matric), matriculation);

                    HashMap<String, Object> score= new HashMap<String, Object>();
                    score.put("exam", 0.0);
                    score.put("test", 0.0);
                    score.put("practical", 0.0);
                    score.put("total", 0.0);
                    score.put("grade", "F9");

                    String scorePath = path+matric+"/score/";
                    String section2="first";
                    for(int bb=0; bb<2; bb++){
                        if(bb==0){
                            section2 = "first";
                        }else{
                            section2 = "second";
                        }
                        batch.set(firestore.collection(scorePath).document(section2), score);
                    }
                }
            }

            batch.commit();
        }catch(Exception e){
            customExceptionHandler.customExceptionHandler(e);
        }
        return null;
    }

    private String getDepartmentId(String departmentName, String departmentCode) {
        return departmentName + "-" + departmentCode;
    }

    private boolean checkIfStudentExists(String path, String matric) throws InterruptedException, ExecutionException{
        
        DocumentReference docRef = firestore.collection(path).document(matric);

        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            // Document exists
            return true;
        } else {
            // Document does not exist
            return false;
        }
    }

    public List getAllStudents(String sessionId, String departmentName, String departmentCode, int level) throws InterruptedException, ExecutionException {
        String section = "nd";
        if (level == 0) {
            section = "nd";
        } else if (level == 1) {
            section = "hnd";
        }
        List studentList = new ArrayList<>();

        String departmentId = getDepartmentId(departmentName, departmentCode);
        String path = "sessions/" + sessionId + "/departments/" + departmentId + "/" + section + "/";
        ApiFuture<QuerySnapshot> querySnapshot = firestore.collection(path).get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            String matric = document.getId();
            DocumentReference scoreRef = document.getReference().collection("score").document("first");
            DocumentReference scoreRef2 = document.getReference().collection("score").document("second");

            ApiFuture<DocumentSnapshot> scoreSnapshot = scoreRef.get();
            ApiFuture<DocumentSnapshot> scoreSnapshot2 = scoreRef2.get();
            Double exam = scoreSnapshot.get().getDouble("exam");
            Double test = scoreSnapshot.get().getDouble("test");
            Double practical = scoreSnapshot.get().getDouble("practical");
            Double total = scoreSnapshot.get().getDouble("total");
            String grade = scoreSnapshot.get().getString("grade");

            Double exam2 = scoreSnapshot2.get().getDouble("exam");
            Double test2 = scoreSnapshot2.get().getDouble("test");
            Double practical2 = scoreSnapshot2.get().getDouble("practical");
            Double total2 = scoreSnapshot2.get().getDouble("total");
            String grade2 = scoreSnapshot2.get().getString("grade");
            

            Scorer score1 = new Scorer(exam, test, practical, total, grade);
            Scorer score2 = new Scorer(exam2, test2, practical2, total2, grade2);

            HashMap<String, Scorer> first = new HashMap<String, Scorer>();
            HashMap<String, Scorer> second = new HashMap<String, Scorer>();

            first.put("first", score1);
            second.put("second", score2);
            
            StudentScore studentScore = new StudentScore(matric, sessionId, departmentName, departmentCode, level, grade, first, second);

            studentList.add(studentScore);
        }

        return studentList;
    }
    

    public String deleteStudent(DeleteStudent deleteStudent) throws InterruptedException, ExecutionException{
        // try{
            
            String level="nd";
            if(deleteStudent.getLevel() == 0){
                level = "nd";
            } else if (deleteStudent.getLevel() == 1){
                level = "hnd";
            }
            
            String path = "sessions/"+deleteStudent.getSessionId()+"/departments/"+deleteStudent.getDepartmentId()+"/"+level+"/";

            firestore.collection(path).document(deleteStudent.getStudentId()).delete();
            
        //     return null;
        // } catch(Exception e){

        // }
        return deleteStudent.getStudentId();

    }

}
