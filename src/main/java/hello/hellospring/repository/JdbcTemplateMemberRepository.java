package hello.hellospring.repository;

import java.util.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import hello.hellospring.domain.Member;

public class JdbcTemplateMemberRepository implements MemberRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired  // 생성자 하나일 경우 스프링 빈에 등록되므로 생략 가능
	public JdbcTemplateMemberRepository(DataSource dataSource) {  // 스프링이 dataSource 자동으로 injection
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Member save(Member member) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");  // 쿼리 생략 가능하게 해 줌
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", member.getName());
		
		Number key = jdbcInsert.executeAndReturnKey(new
				MapSqlParameterSource(parameters));
		member.setId(key.longValue());
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		List<Member> result = jdbcTemplate
				.query("select * from member where id = ?", memberRowMapper(), id);  // 결과를 RowMapper로 매핑
		return result.stream().findAny();
	}

	@Override
	public Optional<Member> findByName(String name) {
		List<Member> result = jdbcTemplate
				.query("select * from member where name = ?", memberRowMapper(), name);
		return result.stream().findAny();
	}

	@Override
	public List<Member> findAll() {
		return jdbcTemplate.query("select * from member", memberRowMapper());
	}
	
	// 결과물을 RowMapper로 매핑
	private RowMapper<Member> memberRowMapper() {
		/*
		 * return new RowMapper<Member>() {
		 *	@Override
		 *	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		 */
		   return (rs, rowNum) -> {
				Member member = new Member();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				return member;
			};
		}
	}
