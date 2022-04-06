package chap02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class);

        Greeter g1 = ctx.getBean("greeter", Greeter.class);
        String msg = g1.greet("스프링");
        System.out.println(msg);

        Greeter g2 = ctx.getBean("greeter", Greeter.class);
        System.out.println("(g1 == g2) = " + (g1 == g2));

        Greeter g3 = ctx.getBean("greeter2", Greeter.class);
        msg = g3.greet("스프링");
        System.out.println(msg);

        System.out.println("(g1 == g3) = " + (g1 == g3));

        ctx.close();
    }
}
