package spring;

import java.time.LocalDateTime;

public class MemberRegisterService {
    private MemberDao memberDao;

    public MemberRegisterService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

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
