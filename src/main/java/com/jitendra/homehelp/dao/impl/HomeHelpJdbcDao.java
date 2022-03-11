package com.jitendra.homehelp.dao.impl;

import com.jitendra.homehelp.entity.HomeHelp;
import com.jitendra.homehelp.enums.HelpType;
import com.jitendra.homehelp.repository.HomeHelpRepository;
import com.jitendra.homehelp.dao.HomeHelpDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeHelpJdbcDao implements HomeHelpDao {

    private static final Logger logger =   LogManager.getLogger(HomeHelpJdbcDao.class);


    @Autowired
    private  HomeHelpRepository homeHelpRepository;

    @Override
    public HomeHelpRepository getRepository() {
        return homeHelpRepository;
    }

    @Override
    public HomeHelp save(HomeHelp homeHelp) {
        if (logger.isDebugEnabled())
            logger.debug(String.format("Saving Home Help : HomeHelp {}", homeHelp.toJson()));
        return homeHelpRepository.save(homeHelp);
    }

    @Override
    public Boolean delete(Long id) {
        if (logger.isDebugEnabled())
            logger.debug(String.format("Deleting Home Help : HomeHelp {}", id));
        try {
            homeHelpRepository.deleteById(id);
            return Boolean.TRUE;
        }catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public HomeHelp getById(Long id) {
        return homeHelpRepository.findById(id).orElse(null);
    }

    @Override
    public Page<HomeHelp> getHomeHelp(String id) {
        Pageable pageable = Pageable.ofSize(10);
        return homeHelpRepository.getHomeHelpById(id,pageable);
    }

    @Override
    public Page<HomeHelp> getHomeHelp(String homeId, Pageable pageable) {
        return homeHelpRepository.getHomeHelpById(homeId,pageable);
    }

    @Override
    public List<HomeHelp> getByName(String homeId,String name) {
        return homeHelpRepository.getByName(homeId,name);
    }

    @Override
    public List<HomeHelp> getNyNameLike(String homeId,String name) {
        return homeHelpRepository.getNyNameLike(homeId,"%"+name+"%");
    }

    @Override
    public HomeHelp getHomeHelpByNameAndTypeAndHomeId(String homeId, String name, HelpType helpType) {
        return homeHelpRepository.getHomeHelpByNameAndTypeAndHomeId(homeId,StringUtils.lowerCase( name),  helpType);
    }
}
