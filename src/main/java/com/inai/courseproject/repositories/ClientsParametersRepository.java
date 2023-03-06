package com.inai.courseproject.repositories;

import com.inai.courseproject.models.user.ClientsParameters;
import com.inai.courseproject.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClientsParametersRepository extends JpaRepository<ClientsParameters, Integer> {

    @Query("select p from ClientsParameters p where p.setDate = :setDate and p.client = :client")
    Optional<ClientsParameters> findBySetDateAndClient(@Param("setDate") LocalDate setDate, @Param("client") User client);

    @Query("select p from ClientsParameters p where p.setDate = :setDate and p.client in :clients")
    List<ClientsParameters> findAllBySetDateAndAndClientIn(@Param("setDate") LocalDate setDate,
                                                           @Param("clients") List<User> clients);

    Optional<ClientsParameters> findFirstByClient(User client);

    List<ClientsParameters> findAllByClientOrderBySetDateDesc(User client);
}
