package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
@EnableTransactionManagement
public class AppCtx {
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost/spring5fs?serverTimezone=Asia/Seoul&characterEncoding=utf8");
        ds.setUsername("spring5");
        ds.setPassword("spring5");
//        ds.setInitialSize(2);
//        ds.setMaxActive(10);
        ds.setTestWhileIdle(true);//유휴 커넥션 검사
        ds.setMinEvictableIdleTimeMillis(1000 * 60 * 3);//최소 유휴 시간 3분
        ds.setTimeBetweenEvictionRunsMillis(1000 * 10);//10초 주기

//        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306" +
//                "/VisitingCardBinder?serverTimezone=Asia/Seoul", "root", "1q2w3e");

        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());

        return tm;
    }

    @Bean
    public MemberDao memberDao() {
        return new MemberDao(dataSource());
    }

    @Bean
    public MemberRegisterService memberRegisterService() {
        return new MemberRegisterService(memberDao());
    }

    @Bean
    public ChangePasswordService changePasswordService() {
        ChangePasswordService changePasswordService = new ChangePasswordService();
        changePasswordService.setMemberDao(memberDao());

        return changePasswordService;
    }

    @Bean
    public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }

    @Bean
    public MemberListPrinter memberListPrinter() {
        return new MemberListPrinter(memberDao(), memberPrinter());
    }

    @Bean
    public MemberInfoPrinter memberInfoPrinter() {
        MemberInfoPrinter memberInfoPrinter = new MemberInfoPrinter();
        memberInfoPrinter.setMemberDao(memberDao());
        memberInfoPrinter.setMemberPrinter(memberPrinter());

        return memberInfoPrinter;
    }
}
