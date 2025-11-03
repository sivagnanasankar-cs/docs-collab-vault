package com.doccollab.file.workers;

import com.doccollab.file.models.ProcessingResult;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileWorkerService {

    private final ExecutorService executor;

    public FileWorkerService() {
        this.executor = Executors.newCachedThreadPool();
    }

    public Future<ProcessingResult> submitProcessingTask(String documentId) {
        return executor.submit(new DocumentProcessingTask(documentId));
    }

    public void shutdown() {
        executor.shutdown();
    }
}