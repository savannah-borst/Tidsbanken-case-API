package com.tidsbankencaseapi.Repositories;

import com.tidsbankencaseapi.Models.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Integer> {
}
