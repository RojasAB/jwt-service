package com.alonesoft.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alonesoft.jwt.model.ChangePasswordRequest;

public interface ChangePasswordRequestRepository extends JpaRepository<ChangePasswordRequest, String> {

}
