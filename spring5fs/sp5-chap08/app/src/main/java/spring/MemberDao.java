package spring;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class MemberDao {
    private JdbcTemplate jdbcTemplate;

    public MemberDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Member selectByEmail(String email) {
        /* 임의 클래스 사용
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where EMAIL = ?",
                new RowMapper<Member>() {
                    @Override
                    public Member mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        Member member = new Member(
                                rs.getString("EMAIL"),
                                rs.getString("PASSWORD"),
                                rs.getString("NAME"),
                                rs.getTimestamp("REGDATE")
                                        .toLocalDateTime());
                        member.setId(rs.getLong("ID"));

                        return member;
                    }
                }, email);
         */
        /* 람다사용
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where EMAIL = ?",
                (ResultSet rs, int rowNum) -> {
                    Member member = new Member(
                                rs.getString("EMAIL"),
                                rs.getString("PASSWORD"),
                                rs.getString("NAME"),
                                rs.getTimestamp("REGDATE")
                                        .toLocalDateTime());
                    member.setId(rs.getLong("ID"));
                    return  member;
                },
                email);
         */
        //내부클래스 미리 구현 후 사용
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where EMAIL = ?",
                new MemberRowMapper(), email);

        return results.isEmpty() ? null : results.get(0);
    }

    public List<Member> selectAll() {
        /* 람다 사용
        List<Member> results = jdbcTemplate.query("select * from MEMBER",
                (ResultSet rs, int rowNum) -> {
                        Member member = new Member(
                            rs.getString("EMAIL"),
                            rs.getString("PASSWORD"),
                            rs.getString("NAME"),
                            rs.getTimestamp("REGDATE")
                                    .toLocalDateTime());
                        member.setId(rs.getLong("ID"));
                        return member;
                });
         */
        //내부클래스 미리 구현 후 사용
        List<Member> results = jdbcTemplate.query("select * from MEMBER",
                new MemberRowMapper());
        return results;
    }

    class MemberRowMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member(
                    rs.getString("EMAIL"),
                    rs.getString("PASSWORD"),
                    rs.getString("NAME"),
                    rs.getTimestamp("REGDATE")
                            .toLocalDateTime());
            member.setId(rs.getLong("ID"));
            return member;
        }
    }

    public int count() {
        /*
        List<Integer> results = jdbcTemplate.query(
                "select count(*) from MEMBER",
                new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt(1);
                    }
                });
        return results.get(0);
         */
        return jdbcTemplate.queryForObject(
                "select count(*) from MEMBER", Integer.class);
    }

    public void update(Member member) {
        jdbcTemplate.update(
                "update MEMBER set NAME = ?, PASSWORD = ? WHERE EMAIL = ?",
                member.getName(), member.getPassword(), member.getEmail());
    }

    public void insert(final Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        /* 임의클래스 사용
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) " +
                                "values (?, ?, ?, ?)",
                        new String[]{"ID"});
                pstmt.setString(1, member.getEmail());
                pstmt.setString(2, member.getPassword());
                pstmt.setString(3, member.getName());
                pstmt.setTimestamp(4,
                        Timestamp.valueOf(member.getRegisterDateTime()));

                return pstmt;
            }
        }, keyHolder);
         */
        //람다식 사용
        jdbcTemplate.update((Connection con) -> {
            PreparedStatement pstmt = con.prepareStatement(
                    "insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) " +
                            "values (?, ?, ?, ?)",
                    new String[]{"ID"});
            pstmt.setString(1, member.getEmail());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getName());
            pstmt.setTimestamp(4,
                    Timestamp.valueOf(member.getRegisterDateTime()));

            return pstmt;
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        member.setId(keyValue.longValue());
    }
}
