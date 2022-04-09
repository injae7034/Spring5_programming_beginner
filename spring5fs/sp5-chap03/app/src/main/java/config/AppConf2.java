package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;
import spring.VersionPrinter;

@Configuration
public class AppConf2 {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberPrinter memberPrinter;

    @Bean
    public MemberRegisterService memberRegisterService() {
        return new MemberRegisterService(memberDao);
    }

    @Bean
    public ChangePasswordService changePasswordService() {
        ChangePasswordService changePasswordService =
                new ChangePasswordService();
        changePasswordService.setMemberDao(memberDao);

        return changePasswordService;
    }

    @Bean
    public MemberListPrinter memberListPrinter() {
        return new MemberListPrinter(memberDao, memberPrinter);
    }

    @Bean
    public MemberInfoPrinter memberInfoPrinter() {
        MemberInfoPrinter memberInfoPrinter = new MemberInfoPrinter();
        //memberInfoPrinter.setMemberDao(memberDao);
        //memberInfoPrinter.setMemberPrinter(memberPrinter);

        return memberInfoPrinter;
    }

    @Bean
    public VersionPrinter versionPrinter() {
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);

        return versionPrinter;
    }
}
