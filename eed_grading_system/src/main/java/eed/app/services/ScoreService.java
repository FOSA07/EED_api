package eed.app.services;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import appdev.firebase.config.CustomExceptionHandler;
import eed.app.models.Score;
import eed.app.models.ScoreMap;

@Service
public class ScoreService {

    private final Firestore firestore = FirestoreClient.getFirestore();
    public CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();

    public Score addStudents(Score score) throws InterruptedException, ExecutionException{
        try{        
            String section = "nd";
            if(score.getLevel()==0){
                section="nd";
            }else if(score.getLevel()==1){
                section="hnd";
            }

            String departmentId = getDepartmentId(score.getDepartmentName(), score.getDepartmentCode());
            // String[] parts = departmentId.split("-");
            // String after = parts[1];
            String studentId = score.getStudentId().replaceAll("/", "*");
            String path = "sessions/"+score.getSessionId()+"/departments/"+departmentId+"/"+section+"/"+studentId+"/score/";

            double exam = score.getScoreMap().get("exam");
            double test = score.getScoreMap().get("test");
            double practical = score.getScoreMap().get("practical");

            double total = exam+test+practical;
            String grade = "F9";
            if(total < 41.0){
                grade = "F9";
            }else if(total < 46.0){
                grade = "E8";
            }else if(total < 51.0){
                grade = "D7";
            }else if(total < 61.0){
                grade = "C3";
            }else if(total < 81.0){
                grade = "B2";
            }else if(total < 101.0){
                grade = "A1";
            }

            HashMap<String, Object> scores= new HashMap<String, Object>();
            scores.put("exam", exam);
            scores.put("test", test);
            scores.put("practical", practical);
            scores.put("total", total);
            scores.put("grade", grade);

            firestore.collection(path).document(score.getSection()).update(scores);

            ScoreMap scoreMap = new ScoreMap(exam, test, practical);

            // return scoreMap;
        }catch(Exception e){
            customExceptionHandler.customExceptionHandler(e);
        }

        return score;
    }
    
    private String getDepartmentId(String departmentName, String departmentCode) {
        return departmentName + "-" + departmentCode;
    }

}
