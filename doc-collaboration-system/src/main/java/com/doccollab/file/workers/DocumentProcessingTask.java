package com.doccollab.file.workers;

import com.doccollab.file.models.ProcessingResult;
import com.doccollab.file.models.Status;

import java.util.concurrent.Callable;

public class DocumentProcessingTask implements Callable<ProcessingResult> {

    private final String documentId;

    public DocumentProcessingTask(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public ProcessingResult call() throws Exception {
        long startTime = System.currentTimeMillis();
        try {
            // Simulate a long-running, I/O-heavy processing job
            Thread.sleep(100);
            return new ProcessingResult(documentId, Status.PROCESSED, System.currentTimeMillis() - startTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new ProcessingResult(documentId, Status.ERROR, System.currentTimeMillis() - startTime);
        }
    }
}