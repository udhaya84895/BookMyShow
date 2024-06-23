package com.bookmyshow.demo.repositories;

import com.bookmyshow.demo.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
}
