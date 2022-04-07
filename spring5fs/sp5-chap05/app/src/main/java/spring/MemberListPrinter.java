package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("listPrinter")
public class MemberListPrinter {

    private MemberDao memberDao;
    private MemberPrinter memberPrinter;

    public void printAll() {
        Collection<Member> members = memberDao.selectAll();

        members.forEach(m -> memberPrinter.print(m));
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Autowired
    //@Qualifier("summaryPrinter")
//    public void setMemberPrinter(MemberPrinter memberPrinter)
    public void setMemberPrinter(MemberSummaryPrinter memberPrinter) {
        this.memberPrinter = memberPrinter;
    }
}
