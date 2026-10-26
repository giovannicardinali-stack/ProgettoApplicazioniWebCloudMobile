package com.example.demo.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import java.time.Duration;

/**
 * classe di configurazione per la gestione della cache con redis
 * Sovrascrive la serializzazione di default di Spring Data Redis (che usa la serializzazione
 * binaria Java) sostituendola con un formato JSON gestito da Jackson.
 */

@Configuration
public class RedisSerializationConfiguration {

    //converte ogni classe in una stringa in JSon
    @Bean
    public RedisCacheConfiguration cacheConfiguration(){
        ObjectMapper objectMapper = new ObjectMapper();

        // Permette a Jackson di risalire al tipo di classe Java durante la deserializzazione
        //dice a Jackson di aggiungere nel JSON un campo speciale (chiamato @class) contenente
        // il nome completo della classe Java.
        objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );

        // prende l'oggetto Java, lo passa all'ObjectMapper per farlo diventare una
        // stringa JSON, e lo invia a Redis (e fa il percorso inverso quando legge)
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        //definisce le regole per la cache di redis
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)) // TTL 10 minuti
                .disableCachingNullValues() //non salva in memoria valori null
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(serializer)
                );
    }
}
