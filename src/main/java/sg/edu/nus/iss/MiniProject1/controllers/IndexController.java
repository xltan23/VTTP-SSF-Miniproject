package sg.edu.nus.iss.MiniProject1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.MiniProject1.models.Food;
import sg.edu.nus.iss.MiniProject1.services.FoodService;

@Controller
@RequestMapping(path = "/home")
public class IndexController {

    @Autowired
    private FoodService foodSvc;

    // index.html performs GetMapping for Username input
    // Directed to localhost:8080/home?user={user}
    @GetMapping
    public String getUser(@RequestParam("user") String user, Model model) {
        List<Food> foodArchive = foodSvc.retrieveArchive(user);
        model.addAttribute("displayName", user);
        model.addAttribute("foodArchive", foodArchive);
        return "home";
    } 
}
