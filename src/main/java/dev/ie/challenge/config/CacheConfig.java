package dev.ie.challenge.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.infinispan.client.hotrod.RemoteCacheManager;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class CacheConfig {

    @Inject
    RemoteCacheManager cacheManager;

    void onStart(@Observes StartupEvent ev){        
        cacheManager.administration().getOrCreateCache("developers", "example.PROTOBUF_DIST");
        cacheManager.administration().getOrCreateCache("challenges", "example.PROTOBUF_DIST");
        cacheManager.administration().getOrCreateCache("entries", "example.PROTOBUF_DIST");
    }
    
}
