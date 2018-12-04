package com.doc.central.app.repository;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.doc.central.app.domain.Users;
@Repository
public interface UsersRepository extends CrudRepository<Users, BigInteger> {
}
