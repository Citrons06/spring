package hello.hellospring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

@Transactional
public class MemberService {
	
	private MemberRepository memberRepository;
	
//	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	/**
	 * 회원가입
	 */
	public Long join(Member member) {
		
//		long start = System.currentTimeMillis();
		
//		try {
			validateDuplicateMember(member);  // 중복 회원 검증
			memberRepository.save(member);
			return member.getId();
//		} finally {
//			long finish = System.currentTimeMillis();
//			long timeMs = finish - start;
//			System.out.println("join = " + timeMs + "ms");
//		}
		
		// 같은 이름의 중복 회원 금지 (1)
//		Optional<Member> result = memberRepository.findByName(member.getName());
//		result.ifPresent(m -> {    									 // 멤버에 값이 있으면
//			throw new IllegalStateException("이미 존재하는 회원입니다.");   // 로직 동작 (Optional로 인해 가능)
//		});
		
		// 같은 이름의 중복 회원 금지 (2) - Optional 바로 반환하지 않음
//		validateDuplicateMember(member);  // 중복 회원 검증
//		memberRepository.save(member);
//		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		memberRepository.findByName(member.getName())
			.ifPresent(m -> {
				throw new IllegalStateException("이미 존재하는 회원입니다.");
			});
	}
	
	/**
	 * 전체 회원 조회
	 */
	public List<Member> findMembers() {
		
//		long start = System.currentTimeMillis();
		
//		try {
			return memberRepository.findAll();
//		} finally {
//			long finish = System.currentTimeMillis();
//			long timeMs = finish - start;
//			System.out.println("findMembers " + timeMs + "ms");
//		}
	}
	
	public Optional<Member> findOne(Long memberId) {
		return memberRepository.findById(memberId);
	}
}
