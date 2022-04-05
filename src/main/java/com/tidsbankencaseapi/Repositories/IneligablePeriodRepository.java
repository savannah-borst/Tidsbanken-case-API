package com.tidsbankencaseapi.Repositories;

import com.tidsbankencaseapi.Models.IneligablePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IneligablePeriodRepository extends JpaRepository<IneligablePeriod, Integer> {
}
