package main;

import chap07.Calculator;
import config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspect {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppCtx.class);

        Calculator calculator = context.getBean("calculator", Calculator.class);
        long result = calculator.factorial(5);

        System.out.println("calculator.factorial(5) = " + result);
        System.out.println(calculator.getClass().getName());

        context.close();
    }
}
