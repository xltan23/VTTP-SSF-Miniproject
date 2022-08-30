package sg.edu.nus.iss.MiniProject1.controllers;

import java.util.Date;
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
    // Screen display: Access and show temporary workout list | Available workouts to attempt
    @GetMapping("{user}") 
    public String getUserWorkout(
        @PathVariable(name = "user", required = true) String user,
        Model model) {
            List<Workout> workoutList = workSvc.retrieveWorkout(user);
            List<String> staticWorkout = workSvc.getStatics();
            model.addAttribute("username", user.toUpperCase());
            model.addAttribute("workoutList", workoutList);
            model.addAttribute("staticWorkout", staticWorkout);
            return "workout";
    }

    // Form info post to (localhost:8080/workout/savestatic)
    // Save workout to temporary workout list 
    // Return to (localhost:8080/workout/{user})
    @PostMapping(value = "/savestatic", consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String postStaticWorkout(@RequestBody MultiValueMap<String,String> form, Model model) {
        String user = form.getFirst("user");

        Workout workout = new Workout();
        workout.setName(form.getFirst("staticName"));
        Integer duration = Integer.parseInt(form.getFirst("duration"));
        Integer sets = Integer.parseInt(form.getFirst("sets"));
        workout.setDuration(duration);
        workout.setRepetition(0);
        workout.setSets(sets);
        // Perform intensity conversion
        workout.setIntensity(workSvc.timeIntensityConversion(duration, sets));

        // Save workout to temporary workout list
        workSvc.save(user, workout);

        String userTrim = user.replaceAll(" ", "%20");
        return "redirect:/workout/%s".formatted(userTrim);
    }

    // Form info post to (localhost:8080/workout/savedynamic)
    // Save workout to temporary workout list 
    // Return to (localhost:8080/workout/{user})
    @PostMapping(value = "/savedynamic", consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String postDynamicWorkout(@RequestBody MultiValueMap<String,String> form, Model model) {
        String user = form.getFirst("user");

        Workout workout = new Workout();
        workout.setName(form.getFirst("name"));
        Integer repetition = Integer.parseInt(form.getFirst("repetition"));
        Integer sets = Integer.parseInt(form.getFirst("sets"));
        workout.setDuration(0);
        workout.setRepetition(repetition);
        workout.setSets(sets);
        // Perform intensity conversion
        workout.setIntensity(workSvc.repIntensityConversion(repetition, sets));

        // Save workout to temporary workout list
        workSvc.save(user, workout);

        String userTrim = user.replaceAll(" ", "%20");
        return "redirect:/workout/%s".formatted(userTrim);
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
        model.addAttribute("workoutList", workoutList);
        model.addAttribute("workoutSummary", ws);
        return "summary";
    }
}
