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
import sg.edu.nus.iss.MiniProject1.services.WorkoutService;

@Controller
@RequestMapping(path = "/workout/static-repetition")
public class StaticRepetitionController {
    
    @Autowired
    private WorkoutService workSvc;

    // Link to access user's workout page (localhost:8080/workout/static-duration/{user})
    // Screen display: Access and show temporary workout list | Available workouts to attempt
    @GetMapping("{user}") 
    public String getUserStaticRepetition(
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
            List<String> staticWorkout1 = workSvc.getStaticRepetition1();
            List<String> staticWorkout2 = workSvc.getStaticRepetition2();
            List<String> staticWorkout3 = workSvc.getStaticRepetition3();
            List<String> staticWorkout4 = workSvc.getStaticRepetition4();
            model.addAttribute("username", user.toUpperCase());
            model.addAttribute("date", (new Date()).toString());
            model.addAttribute("workoutList", reverseWorkout);
            model.addAttribute("staticWorkout1", staticWorkout1);
            model.addAttribute("staticWorkout2", staticWorkout2);
            model.addAttribute("staticWorkout3", staticWorkout3);
            model.addAttribute("staticWorkout4", staticWorkout4);
            return "staticRepetition";
    }

    // Form info post to (localhost:8080/workout/static-repetition/save1)
    // Save workout to temporary workout list 
    // Return to (localhost:8080/workout/static-repetition/{user})
    @PostMapping(value = "/save1", consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String postStaticWorkout1(@RequestBody MultiValueMap<String,String> form, Model model) {
        String user = form.getFirst("user");

        Workout workout = new Workout();
        workout.setName(form.getFirst("staticName1"));
        Integer repetition = Integer.parseInt(form.getFirst("repetition"));
        Integer sets = Integer.parseInt(form.getFirst("sets"));
        workout.setDuration(0);
        workout.setRepetition(repetition);
        workout.setSets(sets);
        // Perform intensity conversion
        workout.setIntensity(workSvc.repIntensityConversion1(repetition, sets));

        // Save workout to temporary workout list
        workSvc.save(user, workout);

        String userTrim = user.replaceAll(" ", "%20");
        return "redirect:/workout/static-repetition/%s".formatted(userTrim);
    }

    // Form info post to (localhost:8080/workout/static-repetition/save2)
    // Save workout to temporary workout list 
    // Return to (localhost:8080/workout/static-repetition/{user})
    @PostMapping(value = "/save2", consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String postStaticWorkout2(@RequestBody MultiValueMap<String,String> form, Model model) {
        String user = form.getFirst("user");

        Workout workout = new Workout();
        workout.setName(form.getFirst("staticName2"));
        Integer repetition = Integer.parseInt(form.getFirst("repetition"));
        Integer sets = Integer.parseInt(form.getFirst("sets"));
        workout.setDuration(0);
        workout.setRepetition(repetition);
        workout.setSets(sets);
        // Perform intensity conversion
        workout.setIntensity(workSvc.repIntensityConversion2(repetition, sets));

        // Save workout to temporary workout list
        workSvc.save(user, workout);

        String userTrim = user.replaceAll(" ", "%20");
        return "redirect:/workout/static-repetition/%s".formatted(userTrim);
    }

    // Form info post to (localhost:8080/workout/static-repetition/save3)
    // Save workout to temporary workout list 
    // Return to (localhost:8080/workout/static-repetition/{user})
    @PostMapping(value = "/save3", consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String postStaticWorkout3(@RequestBody MultiValueMap<String,String> form, Model model) {
        String user = form.getFirst("user");

        Workout workout = new Workout();
        workout.setName(form.getFirst("staticName3"));
        Integer repetition = Integer.parseInt(form.getFirst("repetition"));
        Integer sets = Integer.parseInt(form.getFirst("sets"));
        workout.setDuration(0);
        workout.setRepetition(repetition);
        workout.setSets(sets);
        // Perform intensity conversion
        workout.setIntensity(workSvc.repIntensityConversion3(repetition, sets));

        // Save workout to temporary workout list
        workSvc.save(user, workout);

        String userTrim = user.replaceAll(" ", "%20");
        return "redirect:/workout/static-repetition/%s".formatted(userTrim);
    }

    // Form info post to (localhost:8080/workout/static-repetition/save4)
    // Save workout to temporary workout list 
    // Return to (localhost:8080/workout/static-repetition/{user})
    @PostMapping(value = "/save4", consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String postStaticWorkout4(@RequestBody MultiValueMap<String,String> form, Model model) {
        String user = form.getFirst("user");

        Workout workout = new Workout();
        workout.setName(form.getFirst("staticName4"));
        Integer repetition = Integer.parseInt(form.getFirst("repetition"));
        Integer sets = Integer.parseInt(form.getFirst("sets"));
        workout.setDuration(0);
        workout.setRepetition(repetition);
        workout.setSets(sets);
        // Perform intensity conversion
        workout.setIntensity(workSvc.repIntensityConversion4(repetition, sets));

        // Save workout to temporary workout list
        workSvc.save(user, workout);

        String userTrim = user.replaceAll(" ", "%20");
        return "redirect:/workout/static-repetition/%s".formatted(userTrim);
    }
}
