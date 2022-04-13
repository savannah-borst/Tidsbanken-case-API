package com.tidsbankencaseapi.Repositories;

import com.tidsbankencaseapi.Models.IneligiblePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IneligiblePeriodRepository extends JpaRepository<IneligiblePeriod, Integer>{
    Optional<List<IneligiblePeriod>> OrderByPeriodStartAsc();
}
