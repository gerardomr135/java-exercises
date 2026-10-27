package com.fireal99.roomclr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RoomClrApplication {
    private static final Logger LOG = LoggerFactory.getLogger(RoomClrApplication.class);

    @Bean
    public CommandLineRunner run() throws Exception {
        return args -> {
            LOG.info("Starting CLR application");

            for (int i = 1; i <= 100; i++) {
                if (i % 3 == 0 && i % 5 == 0) {
                    LOG.info("FizzBuzz");
                    continue;
                }
                if (i % 3 == 0) {
                    LOG.info("Fizz");
                    continue;
                }
                if (i % 5 == 0) {
                    LOG.info("Buzz");
                    continue;
                }
                LOG.info("{}", i);
            }

            LOG.info("Finishing CLR application");
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(RoomClrApplication.class, args);

    }

}
