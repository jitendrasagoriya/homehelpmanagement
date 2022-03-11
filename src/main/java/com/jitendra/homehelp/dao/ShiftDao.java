package com.jitendra.homehelp.dao;

import com.jitendra.homehelp.entity.HomeHelp;
import com.jitendra.homehelp.entity.Shift;
import com.jitendra.homehelp.repository.ShiftRepository;
import com.jitendra.homehelp.service.ShiftService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShiftDao extends BaseDao<ShiftRepository>{

    public Shift save(Shift homeHelp);

    public Boolean delete(Long id);

    public Shift getById(Long id);

    public Page<Shift> getByHomeHelp(Long id, Pageable pageable);

    public List<Shift> getByHomeHelp(Long id);




}
