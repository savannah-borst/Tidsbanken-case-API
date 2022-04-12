package com.tidsbankencaseapi.Repositories;

import com.tidsbankencaseapi.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
