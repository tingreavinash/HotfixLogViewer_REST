package com.avinash.InstagramChatReader;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InstagramChatApplication implements ApplicationRunner {
	public static String participant_argument;

	public static void main(String[] args) {
		SpringApplication.run(InstagramChatApplication.class, args);

	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
		/*for (String name : args.getOptionNames()){
			System.out.println("arg-" + name + "=" + args.getOptionValues(name));
			if ("participant_id".equalsIgnoreCase(name)) {
				participant_argument = args.getOptionValues(name).toString();
			}
        }*/

        boolean containsOption = args.containsOption("participant_id");
        if(containsOption) {
        	
        	participant_argument = args.getOptionValues("participant_id").get(0);
        	System.out.println("Argument value passed: "+participant_argument);
        }
        
        
	}

}