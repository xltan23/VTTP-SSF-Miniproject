package sg.edu.nus.iss.MiniProject1.services;

import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.MiniProject1.models.Workout;
import sg.edu.nus.iss.MiniProject1.repositories.WorkoutRepository;

@Service
public class WorkoutService {
    
    @Autowired
    private WorkoutRepository workoutRepo;

    // Retrieve temporary workout list from session
    public List<Workout> retrieveWorkout(String name) {
        Optional<String> opt = workoutRepo.get(name);
        String payload;
        System.out.printf(">>> Retrieving session data for %s\n", name.toLowerCase());

        if (opt.isEmpty()) {
            return Collections.emptyList();
        } else {
            payload = opt.get();
            System.out.printf(">>> Session workout: %s\n", payload);
        }

        StringReader sr = new StringReader(payload);
        JsonReader jr = Json.createReader(sr);
        JsonArray workoutSess = jr.readArray();
        List<Workout> workoutList = new LinkedList<>();
        for (int i = 0; i < workoutSess.size() ;i++) {
            JsonObject workout = workoutSess.getJsonObject(i);
            workoutList.add(Workout.createW(workout));
        }
        return workoutList;
    }

    // Saving workout in temporary list for session
    public void save(String name, Workout newWorkout) {
        // Retrieve temporary workout list
        Optional<String> opt = workoutRepo.get(name);
        List<Workout> workoutList = new LinkedList<>();
        String payload;
        if (!opt.isEmpty()) {
            // If user's session exist, fill the workout list
            payload = opt.get();
            System.out.println(">>> Retrieving session data...\n");
            StringReader sr = new StringReader(payload);
            JsonReader jr = Json.createReader(sr);
            JsonArray workoutSess = jr.readArray();
            for (int i = 0; i < workoutSess.size(); i++) {
                JsonObject workout = workoutSess.getJsonObject(i);
                workoutList.add(Workout.createW(workout));
            }
        } else {
            // If user's session is new, start with empty workout list
        }
        workoutList.add(newWorkout);
        String newPayload = listToJson(workoutList);
        System.out.printf(">>> Saving new session data: %s\n", newPayload);
        workoutRepo.save(name, newPayload);
    }

