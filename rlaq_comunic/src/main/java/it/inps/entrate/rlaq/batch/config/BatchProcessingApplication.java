package it.inps.entrate.rlaq.batch.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = "it.inps.entrate.rlaq.batch")
@PropertySource("classpath:config.properties")
public class BatchProcessingApplication {

  public static void main(String[] args) {
    System.exit(SpringApplication.exit(SpringApplication.run(BatchProcessingApplication.class, args)));
  }
  
}