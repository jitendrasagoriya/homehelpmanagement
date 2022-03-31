package com.jitendra.homehelp.batch.listener;

import com.jitendra.homehelp.dto.PipeDeliminatorBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.SkipListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class AbstractStepSkipListener<H,T extends PipeDeliminatorBean> implements SkipListener<String,H> {

    private static final Logger logger =   LogManager.getLogger(AbstractStepSkipListener.class);

    private BufferedWriter bw = null;

    private T t;

    public AbstractStepSkipListener(File file) throws IOException {
        bw= new BufferedWriter(new FileWriter(file, true));
        logger.info("MySkipListener =========> :"+file);
    }

    @Override
    public void onSkipInRead(Throwable throwable) {    }

    @Override
    public void onSkipInWrite(H h, Throwable throwable) {
        logger.info("StepSkipListener - afterWrite");
        String error = null;
        System.out.println("onSkipInRead =========> :");
        t = getPipeEliminatedBean(h);
        error = getError(throwable);
        try {
            bw.write("ERROR : " + error +" | "+t.toPipeSeparatedString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            logger.error("Unable to write skipped line to error file");
        }
    }

    @Override
    public void onSkipInProcess(String s, Throwable throwable) {   }

    public abstract String getError(Throwable throwable);

    public abstract T getPipeEliminatedBean(H h);


}
