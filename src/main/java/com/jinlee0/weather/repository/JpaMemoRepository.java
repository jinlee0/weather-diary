package com.jinlee0.weather.repository;

import com.jinlee0.weather.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemoRepository extends JpaRepository<Memo, Integer> {
}
