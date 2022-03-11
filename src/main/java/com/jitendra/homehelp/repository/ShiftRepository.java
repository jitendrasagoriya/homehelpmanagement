package com.jitendra.homehelp.repository;

import com.jitendra.homehelp.entity.Shift;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftRepository extends PagingAndSortingRepository<Shift,Long> {

    @Query(value = "SELECT * FROM shift s WHERE s.homehelp_id = :id", nativeQuery = true)
    Page<Shift> getByHomeHelpId(@Param(value = "id") Long id, Pageable pageable);

    @Query(value = "SELECT * FROM shift s WHERE s.homehelp_id = :id",nativeQuery = true)
    List<Shift> getByHomeHelpId(@Param(value = "id") Long id);


}
