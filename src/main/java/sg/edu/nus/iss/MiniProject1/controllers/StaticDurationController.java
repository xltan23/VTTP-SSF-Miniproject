package sg.edu.nus.iss.MiniProject1.controllers;

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
import sg.edu.nus.iss.MiniProject1.services.WorkoutService;

@Controller
@RequestMapping(path = "/workout/static-duration")
public class StaticDurationController {
    
    @Autowired
    private WorkoutService workSvc;

    // Link to access user's workout page (localhost:8080/workout/static-duration/{user})
    // Screen display: Access and show temporary workout list | Available workouts to attempt
    @GetMapping("{user}") 
    public String getUserStaticDuration(
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
            List<String> staticWorkout1 = workSvc.getStaticDuration1();
            List<String> staticWorkout2 = workSvc.getStaticDuration2();
            List<String> staticWorkout3 = workSvc.getStaticDuration3();
            model.addAttribute("username", user.toUpperCase());
            model.addAttribute("workoutList", reverseWorkout);
            model.addAttribute("staticWorkout1", staticWorkout1);
            model.addAttribute("staticWorkout2", staticWorkout2);
            model.addAttribute("staticWorkout3", staticWorkout3);
            return "staticDuration";
    }

    // Form info post to (localhost:8080/workout/static-duration/save1)
    // Save workout to temporary workout list 
    // Return to (localhost:8080/workout/static-duration/{user})
    @PostMapping(value = "/save1", consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String postStaticWorkout1(@RequestBody MultiValueMap<String,String> form, Model model) {
        String user = form.getFirst("user");

        Workout workout = new Workout();
        workout.setName(form.getFirst("staticName1"));
        Integer duration = Integer.parseInt(form.getFirst("duration"));
        Integer sets = Integer.parseInt(form.getFirst("sets"));
        workout.setDuration(duration);
        workout.setRepetition(0);
        workout.setSets(sets);
        // Perform intensity conversion
        workout.setIntensity(workSvc.timeIntensityConversion1(duration, sets));

        // Save workout to temporary workout list
        workSvc.save(user, workout);

        String userTrim = user.replaceAll(" ", "%20");
        return "redirect:/workout/static-duration/%s".formatted(userTrim);
    }

    // Form info post to (localhost:8080/workout/static-duration/save2)
    // Save workout to temporary workout list 
    // Return to (localhost:8080/workout/static-duration/{user})
    @PostMapping(value = "/save2", consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String postStaticWorkout2(@RequestBody MultiValueMap<String,String> form, Model model) {
        String user = form.getFirst("user");

        Workout workout = new Workout();
        workout.setName(form.getFirst("staticName2"));
        Integer duration = Integer.parseInt(form.getFirst("duration"));
        Integer sets = Integer.parseInt(form.getFirst("sets"));
        workout.setDuration(duration);
        workout.setRepetition(0);
        workout.setSets(sets);
        // Perform intensity conversion
        workout.setIntensity(workSvc.timeIntensityConversion2(duration, sets));

        // Save workout to temporary workout list
        workSvc.save(user, workout);

        String userTrim = user.replaceAll(" ", "%20");
        return "redirect:/workout/static-duration/%s".formatted(userTrim);
    }

    // Form info post to (localhost:8080/workout/static-duration/save3)
    // Save workout to temporary workout list 
    // Return to (localhost:8080/workout/static-duration/{user})
    @PostMapping(value = "/save3", consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String postStaticWorkout3(@RequestBody MultiValueMap<String,String> form, Model model) {
        String user = form.getFirst("user");

        Workout workout = new Workout();
        workout.setName(form.getFirst("staticName3"));
        Integer duration = Integer.parseInt(form.getFirst("duration"));
        Integer sets = Integer.parseInt(form.getFirst("sets"));
        workout.setDuration(duration);
        workout.setRepetition(0);
        workout.setSets(sets);
        // Perform intensity conversion
        workout.setIntensity(workSvc.timeIntensityConversion3(duration, sets));

        // Save workout to temporary workout list
        workSvc.save(user, workout);

        String userTrim = user.replaceAll(" ", "%20");
        return "redirect:/workout/static-duration/%s".formatted(userTrim);
    }
}
