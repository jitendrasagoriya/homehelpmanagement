package com.jitendra.homehelp.dao.impl;

import com.jitendra.homehelp.entity.Shift;
import com.jitendra.homehelp.repository.ShiftRepository;
import com.jitendra.homehelp.dao.ShiftDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftJdbcDao  implements ShiftDao {

    @Autowired
    private ShiftRepository shiftRepository;

    @Override
    public ShiftRepository getRepository() {
        return shiftRepository;
    }

    @Override
    public Shift save(Shift shift) {
        return shiftRepository.save(shift);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            shiftRepository.deleteById(id);
            return Boolean.TRUE;
        }catch (Exception exception) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Shift getById(Long id) {
        return shiftRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Shift> getByHomeHelp(Long id, Pageable pageable) {
        return shiftRepository.getByHomeHelpId(id,pageable);
    }

    @Override
    public List<Shift> getByHomeHelp(Long id) {
        return shiftRepository.getByHomeHelpId(id);
    }
}
