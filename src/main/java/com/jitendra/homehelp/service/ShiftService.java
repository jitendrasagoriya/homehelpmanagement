package com.jitendra.homehelp.service;


import com.jitendra.homehelp.entity.Shift;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShiftService {

    public Shift save(Shift homeHelp);

    public Boolean delete(Long id);

    public Shift getById(Long id);

    public Page<Shift> getByHomeHelp(Long id, Pageable pageable);

    public List<Shift> getByHomeHelp(Long id);
}
