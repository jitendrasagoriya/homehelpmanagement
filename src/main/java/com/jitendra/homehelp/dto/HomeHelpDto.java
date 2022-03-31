package com.jitendra.homehelp.dto;


import com.jitendra.homehelp.entity.Shift;
import com.jitendra.homehelp.enums.HelpType;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeHelpDto {

    private Long id;

    private Long shiftId;

    private Date date;

    private String status;
}
