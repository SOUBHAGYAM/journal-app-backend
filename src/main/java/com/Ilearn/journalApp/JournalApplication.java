package com.Ilearn.journalApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {

	public static void main(String[] args) {
	    ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);
	    ConfigurableEnvironment environment = context.getEnvironment();
	    String[] profiles = environment.getActiveProfiles();
	    System.out.println(profiles.length > 0 ? profiles[0] : "No active profile (using default)");
	}

    @Bean
    public PlatformTransactionManager Add(MongoDatabaseFactory dbFactory  ) {
        return new MongoTransactionManager(dbFactory);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

//Platformtransactionmanager
//MongoTransactionManager
