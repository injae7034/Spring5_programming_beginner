package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MemberRegisterService {
    @Autowired
    private MemberDao memberDao;

    public Long register(RegisterRequest req) {
        String email = req.getEmail();
        Member member = memberDao.selectByEmail(email);

        if(member != null) {
            throw new DuplicateMemberException("duplicate email: " + email);
        }

        member = new Member(req.getEmail(), req.getPassword(),
                req.getName(), LocalDateTime.now());

        memberDao.insert(member);

        return member.getId();
    }
}
