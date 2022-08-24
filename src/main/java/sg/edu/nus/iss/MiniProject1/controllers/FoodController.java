package sg.edu.nus.iss.MiniProject1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.MiniProject1.models.Food;
import sg.edu.nus.iss.MiniProject1.services.FoodService;

@Controller
@RequestMapping(path = "/food")
public class FoodController {

    @Autowired
    private FoodService foodSvc;

    @PostMapping(value = "/options", consumes="application/x-www-form-urlencoded", produces="text/html")
    public String getFood(@RequestBody MultiValueMap<String,String> form, Model model) {
        String name = form.getFirst("name");
        String minCalories = form.getFirst("minCalories");
        String minCarbs = form.getFirst("minCarbs");
        String minProtein = form.getFirst("minProtein");
        String maxFat = form.getFirst("maxFat");
        // Get new food list
        List<Food> foodList = foodSvc.getFood(minCalories, minCarbs, minProtein, maxFat);
        // Retrieve list of food for the user from repository
        // List<Food> foodArchive = foodSvc.retrieveArchive(name);
        // foodArchive.addAll(foodList);
        foodSvc.save(name, foodList);
        model.addAttribute("displayName", name);
        model.addAttribute("foodList", foodList);
        return "food";
    }

    

}
