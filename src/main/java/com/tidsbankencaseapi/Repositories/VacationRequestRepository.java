package com.tidsbankencaseapi.Repositories;

import com.tidsbankencaseapi.Models.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Integer> {
}
