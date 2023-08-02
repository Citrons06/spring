package hello.hellospring.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import hello.hellospring.domain.Member;

public class JpaMemberRepository implements MemberRepository {
	
	// jpa를 사용하면 스프링 부트가 자동으로 엔터티매니저 생성, 만들어진 매니저 인젝션
	private final EntityManager em;
	
	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}

	
	@Override
	public Member save(Member member) {
		em.persist(member);				// member 영속 저장
		return member;					// JPA가 insert 쿼리 만들어서 member에 모두 집어 넣게 됨
	}

	@Override
	public Optional<Member> findById(Long id) {
		Member member = em.find(Member.class, id);	// (타입, 식별자 PK)
		return Optional.ofNullable(member);			// select 쿼리 만듦
	}

	// PK 기반이 아닌 것들은 JPQL을 작성해야 함
	@Override
	public Optional<Member> findByName(String name) {
		List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
			.setParameter("name", name)
			.getResultList();
		return result.stream().findAny();
	}

	@Override
	public List<Member> findAll() {
		/*
		 * List<Member> result = em.createQuery("select m from Member m", Member.class)
		 * 			.getResultList(); 
		 * 		return result;
		 */
		return em.createQuery("select m from Member m", Member.class)
				.getResultList();
		// m : member 객체 자체
	}

}
