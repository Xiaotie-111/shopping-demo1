package edu.ngd.eurke.shoppingdemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.ngd.eurke.shoppingdemo1", "edu.ngd.platform"})
public class ShoppingDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingDemo1Application.class, args);
	}

}
