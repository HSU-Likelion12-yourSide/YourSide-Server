package com.likelion.yourside.worksheet.repository;

import com.likelion.yourside.domain.Worksheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorksheetRepository extends JpaRepository<Worksheet, Long> {
    @Query("select w from Worksheet w where w.isOpen = true ")
    List<Worksheet> findAllbyIsOpen();
}
