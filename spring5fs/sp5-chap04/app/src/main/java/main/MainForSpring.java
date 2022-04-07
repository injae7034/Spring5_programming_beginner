package main;

import config.AppCtx;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ChangePasswordService;
import spring.DuplicateMemberException;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.RegisterRequest;
import spring.VersionPrinter;
import spring.WrongIdPasswordException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainForSpring {
    private static ApplicationContext context = null;

    public static void main(String[] args) throws IOException {
        context = new AnnotationConfigApplicationContext(AppCtx.class);


        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("명령어를 입력하세요: ");
            String command = reader.readLine();

            if(command.equals("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }

            if(command.startsWith("new ")) {
                processNewCommand(command.split(" "));
                continue;
            } else if(command.startsWith("change ")) {
                processChangeCommand(command.split(" "));
                continue;
            } else if(command.equals("list")) {
                processListCommand();
                continue;
            } else if(command.startsWith("info ")) {
                processInfoCommand(command.split(" "));
                continue;
            } else if(command.equals("version")) {
                processVersionCommand();
                continue;
            }

            printHelp();
        }
    }

    private static void processNewCommand(String[] arg) {
        if(arg.length != 5) {
            printHelp();
            return;
        }

        MemberRegisterService memberRegisterService = context.
                getBean("memberRegisterService", MemberRegisterService.class);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(arg[1]);
        registerRequest.setName(arg[2]);
        registerRequest.setPassword(arg[3]);
        registerRequest.setConfirmPassword(arg[4]);

        if(!registerRequest.isPasswordEqualToConfirmPassword()) {
            System.out.println("비밀번호가 확인 비밀번호와 일치하지 않습니다.\n");
            return;
        }

        try {
            memberRegisterService.register(registerRequest);
            System.out.println("멤버로 등록했습니다.\n");
        } catch (DuplicateMemberException e) {
            System.out.println("이미 존재하는 이메일입니다.\n");
        }
    }

    private static void processChangeCommand(String[] args) {
        if(args.length != 4) {
            printHelp();
            return;
        }

        ChangePasswordService changePasswordService = context
                .getBean("changePasswordService", ChangePasswordService.class);

        try {
            changePasswordService.changePassword(args[1], args[2], args[3]);
            System.out.println("비밀번호를 변경했습니다.\n");
        } catch (MemberNotFoundException e) {
            System.out.println("존재하지 않는 이메일입니다.\n");
        } catch (WrongIdPasswordException e) {
            System.out.println("이메일과 암호가 일치하지 않습니다.");
        }
    }

    private static void processListCommand() {
        MemberListPrinter memberListPrinter =
                context.getBean("memberListPrinter", MemberListPrinter.class);

        memberListPrinter.printAll();
    }

    private static void processInfoCommand(String[] arg) {
        if (arg.length != 2) {
            printHelp();
            return;
        }

        MemberInfoPrinter memberInfoPrinter =
                context.getBean("memberInfoPrinter", MemberInfoPrinter.class);
        memberInfoPrinter.printMemberInfo(arg[1]);
    }

    private static void processVersionCommand() {
        VersionPrinter versionPrinter =
                context.getBean("versionPrinter", VersionPrinter.class);

        versionPrinter.print();
    }

    private static void printHelp() {
        System.out.println();
        System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
        System.out.println("명령어 사용법: ");
        System.out.println("new 이메일 이름 비밀번호 확인비밀번호");
        System.out.println("change 이메일 현재비밀번호 변경비밀번호");
        System.out.println();
    }
}
