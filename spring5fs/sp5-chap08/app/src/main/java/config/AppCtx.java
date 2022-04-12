package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.MemberDao;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
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
    public MemberDao memberDao() {
        return new MemberDao(dataSource());
    }
}
