package com.jitendra.homehelp.batch.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;


@Component
public class CustomChunkListener implements ChunkListener {
    private static final Logger logger =   LogManager.getLogger(CustomChunkListener.class);

    @Override
    public void afterChunk(ChunkContext context) {
        logger.info("Called afterChunk().");
    }

    @Override
    public void beforeChunk(ChunkContext context) {
        logger.info("Called beforeChunk().");
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        logger.info("Called afterChunkError().");
        throw new RuntimeException();
    }
}