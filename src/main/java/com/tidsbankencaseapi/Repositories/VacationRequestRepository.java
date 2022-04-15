package com.tidsbankencaseapi.Repositories;

import com.tidsbankencaseapi.Models.Employee;
import com.tidsbankencaseapi.Models.Status;
import com.tidsbankencaseapi.Models.VacationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Integer> {
    List<VacationRequest> getVacationRequestsByRequestOwnerOrStatus (Employee owner, Status status);
    List<VacationRequest> OrderByPeriodStartAsc();

}
