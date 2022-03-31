package com.jitendra.homehelp.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.jitendra.homehelp.constants.AppConstants;
import com.jitendra.homehelp.dao.impl.HomeHelpJdbcDao;
import com.jitendra.homehelp.dto.PipeDeliminatorBean;
import com.jitendra.homehelp.enums.HelpType;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "home_help",uniqueConstraints = {@UniqueConstraint(columnNames = {"name","homeId","help_type"})})
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class HomeHelp extends PipeDeliminatorBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "help_type", nullable = false)
    private HelpType helpType;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "active", nullable = false)
    private Boolean isActive;

    @Column(name = "homeId")
    private String homeId;

    @OneToMany(mappedBy = "homeHelp", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Shift> shifts;


    public String toJson()  {
        try {
            JsonMapper jsonMapper = new JsonMapper();
            return jsonMapper.writeValueAsString(this);
        } catch (JsonProcessingException jsonProcessingException) {
            return null;
        }
    }


    @Override
    public String toPipeSeparatedString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.getId());
        stringBuffer.append(AppConstants.PIPE);
        stringBuffer.append(this.getName());
        stringBuffer.append(AppConstants.PIPE);

        stringBuffer.append(this.getHelpType().getValue());
        stringBuffer.append(AppConstants.PIPE);
        stringBuffer.append(this.getSalary());
        stringBuffer.append(AppConstants.PIPE);
        stringBuffer.append(this.getPhone());
        stringBuffer.append(AppConstants.PIPE);
        stringBuffer.append(this.getIsActive());
        stringBuffer.append(AppConstants.PIPE);

        shifts.forEach(shift -> {
            stringBuffer.append(shift.toPipeSaperatedString());
            stringBuffer.append(AppConstants.PIPE);
        });

        return stringBuffer.toString();
    }
}