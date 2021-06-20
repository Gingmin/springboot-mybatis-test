package com.example.demo.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
	Optional<MemberEntity> findByEmail(String userEmail);
}
