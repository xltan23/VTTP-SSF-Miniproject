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

    // Time intensity converter (To be tested and verified)
    public Integer timeIntensityConversion(Integer duration, Integer sets) {
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

    // Repetition intensity converter (To be tested and verified)
    public Integer repIntensityConversion(Integer repetition, Integer sets) {
        // Mathematical operations in double format to allow multiplier
        double in = 0;
        if (repetition <= 20) {
            in = repetition*sets*0.7;
        } else if (repetition > 20 && repetition <= 40) {
            in = repetition*sets;
        } else {
            in = repetition*sets*1.3;
        }
        Integer intensity = (int)in;
        return intensity;
    }

    // Create list of static workouts (Duration-based)
    public List<String> getStatics() {
        List<String> staticWorkout = new LinkedList<>();
        staticWorkout.add("PLANK");
        staticWorkout.add("SIDE PLANK");
        staticWorkout.add("LEG RAISE");
        staticWorkout.add("V-MAN");
        staticWorkout.add("PUSH-UP HOLD");
        return staticWorkout;
    }
}
