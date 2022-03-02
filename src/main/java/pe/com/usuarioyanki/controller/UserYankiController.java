package pe.com.usuarioyanki.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.usuarioyanki.model.User;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserYankiController {

	public static final String HASH_KEY = "OperationYanki";
	
	   @Autowired
	    private RedisTemplate redisTemplate;
	   
	    @GetMapping(value = "/{id}")
	    public Mono<User> findUserById(@PathVariable("id") Long id) {
	        String key = "user_" + id;
	        ValueOperations<String, User> operations = redisTemplate.opsForValue();
	        boolean hasKey = redisTemplate.hasKey(key);
	        User user = operations.get(key);

	        if (!hasKey) {
	            return Mono.create(monoSink -> monoSink.success(null));
	        }
	        return Mono.create(monoSink -> monoSink.success(user));
	    }

	    @PostMapping
	    public Mono<User> saveuser(@RequestBody User user) {
	        String key = "user_" + user.getId();
	        ValueOperations<String, User> operations = redisTemplate.opsForValue();
	        operations.set(key, user, 60, TimeUnit.SECONDS);
	        return Mono.create(monoSink -> monoSink.success(user));
	       
	    }

	    @DeleteMapping(value = "/{id}")
	    public Mono<Long> deleteUser(@PathVariable("id") Long id) {
	        String key = "user_" + id;
	        boolean hasKey = redisTemplate.hasKey(key);
	        if (hasKey) {
	            redisTemplate.delete(key);
	        }
	        return Mono.create(monoSink -> monoSink.success(id));
	    }
	
}
