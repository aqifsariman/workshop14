package ibf2022.ssf.workshop14.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/*
 * <<<<<<<<<< RAILWAY SET UP >>>>>>>>>>
 * 1.   RAILWAY LOGIN
 * 2.   RAILWAY INIT
 * 3.   ADD REDIS DATABASE FROM CLICKING NEW ON TOP RIGHT
 * 4.   RAILWAY UP
 * 5.   CREATE NEW VARIABLE IN RAILWAY TO SET REDISUSER, REDISHOST ETC..
 * 6.   GENERATE DOMAIN FROM SETTINGS
 */

@Configuration
public class RedisConfig {
    /*
     * <<<<<<<<<<<<<<<<<<<< CONFIG >>>>>>>>>>
     * INJECTED FROM APPLICATION.PROPERTIES
     * SETTING THE ENVIRONMENT VARIABLES
     */
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.redis.username}")
    private String redisUsername;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.database}")
    private Optional<Integer> redisDatabase;

    @Bean
    @Scope("singleton")
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort.get());

        if (!redisUsername.isEmpty() && !redisPassword.isEmpty()) {
            config.getUsername();
            config.setPassword(redisPassword);
        }
        /* */
        config.setDatabase((redisDatabase.get()));
        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(jedisFac);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        RedisSerializer<Object> objSerializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
        redisTemplate.setValueSerializer(objSerializer);
        redisTemplate.setHashValueSerializer(objSerializer);
        return redisTemplate;
    }

}
