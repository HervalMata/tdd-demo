package com.garciahurtado.pillardemo;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.garciahurtado.pillardemo.model.NewspaperModel;
import com.garciahurtado.pillardemo.service.NewspaperService;

@EnableAutoConfiguration
@ComponentScan
public class Application {

	private static NewspaperService db;
	
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        Application.seedData(ctx);
        System.out.println("=== Spring Application Started ===");
    }

    /**
     * Create a few mock models to seed the database so that it will be usable
     * @param ctx
     */
	private static void seedData(ApplicationContext ctx) {
		db = ctx.getBean(NewspaperService.class);
		NewspaperModel newspaper1 = new NewspaperModel("Days of the News");
		NewspaperModel newspaper2 = new NewspaperModel("Metropolis Daily");
		NewspaperModel newspaper3 = new NewspaperModel("Financial Times");
		NewspaperModel newspaper4 = new NewspaperModel("Gossip and More");
		
		db.create(newspaper1);
		db.create(newspaper2);
		db.create(newspaper3);
		db.create(newspaper4);
		
		System.out.println("=== Seed data inserted ===");
	}
}