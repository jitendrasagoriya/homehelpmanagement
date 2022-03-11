package com.jitendra.homehelp.service.impl;

import com.jitendra.homehelp.dao.HomeHelpDao;
import com.jitendra.homehelp.dao.impl.HomeHelpJdbcDao;
import com.jitendra.homehelp.entity.HomeHelp;
import com.jitendra.homehelp.enums.HelpType;
import com.jitendra.homehelp.exceptions.DuplicateHomeHelpException;
import com.jitendra.homehelp.service.HomeHelpService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeHelpServiceImpl implements HomeHelpService {

    private static final Logger logger =   LogManager.getLogger(HomeHelpJdbcDao.class);

    @Autowired
    private HomeHelpDao homeHelpDao;

    @Override
    public HomeHelp save(HomeHelp homeHelp) throws DuplicateHomeHelpException {
        HomeHelp homeHelp1 = homeHelpDao.getHomeHelpByNameAndTypeAndHomeId(homeHelp.getHomeId(), homeHelp.getName(), homeHelp.getHelpType());
        if(homeHelp1!=null)
            throw new DuplicateHomeHelpException(homeHelp.getName());
        return homeHelpDao.save(homeHelp);
    }

    @Override
    public Boolean delete(Long id) {
        return homeHelpDao.delete(id);
    }

    @Override
    public HomeHelp getById(Long id) {
        return homeHelpDao.getById(id);
    }

    @Override
    public Page<HomeHelp> getHomeHelp(String homeId) {
        return homeHelpDao.getHomeHelp(homeId);
    }

    @Override
    public List<HomeHelp> getByName(String homeID,String name) {
        return homeHelpDao.getByName(homeID,StringUtils.lowerCase(name));
    }

    @Override
    public List<HomeHelp> getNyNameLike(String homeID,String name) {
        return homeHelpDao.getNyNameLike(homeID,StringUtils.lowerCase(name));
    }

    @Override
    public HomeHelp getHomeHelpByNameAndTypeAndHomeId(String homeId, String name, HelpType helpType) {
        return homeHelpDao.getHomeHelpByNameAndTypeAndHomeId(homeId, name, helpType);
    }
}
