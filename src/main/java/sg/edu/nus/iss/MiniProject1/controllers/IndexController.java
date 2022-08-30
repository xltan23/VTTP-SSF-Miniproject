package sg.edu.nus.iss.MiniProject1.controllers;

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

    // Receives username from index page (localhost:8080)
    // Map to home page (localhost:8080/home?user={user}) 
    @GetMapping
    public String getUser(@RequestParam("user") String user, Model model) {
        // Retrieve workout and food archive list
        List<WorkoutSummary> archiveList = workSumSvc.retrieveArchive(user);
        List<Food> foodList = foodSvc.retrieveFood(user);
        model.addAttribute("displayName", user.toUpperCase());
        model.addAttribute("archiveList", archiveList);
        model.addAttribute("foodArchive", foodList);
        return "home";
    } 
}
