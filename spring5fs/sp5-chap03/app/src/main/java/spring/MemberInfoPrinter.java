package spring;

public class MemberInfoPrinter {
    private MemberDao memberDao;
    private MemberPrinter memberPrinter;

    public void printMemberInfo(String email) {
        Member member = memberDao.selectByEmail(email);

        if(member == null) {
            System.out.println("입력하신 이메일에 해당하는 회원 정보가 없습니다.\n");
            return;
        }

        memberPrinter.print(member);
        System.out.println();
    }

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void setMemberPrinter(MemberPrinter memberPrinter) {
        this.memberPrinter = memberPrinter;
    }
}
