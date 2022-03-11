package com.jitendra.homehelp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jitendra.homehelp.constants.AppConstants;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Time;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shift")
@ApiModel
public class Shift {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "homehelp_id")
    @JsonIgnore
    private HomeHelp homeHelp;

    @Column(name = "shift_time", nullable = false)
    private Time time;

    public String toPipeSaperatedString() {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(this.id);
        stringBuffer.append(AppConstants.PIPE);
        stringBuffer.append(this.time);

        return stringBuffer.toString();
    }


}