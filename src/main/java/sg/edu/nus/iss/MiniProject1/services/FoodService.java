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
    
    // Setting base link
    private static final String URL = "https://api.spoonacular.com/recipes/findByNutrients";

    // API key for Spoontacular
    @Value("${API_KEY}")
    private String key;

    @Autowired
    private FoodRepository foodRepo;

    // Retrieve full food archive from Redis
    public List<Food> retrieveFood(String name) {
        Optional<String> opt = foodRepo.get(name);
        String payload;
        System.out.printf(">>> Retrieving food archive for %s\n", name.toLowerCase());

        if (opt.isEmpty()) {
            return Collections.emptyList();
        } else {
            payload = opt.get();
            System.out.printf(">>> Food archive: %s\n", payload);
        }

        StringReader sr = new StringReader(payload);
        JsonReader jr = Json.createReader(sr);
        JsonArray foodArray = jr.readArray();
        List<Food> foodList = new LinkedList<>();
        for (int i = 0; i < foodArray.size(); i++) {
            JsonObject food = foodArray.getJsonObject(i);
            foodList.add(Food.createFR(food));
        }
        return foodList;
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
            // Get Response in terms of JsonString
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
            // Upon creation: Date and Time set
            foodList.add(Food.createF(foodItem));
        }
        return foodList;
    }

    // Saving food in food archive in Redis
    public void save(String name, Food newFood) {
        // Retrieve food archive
        Optional<String> opt = foodRepo.get(name);
        List<Food> foodList = new LinkedList<>();
        String payload;
        if (!opt.isEmpty()) {
            // If user's records exist, fill foodList
            payload = opt.get();
            System.out.println(">>> Retrieving all food archive...\n");
            StringReader sr = new StringReader(payload);
            JsonReader jr = Json.createReader(sr);
            JsonArray foodArray = jr.readArray();
            for (int i = 0; i < foodArray.size(); i++) {
                JsonObject food = foodArray.getJsonObject(i);
                foodList.add(Food.createFR(food));
            }
        } else {
            // If user's records absent (i.e. new user), start with empty food list
        }
        foodList.add(newFood);
        // Converting list into JsonArray and into JsonString
        String newPayload = listToJson(foodList);
        System.out.printf(">>> Saving new food archive: %s\n", newPayload);
        foodRepo.save(name, newPayload);
    }

    // List to JsonArray method that supports up to 10 entries
    public String listToJson(List<Food> list) {
        int size = list.size();
        String newPayload = "";
        switch(size) {
            case 1:
                JsonArray ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 2:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 3:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 4:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .add(list.get(3).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 5:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .add(list.get(3).toJson())
                    .add(list.get(4).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 6:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .add(list.get(3).toJson())
                    .add(list.get(4).toJson())
                    .add(list.get(5).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 7:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .add(list.get(3).toJson())
                    .add(list.get(4).toJson())
                    .add(list.get(5).toJson())
                    .add(list.get(6).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 8:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .add(list.get(3).toJson())
                    .add(list.get(4).toJson())
                    .add(list.get(5).toJson())
                    .add(list.get(6).toJson())
                    .add(list.get(7).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 9:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .add(list.get(3).toJson())
                    .add(list.get(4).toJson())
                    .add(list.get(5).toJson())
                    .add(list.get(6).toJson())
                    .add(list.get(7).toJson())
                    .add(list.get(8).toJson())
                    .build();
                newPayload = ja.toString();
                break;
            case 10:
                ja = Json.createArrayBuilder()
                    .add(list.get(0).toJson())
                    .add(list.get(1).toJson())
                    .add(list.get(2).toJson())
                    .add(list.get(3).toJson())
                    .add(list.get(4).toJson())
                    .add(list.get(5).toJson())
                    .add(list.get(6).toJson())
                    .add(list.get(7).toJson())
                    .add(list.get(8).toJson())
                    .add(list.get(9).toJson())
                    .build();
                newPayload = ja.toString();
                break;
        }
        return newPayload;
    }

    public void deleteFood(String name, Food deleteFood) {
        // Retrieve food archive
        Optional<String> opt = foodRepo.get(name);
        List<Food> foodList = new LinkedList<>();
        String payload;
        if (!opt.isEmpty()) {
            // If user's records exist, fill foodList
            payload = opt.get();
            System.out.println(">>> Retrieving all food archive...\n");
            StringReader sr = new StringReader(payload);
            JsonReader jr = Json.createReader(sr);
            JsonArray foodArray = jr.readArray();
            for (int i = 0; i < foodArray.size(); i++) {
                JsonObject food = foodArray.getJsonObject(i);
                foodList.add(Food.createFR(food));
            }
        } else {
            // If user's records absent (i.e. new user), start with empty food list
        }
        // Search food list to find the food saved at particular time
        Food oldFood = foodList.stream().filter(o -> o.getTime().equals(deleteFood.getTime())).findAny().orElse(null);
        // Remove that food from list
        foodList.remove(oldFood);
        // Create empty JSON Array
        String newPayload = "[]";
        if (foodList.size() > 0) {
            // Converting list into JsonArray and into JsonString
            newPayload = listToJson(foodList);
            System.out.printf(">>> Saving new food archive: %s\n", newPayload);
        } else {
            System.out.println(">>> The food archive is now empty.");
        }
        foodRepo.save(name, newPayload);
    }
}
