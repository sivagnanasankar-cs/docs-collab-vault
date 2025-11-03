package com.doccollab.auth.models;

public class AuthenticatedUser {

    private final String subject;
    // Add other user details as needed

    public AuthenticatedUser(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}