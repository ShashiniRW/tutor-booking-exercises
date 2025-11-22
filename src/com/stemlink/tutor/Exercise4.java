package com.stemlink.tutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercise4 {
    public static void main(String[] args) {
        System.out.println("=== Creating Different Session Requests ===\n");

        // 1. Simple session (only required fields)
        SessionRequest simple = new SessionRequest.Builder(
                "STU-001",
                "MEN-003",
                "Java Basics"
        ).build();

        System.out.println("1. Simple Session:");
        System.out.println(simple);
        System.out.println();

        // 2. Detailed session with notes and materials
        SessionRequest detailed = new SessionRequest.Builder(
                "STU-002",
                "MEN-005",
                "Spring Boot REST APIs"
        )
                .sessionNotes("Focus on exception handling and validation")
                .materialsNeeded(Arrays.asList("Laptop", "Spring Boot docs", "Postman"))
                .preferredTime(LocalDateTime.now().plusDays(3))
                .build();

        System.out.println("2. Detailed Session:");
        System.out.println(detailed);
        System.out.println();

        // 3. Urgent session
        SessionRequest urgent = new SessionRequest.Builder(
                "STU-003",
                "MEN-002",
                "Database Design"
        )
                .isUrgent(true)
                .preferredTime(LocalDateTime.now().plusDays(2))
                .sessionNotes("Need help before exam!")
                .build();

        System.out.println("3. Urgent Session:");
        System.out.println(urgent);
        System.out.println();

        // 4. Group session
        SessionRequest group = new SessionRequest.Builder(
                "STU-004",
                "MEN-001",
                "React Fundamentals"
        )
                .maxStudents(4)
                .materialsNeeded(Arrays.asList("Laptop", "VS Code", "Node.js installed"))
                .build();

        System.out.println("4. Group Session:");
        System.out.println(group);
        System.out.println();

        // 5. Test validation - try invalid maxStudents (0 or negative)
        try {
            SessionRequest invalid = new SessionRequest.Builder(
                    "STU-005",
                    "MEN-002",
                    "Advanced Java"
            )
                    .maxStudents(0) // Invalid! Must be at least 1
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("5. Validation Test:");
            System.out.println("✅ Caught error: " + e.getMessage());
        }
    }
}

class SessionRequest {
    // Final fields (immutable)
    private final String studentId;
    private final String mentorId;
    private final String subject;
    private final String sessionNotes;
    private final List<String> materialsNeeded;
    private final boolean isUrgent;
    private final LocalDateTime preferredTime;
    private final int maxStudents;

    // Private constructor that takes only the Builder
    private SessionRequest(Builder builder) {
        this.studentId = builder.studentId;
        this.mentorId = builder.mentorId;
        this.subject = builder.subject;
        this.sessionNotes = builder.sessionNotes;
        // Create defensive copy of the list
        this.materialsNeeded = new ArrayList<>(builder.materialsNeeded);
        this.isUrgent = builder.isUrgent;
        this.preferredTime = builder.preferredTime;
        this.maxStudents = builder.maxStudents;
    }

    // Getters only (no setters)
    public String getStudentId() {
        return studentId;
    }

    public String getMentorId() {
        return mentorId;
    }

    public String getSubject() {
        return subject;
    }

    public String getSessionNotes() {
        return sessionNotes;
    }

    public List<String> getMaterialsNeeded() {
        return new ArrayList<>(materialsNeeded); // Return defensive copy
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public LocalDateTime getPreferredTime() {
        return preferredTime;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String sessionType = (maxStudents > 1) ? "Group (max " + maxStudents + " students)" : "Individual";
        String urgentStatus = isUrgent ? "Yes" : "No";
        String notes = sessionNotes.isEmpty() ? "(none)" : sessionNotes;
        String materials = materialsNeeded.isEmpty() ? "(none)" : materialsNeeded.toString();
        String time = (preferredTime != null) ? preferredTime.format(formatter) : "Not specified";

        return "SessionRequest {\n" +
                "  Student: " + studentId + " | Mentor: " + mentorId + "\n" +
                "  Subject: " + subject + "\n" +
                "  Type: " + sessionType + " | Urgent: " + urgentStatus + "\n" +
                "  Notes: " + notes + "\n" +
                "  Materials: " + materials + "\n" +
                "  Preferred Time: " + time + "\n" +
                "}";
    }

    // Static nested Builder class
    public static class Builder {
        // Required parameters (final)
        private final String studentId;
        private final String mentorId;
        private final String subject;

        // Optional parameters (with defaults)
        private String sessionNotes = "";
        private List<String> materialsNeeded = new ArrayList<>();
        private boolean isUrgent = false;
        private LocalDateTime preferredTime = null;
        private int maxStudents = 1;

        // Constructor with required parameters
        public Builder(String studentId, String mentorId, String subject) {
            this.studentId = studentId;
            this.mentorId = mentorId;
            this.subject = subject;
        }

        // Fluent setter methods (return this for method chaining)
        public Builder sessionNotes(String notes) {
            this.sessionNotes = notes;
            return this;
        }

        public Builder materialsNeeded(List<String> materials) {
            this.materialsNeeded = new ArrayList<>(materials); // Defensive copy
            return this;
        }

        public Builder isUrgent(boolean urgent) {
            this.isUrgent = urgent;
            return this;
        }

        public Builder preferredTime(LocalDateTime time) {
            this.preferredTime = time;
            return this;
        }

        public Builder maxStudents(int max) {
            this.maxStudents = max;
            return this;
        }

        // Build with validation
        public SessionRequest build() {
            // Validation 1: Subject must not be empty
            if (subject == null || subject.trim().isEmpty()) {
                throw new IllegalArgumentException("Subject cannot be empty");
            }

            // Validation 2: maxStudents must be at least 1
            if (maxStudents < 1) {
                throw new IllegalArgumentException("maxStudents must be at least 1");
            }

            // Validation 3: If group session (maxStudents > 1), ensure it's at least 2
            // This is implicitly handled - if maxStudents > 1, it's already >= 2

            // Validation 4: If urgent, cannot schedule more than 7 days in advance
            if (isUrgent && preferredTime != null) {
                LocalDateTime sevenDaysFromNow = LocalDateTime.now().plusDays(7);
                if (preferredTime.isAfter(sevenDaysFromNow)) {
                    throw new IllegalArgumentException(
                            "Urgent sessions cannot be scheduled more than 7 days in advance");
                }
            }

            return new SessionRequest(this);
        }
    }
}