package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;  // 스프링 데이터가 제공해 줌

// 스프링 데이터 JPA가 JPARepository를 받고 있으면 인터페이스를 보고 자동으로 구현체를 만들어 스프링 빈을 등록해 줌
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
	
	// select m from Member m where m.name = ?
	@Override
	Optional<Member> findByName(String name);
}