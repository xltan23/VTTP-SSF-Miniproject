package sg.edu.nus.iss.MiniProject1.services;

import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.MiniProject1.models.Food;
import sg.edu.nus.iss.MiniProject1.repositories.FoodRepository;

@Service
public class FoodService {
    
    private static final String URL = "https://api.spoonacular.com/recipes/findByNutrients";

    @Value("${API_KEY}")
    private String key;

    @Autowired
    private FoodRepository foodRepo;

    // Edit the retrieve
    public List<Food> retrieveArchive(String name) {
        Optional<String> opt = foodRepo.get(name);
        String payload;
        System.out.printf(">>> Retrieving data for %s\n", name);

        if (opt.isEmpty()) {
            return Collections.emptyList();
        } else {
            payload = opt.get();
            System.out.printf(">>> Cache: %s\n", payload);
        }
        StringReader sr = new StringReader(payload);
        JsonReader jr = Json.createReader(sr);
        JsonArray archive = jr.readArray();
        List<Food> archiveList = new LinkedList<>();
        for (int i = 0; i < archiveList.size(); i++) {
            JsonObject food = archive.getJsonObject(i);
            archiveList.add(Food.create(food));
        }
        return archiveList;
    }

    public List<Food> getFood(String minCalories, String minCarbs, String minProtein, String maxFat) {
        String payload;

        try {

            System.out.println("Obtaining food by nutrients from Spoonacular");

            String url = UriComponentsBuilder.fromUriString(URL)
                .queryParam("minCalories", minCalories)
                .queryParam("minCarbs", minCarbs)
                .queryParam("minProtein", minProtein)
                .queryParam("maxFat", maxFat)
                .queryParam("apiKey", key)
                .toUriString();

            // Create GET request 
            RequestEntity<Void> req = RequestEntity.get(url).build();

            // Make the call to Spoonacular
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> resp;

            resp = restTemplate.exchange(req, String.class);
            // Get Response in terms of Json String
            payload = resp.getBody();
            System.out.printf("Payload: %s", payload);

        } catch (Exception ex) {
            System.err.printf("Error: %s\n", ex.getMessage());
            return Collections.emptyList();
        }
        // Convert payload to Json Object
        StringReader sr = new StringReader(payload);
        JsonReader jr = Json.createReader(sr);
        JsonArray foodResult = jr.readArray();
        List<Food> foodList = new LinkedList<>();
        for (int i = 0; i < foodResult.size(); i++) {
            JsonObject foodItem = foodResult.getJsonObject(i);
            foodList.add(Food.create(foodItem));
        }
        return foodList;
    }

    public void save(String name, List<Food> foodList) {
        JsonArray jaFood = Json.createArrayBuilder().build();
        for (int i = 0; i < foodList.size(); i++) {
            Food food = foodList.get(i);
            JsonObject joFood = food.toJson();
            jaFood.add(joFood);
        }
        String payload = jaFood.toString();
        foodRepo.save(name, payload);
    }
}
