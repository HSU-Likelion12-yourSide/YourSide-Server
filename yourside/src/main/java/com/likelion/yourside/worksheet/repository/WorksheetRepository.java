package com.likelion.yourside.worksheet.repository;

import com.likelion.yourside.domain.Worksheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorksheetRepository extends JpaRepository<Worksheet, Long> {
}
