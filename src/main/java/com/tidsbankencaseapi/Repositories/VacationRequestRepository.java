package com.tidsbankencaseapi.Repositories;

import com.tidsbankencaseapi.Models.Employee;
import com.tidsbankencaseapi.Models.Status;
import com.tidsbankencaseapi.Models.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Integer> {
    List<VacationRequest> getVacationRequestsByOwnerOrStatus(Employee owner, Status status);
}
