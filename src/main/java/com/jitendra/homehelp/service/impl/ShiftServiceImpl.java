package com.jitendra.homehelp.service.impl;

import com.jitendra.homehelp.dao.ShiftDao;
import com.jitendra.homehelp.entity.Shift;
import com.jitendra.homehelp.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    private ShiftDao shiftDao;

    @Override
    public Shift save(Shift shift) {
        return shiftDao.save(shift);
    }

    @Override
    public Boolean delete(Long id) {
        return shiftDao.delete(id);
    }

    @Override
    public Shift getById(Long id) {
        return shiftDao.getById(id);
    }

    @Override
    public Page<Shift> getByHomeHelp(Long id, Pageable pageable) {
        return shiftDao.getByHomeHelp(id,pageable);
    }

    @Override
    public List<Shift> getByHomeHelp(Long id) {
        return shiftDao.getByHomeHelp(id);
    }
}
