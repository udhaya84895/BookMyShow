package com.bookmyshow.demo.repositories;

import com.bookmyshow.demo.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {
}
