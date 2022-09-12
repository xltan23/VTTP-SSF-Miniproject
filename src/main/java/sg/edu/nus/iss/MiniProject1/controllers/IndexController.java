package sg.edu.nus.iss.MiniProject1.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.MiniProject1.models.Food;
import sg.edu.nus.iss.MiniProject1.models.WorkoutSummary;
import sg.edu.nus.iss.MiniProject1.services.FoodService;
import sg.edu.nus.iss.MiniProject1.services.WorkoutSumService;

@Controller
@RequestMapping(path = "/home")
public class IndexController {

    @Autowired
    private FoodService foodSvc;

    @Autowired
    private WorkoutSumService workSumSvc;

    // Receives username from index page (localhost:8080) [index.html]
    // Map to home page (localhost:8080/home?user={user}) [home.html]
    @GetMapping
    public String getUser(@RequestParam("user") String user, Model model) {
        // Obtain hour of the day
        Calendar cal = Calendar.getInstance();
        // Retrieve workout and food archive list
        List<WorkoutSummary> archiveList = workSumSvc.retrieveArchive(user);
        // Reversed list order to display latest list item on top
        List<WorkoutSummary> reverseWorkout = new LinkedList<>();
        if (archiveList.size() != 0) {
            for (int i = archiveList.size()-1; i > -1; i--) {
                WorkoutSummary archive = archiveList.get(i);
                reverseWorkout.add(archive);
            }
        }
        List<Food> foodList = foodSvc.retrieveFood(user);
        // Reversed list order to display latest list item on top
        List<Food> reverseFood = new LinkedList<>();
        if (foodList.size() != 0) {
            for (int i = foodList.size()-1; i > -1; i--) {
                Food food = foodList.get(i);
                reverseFood.add(food);
            }
        }
        model.addAttribute("username", user.toUpperCase());
        model.addAttribute("hour", cal.get(Calendar.HOUR_OF_DAY));
        model.addAttribute("date", (new Date()).toString());
        model.addAttribute("archiveList", reverseWorkout);
        model.addAttribute("foodList", reverseFood);
        return "home";
    } 
}
