package appdevelopement.max.guessimdb250;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FireBase {

    ArrayList<Double> average;
    private Double usersAverageTries;
    FirebaseFirestore db;

    public void addTriesToFireBase(Float averageTries, String uniqeId) {

        db = FirebaseFirestore.getInstance();

        Map<String, Object> input = new HashMap<>();
        input.put("score", averageTries);

        db.collection("userScore").document(uniqeId).set(input)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("firestore", "onSuccess: succeeded");

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("firestore", "Error adding document", e);
            }
        });

    }

    public void calculateTotalTriesAverage() {

        db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("usersAverageScore").document("avg");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        usersAverageTries = (Double) document.get("averageScore");
                        Common.totalAverageTries = usersAverageTries;
                    } else {
                        Log.d("FireBase2", "No such document");
                    }
                } else {
                    Log.d("FireBase2", "get failed with ", task.getException());
                }
            }
        });

    }

    public void setUsersAverageTries(Double usersAverageTries) {
        this.usersAverageTries = usersAverageTries;
    }

    public Double getUsersAverageTries() {
        return usersAverageTries;
    }



    public float queryTotalAverage() {
        db = FirebaseFirestore.getInstance();

        average = new ArrayList<>();

        db.collection("userScore").
                get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot ds : task.getResult()) {
                                Double userAverage = (Double) ds.get("score");
                                average.add(userAverage);
                            }
/*
                            Double avg = Arrays.stream(average).sum;
                            Stream<Double> stream1 = average.stream(;
                            stream1.forEach(x -> );
*/
                            /*
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    Log.d("RESULTIS", "onComplete: " + document.get("score"));
                                    Object b = document.get("score");


                                    }

*/
                            Log.d("RESULTIS", "onComplete: " + Arrays.toString(average.toArray()));
                                // average.add(document.getData());


                          //  Log.d("RESULTISS", "onComplete: " + bajs);
                        } else {

                        }
                    }

                });

        return 2;
    }
}
