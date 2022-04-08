package main;

import chap07.Calculator;
import chap07.RecCalculator;
import config.AppCtxWithCache;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspectWithCache {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppCtxWithCache.class);

//        RecCalculator calculator = context.getBean("calculator", RecCalculator.class);
//        long result = calculator.factorial(5);
//
//        System.out.println("calculator.factorial(5) = " + result);
//        System.out.println(calculator.getClass().getName());

        Calculator calculator = context.getBean("calculator", Calculator.class);
        calculator.factorial(7);
        calculator.factorial(7);
        calculator.factorial(5);
        calculator.factorial(5);

        context.close();
    }
}
