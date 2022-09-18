package sg.edu.nus.iss.MiniProject1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.MiniProject1.models.WorkoutSummary;
import sg.edu.nus.iss.MiniProject1.services.WorkoutSumService;

@RestController
@RequestMapping(path = "/user-workout", produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkoutRestController {
    
    @Autowired
    private WorkoutSumService workSumSvc;

    // localhost:8080/user-workout/{user}
    @GetMapping(value = "{user}")
    public ResponseEntity<String> getUserWorkoutSummary(@PathVariable String user) {
        List<WorkoutSummary> workoutList = workSumSvc.retrieveArchive(user);
        String payload = "";
        // If empty, build a Json Object that say the user's record not found
        if (workoutList.isEmpty()) {
            JsonObject err = Json.createObjectBuilder()
                .add("Error", "User %s's workout record not found\n".formatted(user.toLowerCase()))
                .build();
            // Convert to Json String
            payload = err.toString();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(payload);
        }
        // Convert the food list to Json string for display
        payload = workSumSvc.listToJson(workoutList);
        return ResponseEntity.ok(payload);
    }
}
