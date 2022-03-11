package com.jitendra.homehelp.entity;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendance",uniqueConstraints = {@UniqueConstraint(columnNames = {"homeHelpId","shift_id","date"})})
@ApiModel
public class Attendance {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "homeHelpId", nullable = false)
    private Long homeHelpId;

    @Column(name = "date", nullable = false)
    private Date date;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shift_id", referencedColumnName = "id")
    private Shift shiftId;

    @Column(name = "present", nullable = false)
    private Boolean present;




}