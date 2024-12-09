package com.example.jkpvt.Core.Cache;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

@Configuration
public class EhCache {
    @Bean
    public JCacheCacheManager cacheManager() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        javax.cache.CacheManager cacheManager = cachingProvider.getCacheManager();

        org.ehcache.config.CacheConfiguration<String, String> cacheConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(100000)
                        .offheap(50, MemoryUnit.MB))
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(10)))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(60)))
                .build();

        CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(System.getProperty("java.io.tmpdir")))
                .withCache("defaultCache", cacheConfig)
                .withCache("default-update-timestamps-region", cacheConfig)
                .withCache("queryCache", cacheConfig)
                .build(true);

        return new JCacheCacheManager(cacheManager);
    }
}