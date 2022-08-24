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

    // Saving data input under user's name
    public void save(String name, String payload) {
        ValueOperations<String,String> valueOp = redisTemplate.opsForValue();
        // Setting Key:name, Value:Payload in redis
        valueOp.set(name.toLowerCase(), payload);
    }

    // Retrieving data input under user's name
    public Optional<String> get(String name) {
        ValueOperations<String,String> valueOp = redisTemplate.opsForValue();
        String username = valueOp.get(name.toLowerCase());
        if (null == username) {
            return Optional.empty();
        } 
        return Optional.of(username);
    }
}
