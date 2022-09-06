package sg.edu.nus.iss.MiniProject1.controllers;

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
import sg.edu.nus.iss.MiniProject1.services.WorkoutService;

@Controller
@RequestMapping(path = "/workout/running")
public class RunningController {
    
    @Autowired
    private WorkoutService workSvc;

    // Link to access user's workout page (localhost:8080/workout/running/{user})
    // Screen display: Access and show temporary workout list | Available workouts to attempt
    @GetMapping("{user}") 
    public String getUserRunning(
        @PathVariable(name = "user", required = true) String user,
        Model model) {
            List<Workout> workoutList = workSvc.retrieveWorkout(user);
            String intervals = "INTERVALS";
            String uncomfortablePace = "UNCOMFORTABLE PACE";
            String longDistanceJog = "LONG DISTANCE JOG";
            model.addAttribute("username", user.toUpperCase());
            model.addAttribute("workoutList", workoutList);
            model.addAttribute("fastrun", intervals);
            model.addAttribute("modrun", uncomfortablePace);
            model.addAttribute("slowrun", longDistanceJog);
            return "running";
    }

    // Form info post to (localhost:8080/workout/running/save1)
    // Save workout to temporary workout list 
    // Return to (localhost:8080/workout/running/{user})
    @PostMapping(value = "/save1", consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String postRunning1(@RequestBody MultiValueMap<String,String> form, Model model) {
        String user = form.getFirst("user");

        Workout workout = new Workout();
        workout.setName(form.getFirst("fastrun"));
        Integer distance = Integer.parseInt(form.getFirst("distance"));
        Integer sets = Integer.parseInt(form.getFirst("sets"));
        workout.setDuration(distance);
        workout.setRepetition(0);
        workout.setSets(sets);
        // Perform intensity conversion
        workout.setIntensity(workSvc.fastIntensityConversion(distance, sets));

        // Save workout to temporary workout list
        workSvc.save(user, workout);

        String userTrim = user.replaceAll(" ", "%20");
        return "redirect:/workout/running/%s".formatted(userTrim);
    }

    // Form info post to (localhost:8080/workout/running/save2)
    // Save workout to temporary workout list 
    // Return to (localhost:8080/workout/running/{user})
    @PostMapping(value = "/save2", consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String postRunning2(@RequestBody MultiValueMap<String,String> form, Model model) {
        String user = form.getFirst("user");

        Workout workout = new Workout();
        workout.setName(form.getFirst("modrun"));
        Integer distance = Integer.parseInt(form.getFirst("distance"));
        Integer sets = Integer.parseInt(form.getFirst("sets"));
        workout.setDuration(distance);
        workout.setRepetition(0);
        workout.setSets(sets);
        // Perform intensity conversion
        workout.setIntensity(workSvc.moderateIntensityConversion(distance, sets));

        // Save workout to temporary workout list
        workSvc.save(user, workout);

        String userTrim = user.replaceAll(" ", "%20");
        return "redirect:/workout/running/%s".formatted(userTrim);
    }

    // Form info post to (localhost:8080/workout/running/save3)
    // Save workout to temporary workout list 
    // Return to (localhost:8080/workout/running/{user})
    @PostMapping(value = "/save3", consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String postRunning3(@RequestBody MultiValueMap<String,String> form, Model model) {
        String user = form.getFirst("user");

        Workout workout = new Workout();
        workout.setName(form.getFirst("slowrun"));
        Integer distance = Integer.parseInt(form.getFirst("distance"));
        workout.setDuration(distance);
        workout.setRepetition(0);
        workout.setSets(0);
        // Perform intensity conversion
        workout.setIntensity(workSvc.slowIntensityConversion(distance));

        // Save workout to temporary workout list
        workSvc.save(user, workout);

        String userTrim = user.replaceAll(" ", "%20");
        return "redirect:/workout/running/%s".formatted(userTrim);
    }
}