    // List to JsonArray method that supports up to 10 entries
    private String listToJson(List<Workout> list) {
        int size = list.size();
        String newPayload = "";
        switch(size) {
            case 1:
                JsonArray ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 2:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 3:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 4:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .add(list.get(3).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 5:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .add(list.get(3).toJson())
                    .add(list.get(4).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 6:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .add(list.get(3).toJson())
                    .add(list.get(4).toJson())
                    .add(list.get(5).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 7:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .add(list.get(3).toJson())
                    .add(list.get(4).toJson())
                    .add(list.get(5).toJson())
                    .add(list.get(6).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 8:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .add(list.get(3).toJson())
                    .add(list.get(4).toJson())
                    .add(list.get(5).toJson())
                    .add(list.get(6).toJson())
                    .add(list.get(7).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 9:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .add(list.get(3).toJson())
                    .add(list.get(4).toJson())
                    .add(list.get(5).toJson())
                    .add(list.get(6).toJson())
                    .add(list.get(7).toJson())
                    .add(list.get(8).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 10:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .add(list.get(3).toJson())
                    .add(list.get(4).toJson())
                    .add(list.get(5).toJson())
                    .add(list.get(6).toJson())
                    .add(list.get(7).toJson())
                    .add(list.get(8).toJson())
                    .add(list.get(9).toJson())
                    .build();
                newPayload = ja.toString();
                break;
        }
        return newPayload;
    }

    // Static Duration Workout
    // Level 1 Time intensity converter (To be tested and verified)
    public Integer timeIntensityConversion1(Integer duration, Integer sets) {
        // Mathematical operations in double format to allow multiplier
        double in = 0;
        if (duration <= 45) {
            in = duration*sets*0.7;
        } else if (duration > 45 && duration <= 90) {
            in = duration*sets;
        } else {
            in = duration*sets*1.3;
        }
        Integer intensity = (int)in;
        return intensity;
    }

    // Level 1 Static duration workouts 
    public List<String> getStaticDuration1() {
        List<String> staticWorkout = new LinkedList<>();
        staticWorkout.add("PLANK");
        staticWorkout.add("SIDE PLANK");
        staticWorkout.add("LEG RAISE HOLD");
        return staticWorkout;
    }

    // Level 2 Time intensity converter (To be tested and verified)
    public Integer timeIntensityConversion2(Integer duration, Integer sets) {
        // Mathematical operations in double format to allow multiplier
        double in = 0;
        if (duration <= 30) {
            in = duration*sets*0.9;
        } else if (duration > 30 && duration <= 60) {
            in = duration*sets*1.3;
        } else {
            in = duration*sets*1.6;
        }
        Integer intensity = (int)in;
        return intensity;
    }

    // Level 2 Static duration workouts
    public List<String> getStaticDuration2() {
        List<String> staticWorkout = new LinkedList<>();
        staticWorkout.add("(W) PLANK");
        staticWorkout.add("PUSH-UP HOLD");
        staticWorkout.add("V-MAN");
        return staticWorkout;
    }

    // Level 3 Time intensity converter (To be tested and verified)
    public Integer timeIntensityConversion3(Integer duration, Integer sets) {
        // Mathematical operations in double format to allow multiplier
        double in = 0;
        if (duration <= 20) {
            in = duration*sets*0.9;
        } else if (duration > 20 && duration <= 40) {
            in = duration*sets*1.3;
        } else {
            in = duration*sets*1.6;
        }
        Integer intensity = (int)in;
        return intensity;
    }

    // Level 3 Static duration workouts
    public List<String> getStaticDuration3() {
        List<String> staticWorkout = new LinkedList<>();
        staticWorkout.add("(W) PUSH-UP HOLD");
        staticWorkout.add("PULL-UP HOLD");
        staticWorkout.add("(W) LEG RAISE HOLD");
        return staticWorkout;
    }

    // Static Repetition Workout
    // Level 1 Repetition intensity converter (To be tested and verified)
    public Integer repIntensityConversion1(Integer repetition, Integer sets) {
        // Mathematical operations in double format to allow multiplier
        double in = 0;
        if (repetition <= 40) {
            in = repetition*sets*0.7;
        } else if (repetition > 40 && repetition <= 60) {
            in = repetition*sets;
        } else {
            in = repetition*sets*1.3;
        }
        Integer intensity = (int)in;
        return intensity;
    }

    // Level 1 Static duration workouts
    public List<String> getStaticRepetition1() {
        List<String> staticWorkout = new LinkedList<>();
        staticWorkout.add("CRUNCHES");
        staticWorkout.add("LEG THRUST");
        staticWorkout.add("RUSSIAN TWIST");
        staticWorkout.add("MOUNTAIN CLIMBERS");
        return staticWorkout;
    }

    // Level 2 Repetition intensity converter (To be tested and verified)
    public Integer repIntensityConversion2(Integer repetition, Integer sets) {
        // Mathematical operations in double format to allow multiplier
        double in = 0;
        if (repetition <= 30) {
            in = repetition*sets*0.9;
        } else if (repetition > 30 && repetition <= 50) {
            in = repetition*sets*1.1;
        } else {
            in = repetition*sets*1.4;
        }
        Integer intensity = (int)in;
        return intensity;
    }

    // Level 2 Static duration workouts
    public List<String> getStaticRepetition2() {
        List<String> staticWorkout = new LinkedList<>();
        staticWorkout.add("BICYCLE CRUNCHES");
        staticWorkout.add("FLUTTER KICKS");
        staticWorkout.add("SUPERMAN BACK");
        staticWorkout.add("JUMPING JACKS");
        return staticWorkout;
    }

    // Level 3 Repetition intensity converter (To be tested and verified)
    public Integer repIntensityConversion3(Integer repetition, Integer sets) {
        // Mathematical operations in double format to allow multiplier
        double in = 0;
        if (repetition <= 25) {
            in = repetition*sets*0.9;
        } else if (repetition > 25 && repetition <= 40) {
            in = repetition*sets*1.1;
        } else {
            in = repetition*sets*1.4;
        }
        Integer intensity = (int)in;
        return intensity;
    }

    // Level 3 Static duration workouts
    public List<String> getStaticRepetition3() {
        List<String> staticWorkout = new LinkedList<>();
        staticWorkout.add("PUSH-UP");
        staticWorkout.add("SIT-UP");
        staticWorkout.add("LEG RAISE");
        staticWorkout.add("JACK KNIVES");
        return staticWorkout;
    }

    // Level 4 Repetition intensity converter (To be tested and verified)
    public Integer repIntensityConversion4(Integer repetition, Integer sets) {
        // Mathematical operations in double format to allow multiplier
        double in = 0;
        if (repetition <= 4) {
            in = repetition*sets*7;
        } else if (repetition > 4 && repetition <= 7) {
            in = repetition*sets*8.4;
        } else {
            in = repetition*sets*10.5;
        }
        Integer intensity = (int)in;
        return intensity;
    }

    // Level 4 Static duration workouts
    public List<String> getStaticRepetition4() {
        List<String> staticWorkout = new LinkedList<>();
        staticWorkout.add("PULL-UP");
        staticWorkout.add("DIPS");
        staticWorkout.add("ABS ROLLER");
        return staticWorkout;
    }

    // Sprint intensity converter (To be tested and verified)
    public Integer fastIntensityConversion(Integer distance, Integer sets) {
        // Mathematical operations in double format to allow multiplier
        double in = 0;
        if (distance <= 200) {
            in = distance*sets*1.7;
        } else if (distance > 200 && distance <= 400) {
            in = distance*sets*2;
        } else {
            in = distance*sets*2.5;
        }
        Integer intensity = (int)in;
        return intensity;       
    }

    // Uncomfortable pace intensity converter (To be tested and verified)
    public Integer moderateIntensityConversion(Integer distance, Integer sets) {
        // Mathematical operations in double format to allow multiplier
        double in = 0;
        if (distance <= 1600) {
            in = distance*sets*1.3;
        } else if (distance > 1600 && distance <= 2400) {
            in = distance*sets*1.5;
        } else {
            in = distance*sets*1.7;
        }
        Integer intensity = (int)in;
        return intensity;       
    }

    // Jog intensity converter (To be tested and verified)
    public Integer slowIntensityConversion(Integer distance) {
        // Mathematical operations in double format to allow multiplier
        double in = 0;
        if (distance <= 5000) {
            in = distance;
        } else if (distance > 5000 && distance <= 7000) {
            in = distance*1.2;
        } else { 
            in = distance*1.5;
        }
        Integer intensity = (int)in;
        return intensity;       
    }
}
