package sg.edu.nus.iss.MiniProject1.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class FoodRepository {
    
    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String,String> redisTemplate;

    // Set Redis Key: {user}-food
    // Set Redis Value: Food payload
    public void save(String name, String payload) {
        ValueOperations<String,String> valueOp = redisTemplate.opsForValue();
        String userKey = "%s-food".formatted(name.toLowerCase());
        valueOp.set(userKey, payload);
    }

    // Retrieve Box of payload (Empty or Filled)
    public Optional<String> get(String name) {
        ValueOperations<String,String> valueOp = redisTemplate.opsForValue();
        String userKey = "%s-food".formatted(name.toLowerCase());
        String username = valueOp.get(userKey);
        if (null == username) {
            // Return empty box if user does not exist
            return Optional.empty();
        } 
        // Return user's box
        return Optional.of(username);
    }
}
