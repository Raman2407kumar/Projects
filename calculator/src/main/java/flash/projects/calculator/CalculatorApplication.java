package flash.projects.calculator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@SpringBootApplication
public class CalculatorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// This method can be used to execute code after the application has started.
		System.out.println("Calculator Application has started successfully!");
		if(args.length == 0) {
			System.out.println("No arguments provided. Please provide a calculation expression.");
		} 
		String expression = args[0];
		try {
			double result = evaluateExpression(expression);
			System.out.println("Result: " + result);
		} catch (Exception e) {
			System.err.println("Error evaluating expression: " + e.getMessage());
		}
	}

	public static double evaluateExpression(String expression)
	{
		Expression exp = new ExpressionBuilder(expression).build();
		return exp.evaluate();
	}

}
