package com.stemlink.tutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Exercise3 {
    public static void main(String[] args) {
        // Create a booking
        Booking booking1 = new Booking(
                "STU-001",
                "MEN-005",
                "Core Java OOP",
                LocalDateTime.of(2025, 11, 20, 14, 0),
                2
        );

        System.out.println("Original Booking:");
        System.out.println(booking1.getBookingDetails());

        // Try to "modify" - must create new object
        Booking booking2 = new Booking(
                booking1.getStudentId(),
                booking1.getMentorId(),
                booking1.getSubject(),
                LocalDateTime.of(2025, 11, 21, 14, 0), // Changed date
                3 // Changed duration
        );

        System.out.println("\n'Modified' Booking (new object):");
        System.out.println(booking2.getBookingDetails());

        // Demonstrate immutability
        System.out.println("\nAre they the same object? " + (booking1 == booking2));
        System.out.println("Original booking unchanged? " +
                (booking1.getDurationHours() == 2));

        // Test if you try to modify (this will cause compilation error - commented out)
        // booking1.studentId = "STU-999"; // ❌ Cannot assign - studentId is final
    }
}

class Booking {
    // All instance variables are final (immutable)
    private final String bookingId;
    private final String studentId;
    private final String mentorId;
    private final String subject;
    private final LocalDateTime scheduledTime;
    private final int durationHours;
    private final LocalDateTime createdAt;
    private final String status;

    // Static variable for generating IDs
    private static int bookingCounter = 1;

    // Constructor
    public Booking(String studentId, String mentorId, String subject,
                   LocalDateTime scheduledTime, int durationHours) {
        this.bookingId = String.format("BK-%03d", bookingCounter++);
        this.studentId = studentId;
        this.mentorId = mentorId;
        this.subject = subject;
        this.scheduledTime = scheduledTime;
        this.durationHours = durationHours;
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
    }

    // Getters only (NO setters for immutability!)
    public String getBookingId() {
        return bookingId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getMentorId() {
        return mentorId;
    }

    public String getSubject() {
        return subject;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status;
    }

    // Method to get formatted booking details
    public String getBookingDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return "Booking ID: " + bookingId + "\n" +
                "Student: " + studentId + " | Mentor: " + mentorId + "\n" +
                "Subject: " + subject + "\n" +
                "Scheduled: " + scheduledTime.format(formatter) + "\n" +
                "Duration: " + durationHours + " hours\n" +
                "Status: " + status + "\n" +
                "Created: " + createdAt.format(timeFormatter);
    }

    // equals() - two bookings equal if same bookingId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(bookingId, booking.bookingId);
    }

    // hashCode() - based on bookingId
    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }
}