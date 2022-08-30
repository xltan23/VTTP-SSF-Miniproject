package sg.edu.nus.iss.MiniProject1.repositories;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class WorkoutRepository {

    // Set timeout duration for workout
    @Value("${workout.cache.duration}")
    private Long cacheTime;
    
    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String,String> redisTemplate;

    // Set Temporary Redis Key: {user}-temporary
    // Set Temporary Redis Value: Workout payload 
    public void save(String name, String payload) {
        ValueOperations<String,String> valueOp = redisTemplate.opsForValue();
        String userKey = "%s-temporary".formatted(name.toLowerCase());
        valueOp.set(userKey, payload, Duration.ofMinutes(cacheTime));
    }

    // Retrieve Box of payload (Empty or Filled)
    public Optional<String> get(String name) {
        ValueOperations<String,String> valueOp = redisTemplate.opsForValue();
        String userKey = "%s-temporary".formatted(name.toLowerCase());
        String username = valueOp.get(userKey);
        if (null == username) {
            return Optional.empty();
        } 
        return Optional.of(username);
    }
}
