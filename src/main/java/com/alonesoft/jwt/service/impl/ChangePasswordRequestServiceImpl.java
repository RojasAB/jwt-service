package com.alonesoft.jwt.service.impl;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alonesoft.jwt.constants.ChangePasswordConstants;
import com.alonesoft.jwt.dto.ChangePasswordRequestDTO;
import com.alonesoft.jwt.model.ChangePasswordRequest;
import com.alonesoft.jwt.model.User;
import com.alonesoft.jwt.repository.ChangePasswordRequestRepository;
import com.alonesoft.jwt.service.ChangePasswordRequestService;

@Service
public class ChangePasswordRequestServiceImpl implements ChangePasswordRequestService {
	
	private ChangePasswordRequestRepository changePasswordRequestRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public ChangePasswordRequestServiceImpl(
			ChangePasswordRequestRepository changePasswordRequestRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.changePasswordRequestRepository = changePasswordRequestRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public String generateHash(User user) {
		RandomStringGenerator randomStringGenerator =
		        new RandomStringGenerator.Builder()
		                .withinRange(ChangePasswordConstants.ZERO, ChangePasswordConstants.Z)
		                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
		                .build();
		String generated = randomStringGenerator.generate(ChangePasswordConstants.TOTAL_KEY_LENGTH);		
		ChangePasswordRequestDTO changePasswordRequestDTO = new ChangePasswordRequestDTO(generated, user);
		changePasswordRequestRepository.save(changePasswordRequestDTO.toChangePasswordRequest());
		return generated;
	}
	
	@Override
	public Optional<Long> check(String code) {
		ChangePasswordRequest changePasswordRequest = changePasswordRequestRepository.findById(code).orElse(null);
		if(Objects.isNull(changePasswordRequest)) {
			return Optional.empty();
		}
		OffsetDateTime expirationTime = changePasswordRequest.getExpirationTime();
		OffsetDateTime currentTime = OffsetDateTime.now(ZoneOffset.UTC);
		User user = changePasswordRequest.getUser();
		if(currentTime.isBefore(expirationTime) && Objects.nonNull(user)) {
 
		}
		return Optional.empty();
	}
	
	@Override
	public String getPasswordEncripted(String password) {
		return bCryptPasswordEncoder.encode(password);
	}
}
