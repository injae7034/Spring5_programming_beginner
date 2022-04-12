package spring;

public class ChangePasswordService {
    private MemberDao memberDao;

    public void changePassword(String email, String oldPassword, String newPassword) {
        Member member = memberDao.selectByEmail(email);

        if(member == null) {
            throw new MemberNotFoundException("해당 email의 멤버가 존재하지 않습니다.");
        }

        member.changePassword(oldPassword, newPassword);

        memberDao.update(member);
    }

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
}
