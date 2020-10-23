package com.schedulerbitcoin.bitcoinscheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@EnableScheduling
@SpringBootApplication
public class BitcoinSchedulerApplication {

	private static final Logger log = LoggerFactory.getLogger(BitcoinSchedulerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BitcoinSchedulerApplication.class, args);
	}
	@Bean
	//RestTemplate is used to make HTTP Rest Calls (REST Client)
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			lastBitCoinPrice();
		};
	}
	@Scheduled(fixedRate = 2000)
	public void lastBitCoinPrice(){
		RestTemplate restTemplate = new RestTemplate();
		//Response entity takes/returns what is inside the response from the rest template
		//in this case we specify that it is an array
		ResponseEntity<LastPriceList[]> responseEntity = restTemplate.getForEntity(
				"https://api.n.exchange/en/api/v1/price/BTCLTC/latest/?format=json", LastPriceList[].class);
		log.info("Here is the Price List " + Arrays.toString(responseEntity.getBody()));
	}
}
