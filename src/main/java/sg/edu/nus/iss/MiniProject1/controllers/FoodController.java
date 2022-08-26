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

    // Post method to post data from home.html to retrieve Food List
    // Display in food.html (localhost:8080/food/options)
    @PostMapping(consumes="application/x-www-form-urlencoded", produces="text/html")
    public String getFood(@RequestBody MultiValueMap<String,String> form, Model model) {
        String name = form.getFirst("name");
        String minCalories = form.getFirst("minCalories");
        String minCarbs = form.getFirst("minCarbs");
        String minProtein = form.getFirst("minProtein");
        String maxFat = form.getFirst("maxFat");
        // Get new food list
        List<Food> foodList = foodSvc.getFood(minCalories, minCarbs, minProtein, maxFat);
        model.addAttribute("displayName", name);
        model.addAttribute("foodList", foodList);
        return "food";
    }

    // Saving food choices from food.html
    @PostMapping(value = "/save", consumes="application/x-www-form-urlencoded", produces="text/html")
    public String saveFood(@RequestBody MultiValueMap<String,String> form, Model model) {
        // Set the user to be saved under
        String user = form.getFirst("name");

        Food food = new Food();
        food.setTime(form.getFirst("time"));
        food.setId(Integer.parseInt(form.getFirst("id")));
        food.setTitle(form.getFirst("title"));
        food.setImage(form.getFirst("image"));
        food.setImageType(form.getFirst("imageType"));
        food.setCalories(Integer.parseInt(form.getFirst("calories")));
        food.setProtein(form.getFirst("protein"));
        food.setFat(form.getFirst("fat"));
        food.setCarbs(form.getFirst("carbs"));

        // Saving food to repository
        foodSvc.save(user, food);

        // Trim the name such that spaces become + (Cristiano Ronaldo => Cristiano+Ronaldo)
        String userTrim = user.replaceAll(" ", "+");
        return "redirect:/home?user=%s".formatted(userTrim);
    }



}
