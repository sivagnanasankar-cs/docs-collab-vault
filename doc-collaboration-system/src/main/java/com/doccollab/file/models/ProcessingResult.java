package com.doccollab.file.models;

public class ProcessingResult {
    private final String documentId;
    private final Status status;
    private final long processingTimeMs;

    public ProcessingResult(String documentId, Status status, long processingTimeMs) {
        this.documentId = documentId;
        this.status = status;
        this.processingTimeMs = processingTimeMs;
    }

    public String getDocumentId() {
        return documentId;
    }

    public Status getStatus() {
        return status;
    }

    public long getProcessingTimeMs() {
        return processingTimeMs;
    }
}