package com.tidsbankencaseapi.Repositories;

import com.tidsbankencaseapi.Models.IneligiblePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IneligiblePeriodRepository extends JpaRepository<IneligiblePeriod, Integer> {
}
