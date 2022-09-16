package sg.edu.nus.iss.MiniProject1.controllers;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.MiniProject1.models.Workout;
import sg.edu.nus.iss.MiniProject1.models.WorkoutSummary;
import sg.edu.nus.iss.MiniProject1.services.WorkoutService;
import sg.edu.nus.iss.MiniProject1.services.WorkoutSumService;

@Controller
@RequestMapping(path = "/workout")
public class WorkoutController {
    
    @Autowired
    private WorkoutService workSvc;

    @Autowired
    private WorkoutSumService workSumSvc;

    // Link to access user's workout page (localhost:8080/workout/{user})
    // Screen display: Access and show temporary workout list | Available types of workout to attempt
    @GetMapping("{user}") 
    public String getUserWorkout(
        @PathVariable(name = "user", required = true) String user,
        Model model) {
            List<Workout> workoutList = workSvc.retrieveWorkout(user);
            // Reversed list order to display latest list item on top
            List<Workout> reverseWorkout = new LinkedList<>();
            if (workoutList.size() != 0) {
                for (int i = workoutList.size()-1; i > -1; i--) {
                    Workout workout = workoutList.get(i);
                    reverseWorkout.add(workout);
                }
            }
            model.addAttribute("username", user.toUpperCase());
            model.addAttribute("date", (new Date()).toString());
            model.addAttribute("workoutList", reverseWorkout);
            return "workout";
    }

    // Form info post to (localhost:8080/workout/summary)
    // Save workout summary to archive list in Redis 
    @PostMapping(value = "/summary", consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String postWorkout(@RequestBody MultiValueMap<String,String> form, Model model) {
        String user = form.getFirst("user");

        // Retrieve temporary workout list to calculate total intensity
        List<Workout> workoutList = workSvc.retrieveWorkout(user);

        Integer totalIntensity = workSumSvc.totalIntensityCalculator(workoutList);
        Integer caloriesBurnt = workSumSvc.caloriesBurntCalculator(totalIntensity);
        Integer recProtein = workSumSvc.recProteinCalculator(totalIntensity);
        Integer recCarbs = workSumSvc.recCarbsCalculator(totalIntensity);

        // Create workout summary
        WorkoutSummary ws = new WorkoutSummary();
        ws.setName(form.getFirst("workoutName"));
        ws.setTime((new Date()).toString());
        ws.setTotalIntensity(totalIntensity);
        ws.setCaloriesBurnt(caloriesBurnt);
        ws.setRecProtein(recProtein);
        ws.setRecCarbs(recCarbs);

        // Save workout summary to archive list in Redis
        workSumSvc.save(user, ws);

        model.addAttribute("username", user);
        model.addAttribute("date", (new Date()).toString());
        model.addAttribute("workoutList", workoutList);
        model.addAttribute("workoutSummary", ws);
        return "summary";
    }
}
