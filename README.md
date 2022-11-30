Redis cache Library for Spring Boot
-------
This library provides utilities that make it easy to integrate Redis cache into spring boot project

Feature List:
* [Cache data](#Cache-data)
* [Built-in function](#Built-in-function) 

Quick start
-------
* Just add the dependency to an existing Spring Boot project
```xml
<dependency>
    <groupId>com.atviettelsolutions</groupId>
    <artifactId>vts-kit-ms-redis-cache</artifactId>
    <version>1.0.0</version>
</dependency>
```

* Then, add the following properties to your `application-*.yml` file, we have 2 option
  * Single Node
    ```yaml
    redis:
      host: localhost #host name
      port: 6379 #port number
    ```
  * Cluster Node:
    ```yaml
    redis:
      cluster:
        nodes: #list cluster node
    
          - 172.18.0.1:7000
          - 172.18.0.1:7001
          - 172.18.0.1:7002
        maxRedirects: 1 
    ```

Usage
-------
##### Cache data
* Step 1: You must add annotation @EnableCaching
```java
@SpringBootApplication
@EnableCaching
public class RedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }
}
```
* Step 2: You must create Cache before caching:
```java
    @Autowired
    private CacheService cacheService;
    @Autowired
    Configuration<Object, Object> jcacheConfiguration;
    ...
    cacheService.createCache("books", jcacheConfiguration)
```
  
* Step 3: To caching data you should add annotation 


@Cacheable:
Annotation indicating that the result of invoking a method (or all methods in a class) can be cached.
```java
@Component
public class SimpleBookRepository implements BookRepository {
    @Override
    @Cacheable("books")
    public Book getByIsbn(String isbn) {
        return new Book(isbn, "Some book");
    }
}
```

@CacheEvict:
Annotation indicating that a method (or all methods on a class) triggers a cache evict operation.
Evict the mapping for this key from this cache if it is present.

```java
@Component
public class SimpleBookRepository implements BookRepository {
    @Override
    @CacheEvict("books")
    public Book getByIsbn(String isbn) {
        return new Book(isbn, "Some book");
    }
}
```
@CachePut:
Annotation indicating that a method (or all methods on a class) triggers a cache put operation.
If the cache previously contained a mapping for this key, the old value is replaced by the specified value.
```java
@Component
public class SimpleBookRepository implements BookRepository {
    @Override
    @CachePut(value = "books")
    public Book getByIsbn(String isbn) {
        return new Book(isbn, "Some book");
    }
}
```
Additionally, you can set expiration duration for cache:
```java
@Configuration
@EnableCaching
@EnableScheduling
public class Config  {
    public static final String CacheName = "books";
    @CacheEvict(allEntries = true, value = {CacheName})
    @Scheduled(fixedDelay = 1 * 60 * 1000 ,  initialDelay = 500)
    public void reportCacheEvict() {
        System.out.println("Flush Cache " + new Date());
    }
}

```
##### Built-in function
We are wrapped some feature:
* Step 1: Inject CacheService and Configuration to your class:
  ```java
    @Autowired
    private CacheService cacheService;
    @Autowired
    Configuration<Object, Object> jcacheConfiguration;
  ```
* Step 2: Use CacheService
  ###### Save
  ```java
      boolean test = cacheService.save(String key, Object value)
  ```
  ###### Save with expire time
    ```java
        boolean test = cacheService.saveWithExpire(String key, Object value, Long timeSecond)
    ```
  ###### Update
  ```java
      boolean test = cacheService.update(String key, Object value)
  ```
  
  ###### Set expire time
    ```java
        boolean test = cacheService.setExpire(String key, Long timeSecond)
    ```
  ###### Get cache
    ```java
        Object test = cacheService.get(String key)
    ```
  ###### Delete
  ```java
      boolean test = cacheService.delete(String key)
  ```
  ###### Create cache
  ```java
      boolean test = cacheService.createCache("books", jcacheConfiguration)
  ```
  ###### Clear cache
  ```java
      boolean test = cacheService.clearCache(String cacheName)
  ```
  ###### Delete
  ```java
      boolean test = cacheService.delete(String key)
  ```
  ###### Delete All Cache
  ```java
      boolean test = cacheService.clearAllCache()
  ```

##### Use direct RedisTemplate
* Step 1: Inject RedisTemplate to you class
```java
@Autowired
private RedisTemplate template;
```
* Step 2: Use RedisTemplate
```java
@Component
public class RedisExample implements CommandLineRunner {
    @Autowired
    private RedisTemplate template;

    @Override
    public void run(String... args) throws Exception {
        template.opsForValue().set("Test","hello world");
        System.out.println("Value of key Test: "+template.opsForValue().get("Test"));
    }
}
```
- Note: with object you must implement interface Serialize or convent to json, xml,.. to use with RedisTemplate


Build
-------
* Build with Unittest
```shell script
mvn clean install
```

* Build without Unittest
```shell script
mvn clean install -DskipTests
```

Contribute
-------
Please refer [Contributors Guide](CONTRIBUTING.md)

License
-------
This code is under the [MIT License](https://opensource.org/licenses/MIT).

See the [LICENSE](LICENSE) file for required notices and attributions.
