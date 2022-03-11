package com.jitendra.homehelp.repository;

import com.jitendra.homehelp.entity.HomeHelp;
import com.jitendra.homehelp.enums.HelpType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeHelpRepository extends PagingAndSortingRepository<HomeHelp,Long> {

    @Query("FROM HomeHelp home WHERE home.homeId = ?1 AND lower(home.name) = ?2")
    public List<HomeHelp> getByName(@Param(value = "homeId") String homeId, @Param(value = "name") String name);

    @Query("FROM HomeHelp home WHERE home.homeId = ?1 AND lower(home.name) like ?2")
    public List<HomeHelp> getNyNameLike(@Param(value = "homeId") String homeId,@Param(value = "name") String name);

    @Query("FROM HomeHelp home WHERE home.homeId = ?1")
    public Page<HomeHelp> getHomeHelpById(String homeId, Pageable pageable);

    @Query("FROM HomeHelp home WHERE home.homeId = ?1 AND lower(home.name) = ?2 AND home.helpType = ?3")
    public HomeHelp getHomeHelpByNameAndTypeAndHomeId(@Param(value = "homeId") String homeId, @Param(value = "name") String name, @Param(value = "helpType") HelpType helpType);



}
