package com.inai.courseproject.repositories;

import com.inai.courseproject.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.fullName = :fullName and u.phone = :phone and u.email = :email")
    User findByFullNameAndPhoneAndEmail(@Param("fullName") String fullName, @Param("phone") String phone, @Param("email") String email);

    @Query("select u from User u where u.fullName = :fullName")
    User findByFullName(@Param("fullName") String fullName);
}
