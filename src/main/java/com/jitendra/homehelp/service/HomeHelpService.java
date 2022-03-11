package com.jitendra.homehelp.service;

import com.jitendra.homehelp.entity.HomeHelp;
import com.jitendra.homehelp.enums.HelpType;
import com.jitendra.homehelp.exceptions.DuplicateHomeHelpException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HomeHelpService {

    public HomeHelp save(HomeHelp homeHelp) throws DuplicateHomeHelpException;

    public Boolean delete(Long id);

    public HomeHelp getById(Long id);

    public Page<HomeHelp> getHomeHelp(String homeId);

    public List<HomeHelp> getByName(String homeID,String name);

    public List<HomeHelp> getNyNameLike(String homeID,String name);

    public HomeHelp getHomeHelpByNameAndTypeAndHomeId(  String homeId,String name, HelpType helpType);
}
