package com.fis.crm.config;

import java.time.Duration;

import com.fis.crm.domain.CampaignResourceDetail;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

  private GitProperties gitProperties;
  private BuildProperties buildProperties;
  private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

  public CacheConfiguration(JHipsterProperties jHipsterProperties) {
    JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

    jcacheConfiguration =
      Eh107Configuration.fromEhcacheCacheConfiguration(
        CacheConfigurationBuilder
          .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
          .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
          .build()
      );
  }

  @Bean
  public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
    return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
  }

  @Bean
  public JCacheManagerCustomizer cacheManagerCustomizer() {
    return cm -> {
      createCache(cm, com.fis.crm.repository.UserRepository.USERS_BY_LOGIN_CACHE);
      createCache(cm, com.fis.crm.repository.UserRepository.USERS_BY_EMAIL_CACHE);
      createCache(cm, com.fis.crm.domain.User.class.getName());
      createCache(cm, com.fis.crm.domain.Authority.class.getName());
      createCache(cm, com.fis.crm.domain.User.class.getName() + ".authorities");
      createCache(cm, com.fis.crm.domain.ConfigSchedule.class.getName());
      createCache(cm, com.fis.crm.domain.Ticket.class.getName());
      createCache(cm, com.fis.crm.domain.ProcessTicketEntity.class.getName());
      createCache(cm, com.fis.crm.domain.ApDomain.class.getName());
      createCache(cm, com.fis.crm.domain.BussinessType.class.getName());
      createCache(cm, com.fis.crm.domain.Campaign.class.getName());
      createCache(cm, com.fis.crm.domain.Notification.class.getName());
      createCache(cm, com.fis.crm.domain.Document.class.getName());
      createCache(cm, com.fis.crm.domain.DocumentPost.class.getName());
      createCache(cm, com.fis.crm.domain.DocumentPostAttachment.class.getName());
      createCache(cm, com.fis.crm.domain.DocumentPostBookmarks.class.getName());
      createCache(cm, com.fis.crm.domain.DocumentPostView.class.getName());
      createCache(cm, CampaignResourceDetail.class.getName());
      createCache(cm, com.fis.crm.domain.CampaignSmsBatch.class.getName());
    };
  }

  private void createCache(javax.cache.CacheManager cm, String cacheName) {
    javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
    if (cache != null) {
      cache.clear();
    } else {
      cm.createCache(cacheName, jcacheConfiguration);
    }
  }

  @Autowired(required = false)
  public void setGitProperties(GitProperties gitProperties) {
    this.gitProperties = gitProperties;
  }

  @Autowired(required = false)
  public void setBuildProperties(BuildProperties buildProperties) {
    this.buildProperties = buildProperties;
  }

  @Bean
  public KeyGenerator keyGenerator() {
    return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
  }
}
