package com.jitendra.homehelp.batch.writer;

import com.jitendra.homehelp.dto.AttendanceDto;
import com.jitendra.homehelp.utils.Utils;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.ExtractorLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class CleanupWriter extends FlatFileItemWriter<AttendanceDto> {

    public CleanupWriter() {
        setResource(new FileSystemResource("D:\\homehelpmanagement\\homehelpmanagement\\src\\main\\resources\\out\\dump-"+ Utils.getTodayAsStringByGivenFormat("dd-MM-yyyy")+".csv"));
        setLineAggregator(new ExtractorLineAggregator<AttendanceDto>() {
            @Override
            protected String doAggregate(Object[] objects) {
                StringBuilder stringBuilder = new StringBuilder();
                Arrays.stream(objects).forEach(o -> {
                    stringBuilder.append(o);
                    stringBuilder.append("|");
                });
                return stringBuilder.toString();
            }
        });
    }
}
