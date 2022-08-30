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

import sg.edu.nus.iss.MiniProject1.models.Food;
import sg.edu.nus.iss.MiniProject1.services.FoodService;

@Controller
@RequestMapping(path = "/food")
public class FoodController {

    @Autowired
    private FoodService foodSvc;

    // Link to access user's food page (localhost:8080/food/{user}) [food.html]
    @GetMapping("{user}")
    public String getUserFood(
        @PathVariable(name = "user", required = true) String user,
        Model model) {
            List<Food> foodList = foodSvc.retrieveFood(user);
            model.addAttribute("username", user);
            model.addAttribute("foodList", foodList);
            return "food";
    }

    // Form info post to (localhost:8080/food/options) to retrieve data from spoonacular
    // Display outcome in (localhost:8080/food/options) [foodDisplay.html]
    @PostMapping(value = "/options", consumes="application/x-www-form-urlencoded", produces="text/html")
    public String getFood(@RequestBody MultiValueMap<String,String> form, Model model) {
        String user = form.getFirst("user");

        String minCalories = form.getFirst("minCalories");
        String minCarbs = form.getFirst("minCarbs");
        String minProtein = form.getFirst("minProtein");
        String maxFat = form.getFirst("maxFat");
        // Get food list from spoontacular
        List<Food> foodList = foodSvc.getFood(minCalories, minCarbs, minProtein, maxFat);
        model.addAttribute("username", user);
        model.addAttribute("foodList", foodList);
        return "spoonacular";
    }

    // Form info post to (localhost:8080/food/save)
    // Save food to food list
    // Return to (localhost:8080/food/{user}) 
    @PostMapping(value = "/save", consumes="application/x-www-form-urlencoded", produces="text/html")
    public String saveFood(@RequestBody MultiValueMap<String,String> form, Model model) {
        // Set the user to be saved under
        String user = form.getFirst("user");

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

        String userTrim = user.replaceAll(" ", "%20");
        return "redirect:/food/%s".formatted(userTrim);
    }
}
