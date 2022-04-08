package main;

import chap07.Calculator;
import chap07.RecCalculator;
import config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspect {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppCtx.class);

        RecCalculator calculator = context.getBean("calculator", RecCalculator.class);
        long result = calculator.factorial(5);

        System.out.println("calculator.factorial(5) = " + result);
        System.out.println(calculator.getClass().getName());

        context.close();
    }
}
