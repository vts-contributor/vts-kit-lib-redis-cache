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
      vtskit:
        redis:
          host: localhost #host name
          port: 6379 #port number
      ```
  * Cluster Node:
    ```yaml
      vtskit:
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
  
* Step 3: To caching data you should add @Cacheable
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
      boolean test= cacheService.save(String key, Object value)
  ```
  ###### Update
  ```java
      boolean test= cacheService.update(String key, Object value)
  ```
  ###### Delete
  ```java
      boolean test= cacheService.delete(String key)
  ```
  ###### Create cache
  ```java
      boolean test= cacheService.createCache("books", jcacheConfiguration)
  ```
  ###### Clear cache
  ```java
      boolean test= cacheService.clearCache(String cacheName)
  ```
  ###### Delete
  ```java
      boolean test= cacheService.delete(String key)
  ```
  ###### Delete All Cache
  ```java
      boolean test= cacheService.clearAllCache()
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
