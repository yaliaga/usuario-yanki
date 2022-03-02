package pe.com.usuarioyanki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.com.usuarioyanki.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accountyanki-2")
public class UserYankiWebFluxReactive {
	@Autowired
	private ReactiveRedisTemplate reactiveRedisTemplate;

	@GetMapping(value = "/{id}")
	public Mono<User> findUserById(@PathVariable("id") Long id) {
		String key = "user_" + id;
		ReactiveValueOperations<String, User> operations = reactiveRedisTemplate.opsForValue();
		Mono<User> user = operations.get(key);
		return user;
	}
	

	@GetMapping("/all")
	@ResponseBody
	public Flux<User> findAllUser() {
       	 return (Flux<User>) reactiveRedisTemplate.opsForValue();
       
             
         
	}

	

	@PostMapping
	public Mono<User> saveUser(@RequestBody User user) {
		String key = "accountYanki_" + user.getId();
		ReactiveValueOperations<String, User> operations = reactiveRedisTemplate.opsForValue();
		return operations.getAndSet(key, user);
	}

	@DeleteMapping(value = "/{id}")
	public Mono<Long> deleteUser(@PathVariable("id") Long id) {
		String key = "user_" + id;
		return reactiveRedisTemplate.delete(key);
	}
}
