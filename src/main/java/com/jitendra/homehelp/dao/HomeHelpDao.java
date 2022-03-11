package com.jitendra.homehelp.dao;

import com.jitendra.homehelp.entity.HomeHelp;
import com.jitendra.homehelp.enums.HelpType;
import com.jitendra.homehelp.repository.HomeHelpRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HomeHelpDao extends BaseDao<HomeHelpRepository> {

    public HomeHelp save(HomeHelp homeHelp);

    public Boolean delete(Long id);

    public HomeHelp getById(Long id);

    public Page<HomeHelp> getHomeHelp(String homeId);

    public Page<HomeHelp> getHomeHelp(String homeId, Pageable pageable);

    public List<HomeHelp> getByName(String homeID,String name);

    public List<HomeHelp> getNyNameLike(String homeID,String name);

    public HomeHelp getHomeHelpByNameAndTypeAndHomeId(  String homeId,String name, HelpType helpType);

}
