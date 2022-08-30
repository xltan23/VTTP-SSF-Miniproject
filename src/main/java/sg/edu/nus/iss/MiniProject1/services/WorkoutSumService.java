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
import sg.edu.nus.iss.MiniProject1.models.WorkoutSummary;
import sg.edu.nus.iss.MiniProject1.repositories.WorkoutSumRepository;

@Service
public class WorkoutSumService {

    @Autowired
    private WorkoutSumRepository workSumRepo;

    // Retrieve full workout archive from Redis
    public List<WorkoutSummary> retrieveArchive(String name) {
        Optional<String> opt = workSumRepo.get(name);
        String payload;
        System.out.printf(">>> Retrieving archive for %s\n", name.toLowerCase());

        if (opt.isEmpty()) {
            return Collections.emptyList();
        } else {
            payload = opt.get();
            System.out.printf(">>> All workout summary: %s\n", payload);
        }

        StringReader sr = new StringReader(payload);
        JsonReader jr = Json.createReader(sr);
        JsonArray archiveArray = jr.readArray();
        List<WorkoutSummary> archiveList = new LinkedList<>();
        for (int i = 0; i < archiveArray.size(); i++) {
            JsonObject archive = archiveArray.getJsonObject(i);
            archiveList.add(WorkoutSummary.createWS(archive));
        } 
        return archiveList;
    }

    // Saving workout summary in workout archive in Redis
    public void save(String name, WorkoutSummary newSummary) {
        // Retrieve workout archive
        Optional<String> opt = workSumRepo.get(name);
        List<WorkoutSummary> archiveList = new LinkedList<>();
        String payload;
        if (!opt.isEmpty()) {
            // If user's records exist, fill archiveList
            payload = opt.get();
            System.out.println(">>> Retrieving all workout archive...\n");
            StringReader sr = new StringReader(payload);
            JsonReader jr = Json.createReader(sr);
            JsonArray archiveArray = jr.readArray();
            for (int i = 0; i < archiveArray.size(); i++) {
                JsonObject archive = archiveArray.getJsonObject(i);
                archiveList.add(WorkoutSummary.createWS(archive));
            }
        } else {
            // If user's records absent (i.e. new user), start with empty archive list
        }
        archiveList.add(newSummary);
        String newPayload = listToJson(archiveList);
        System.out.printf(">>> Saving new workout archive: %s\n", newPayload);
        workSumRepo.save(name, newPayload);
    }

    // List to JsonArray method that supports up to 10 entries
    private String listToJson(List<WorkoutSummary> list) {
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

    // Calculator to sum up all intensity values in workout list
    public Integer totalIntensityCalculator(List<Workout> workoutList) {
        Integer totalIntensity = 0;
        for (int i = 0; i < workoutList.size(); i++) {
            Integer intensity = workoutList.get(i).getIntensity();
            totalIntensity += intensity;
        }
        return totalIntensity;
    }     

    // Intensity to determine calories burnt (To be tested and verified)
    public Integer caloriesBurntCalculator(Integer totalIntensity) {
        double cb = totalIntensity/10;
        Integer caloriesBurnt = (int)cb;
        return caloriesBurnt;
    }

    // Intensity to determine protein recommended (To be tested and verified)
    public Integer recProteinCalculator(Integer totalIntensity) {
        double rp = totalIntensity/18;
        Integer recProtein = (int)rp;
        return recProtein;
    }

    // Intensity to determine carbs recommended (To be tested and verified)
    public Integer recCarbsCalculator(Integer totalIntensity) {
        double rc = totalIntensity/14;
        Integer recCarbs = (int)rc;
        return recCarbs;
    }
}
