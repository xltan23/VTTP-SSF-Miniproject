package sg.edu.nus.iss.MiniProject1.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class WorkoutSumRepository {
    
    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String,String> redisTemplate;

    // Set Redis Key: {user}-workout
    // Set Redis Value: Workout Summary payload
    public void save(String name, String payload) {
        ValueOperations<String,String> valueOp = redisTemplate.opsForValue();
        String userKey = "%s-workout".formatted(name.toLowerCase());
        valueOp.set(userKey, payload);
    }

    // Retrieve Box of payload (Empty or filled)
    public Optional<String> get(String name) {
        ValueOperations<String,String> valueOp = redisTemplate.opsForValue();
        String userKey = "%s-workout".formatted(name.toLowerCase());
        String username = valueOp.get(userKey);
        if (null == username) {
            return Optional.empty();
        }
        return Optional.of(username);
    }
}
