	package br.com.fiap.budgetbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@Controller
@EnableCaching
@OpenAPIDefinition(
	info = @Info(
		title = "Budget Buddy",
		summary = "API do App Budget Buddy",
		description = "Um app de controle de gastos pessoais",
		version = "1.0.0",
		contact = @Contact(name = "João Carlos", email = "joao@gmail.com")
	)
)
public class BudgetbuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetbuddyApplication.class, args);
	}

	@RequestMapping
	@ResponseBody
	public String home (){
		return "Budget Buddy";
	}

}
