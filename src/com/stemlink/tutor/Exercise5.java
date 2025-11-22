package com.stemlink.tutor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Exercise5 {
    // Store created objects (in-memory)
    private static final List<Student5> students = new ArrayList<>();
    private static final List<Mentor5> mentors = new ArrayList<>();
    private static final List<Subject5> subjects = new ArrayList<>();
    private static final List<Booking5> bookings = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   STEM Link Tutor Booking System     ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        // 1. Initialize platform with subjects
        initializeSubjects();

        // 2. Register students and mentors
        registerUsers();

        // 3. Create bookings
        createBookings();

        // 4. Display system statistics
        displayStatistics();
    }

    private static void initializeSubjects() {
        System.out.println("📚 Initializing subjects...");

        subjects.add(new Subject5("JAVA-01", "Core Java OOP", 40, "PROGRAMMING"));
        System.out.println("  ✅ Added: JAVA-01 - Core Java OOP (40 hrs)");

        subjects.add(new Subject5("SPR-01", "Spring Boot Basics", 32, "PROGRAMMING"));
        System.out.println("  ✅ Added: SPR-01 - Spring Boot Basics (32 hrs)");

        subjects.add(new Subject5("DB-01", "Database Design", 24, "DATABASE"));
        System.out.println("  ✅ Added: DB-01 - Database Design (24 hrs)");

        subjects.add(new Subject5("REACT-01", "React Fundamentals", 36, "WEB"));
        System.out.println("  ✅ Added: REACT-01 - React Fundamentals (36 hrs)");

        subjects.add(new Subject5("DSA-01", "Data Structures", 48, "PROGRAMMING"));
        System.out.println("  ✅ Added: DSA-01 - Data Structures (48 hrs)");
    }

    private static void registerUsers() {
        System.out.println("\n👥 Registering users...");

        // Register students
        students.add(new Student5("Alice Johnson", "alice@email.com"));
        System.out.println("  👨‍🎓 Student registered: " + students.get(0).getDetails());

        students.add(new Student5("Bob Smith", "bob@email.com"));
        System.out.println("  👨‍🎓 Student registered: " + students.get(1).getDetails());

        students.add(new Student5("Carol White", "carol@email.com"));
        System.out.println("  👨‍🎓 Student registered: " + students.get(2).getDetails());

        System.out.println();

        // Register mentors
        mentors.add(new Mentor5("Dr. Sarah Ahmed", "Java", 2000.0, 4.8));
        System.out.println("  👨‍🏫 Mentor registered: " + mentors.get(0).getDetails());

        mentors.add(new Mentor5("Prof. John Doe", "Spring Boot", 2500.0, 4.9));
        System.out.println("  👨‍🏫 Mentor registered: " + mentors.get(1).getDetails());

        mentors.add(new Mentor5("Ms. Emily Chen", "React", 1800.0, 4.7));
        System.out.println("  👨‍🏫 Mentor registered: " + mentors.get(2).getDetails());
    }

    private static void createBookings() {
        System.out.println("\n📅 Creating bookings...");

        // Booking 1: Valid booking
        attemptBooking(
                students.get(0).getStudentId(),
                mentors.get(0).getMentorId(),
                "Core Java OOP",
                LocalDateTime.now().plusDays(5).withHour(14).withMinute(0),
                2
        );

        // Booking 2: Valid booking
        attemptBooking(
                students.get(1).getStudentId(),
                mentors.get(1).getMentorId(),
                "Spring Boot Basics",
                LocalDateTime.now().plusDays(3).withHour(10).withMinute(0),
                3
        );

        // Booking 3: Valid booking
        attemptBooking(
                students.get(2).getStudentId(),
                mentors.get(2).getMentorId(),
                "React Fundamentals",
                LocalDateTime.now().plusDays(7).withHour(16).withMinute(0),
                2
        );

        // Booking 4: Valid booking
        attemptBooking(
                students.get(0).getStudentId(),
                mentors.get(1).getMentorId(),
                "Database Design",
                LocalDateTime.now().plusDays(10).withHour(11).withMinute(0),
                1
        );

        // Booking 5: Invalid booking (duration too long)
        attemptBooking(
                students.get(1).getStudentId(),
                mentors.get(0).getMentorId(),
                "Data Structures",
                LocalDateTime.now().plusDays(2).withHour(9).withMinute(0),
                5  // Invalid: exceeds MAX_BOOKING_HOURS (4)
        );
    }

    private static void attemptBooking(
            String studentId,
            String mentorId,
            String subject,
            LocalDateTime scheduledTime,
            int duration
    ) {
        System.out.println("\n  Attempting booking:");
        System.out.println("    Student: " + studentId);
        System.out.println("    Mentor: " + mentorId);
        System.out.println("    Subject: " + subject);
        System.out.println("    Time: " + scheduledTime);
        System.out.println("    Duration: " + duration + " hours");

        // Validate
        if (!BookingValidator5.isValidBookingTime(scheduledTime)) {
            System.out.println("    ❌ Invalid booking time (must be in future)");
            return;
        }

        if (!BookingValidator5.isValidDuration(duration)) {
            System.out.println("    ❌ Invalid duration (must be " +
                    PlatformConstants5.MIN_BOOKING_HOURS + "-" +
                    PlatformConstants5.MAX_BOOKING_HOURS + " hours)");
            return;
        }

        // Create booking
        Booking5 booking = new Booking5(studentId, mentorId, subject, scheduledTime, duration);
        bookings.add(booking);

        // Calculate fees
        double baseFee = FeeCalculator5.calculateBaseFee("STANDARD", duration);
        double platformFee = FeeCalculator5.calculatePlatformFee(baseFee);
        double total = baseFee + platformFee;

        System.out.println("    ✅ Booking created: " + booking.getBookingId());
        System.out.println("    💰 Fees - Base: LKR " + String.format("%.2f", baseFee) +
                " | Platform: LKR " + String.format("%.2f", platformFee) +
                " | Total: LKR " + String.format("%.2f", total));
    }

    private static void displayStatistics() {
        System.out.println("\n📊 System Statistics");
        System.out.println("═══════════════════════════════════════");

        // Calculate statistics
        int totalStudentsCount = Student5.getTotalStudents();
        int totalMentorsCount = Mentor5.getTotalMentors();
        int totalSubjectsCount = subjects.size();
        int totalBookingsCount = bookings.size();

        // Calculate total revenue
        double totalRevenue = 0.0;
        int totalDuration = 0;

        for (Booking5 booking : bookings) {
            int duration = booking.getDurationHours();
            totalDuration += duration;
            double baseFee = FeeCalculator5.calculateBaseFee("STANDARD", duration);
            double platformFee = FeeCalculator5.calculatePlatformFee(baseFee);
            totalRevenue += (baseFee + platformFee);
        }

        // Calculate average duration
        double averageDuration = totalBookingsCount > 0 ?
                (double) totalDuration / totalBookingsCount : 0.0;

        // Display statistics
        System.out.println("  Total Students: " + totalStudentsCount);
        System.out.println("  Total Mentors: " + totalMentorsCount);
        System.out.println("  Total Subjects: " + totalSubjectsCount);
        System.out.println("  Total Bookings: " + totalBookingsCount);
        System.out.println("  Total Revenue: LKR " + String.format("%.2f", totalRevenue));
        System.out.println("  Average Duration: " + String.format("%.1f", averageDuration) + " hours");
    }
}

// ==================== MODEL CLASSES ====================

class Student5 {
    private final String studentId;
    private String name;
    private String email;

    private static int totalStudents = 0;
    private static int studentCounter = 1;

    public Student5(String name, String email) {
        this.studentId = String.format("STU-%03d", studentCounter);
        this.name = name;
        this.email = email;
        studentCounter++;
        totalStudents++;
    }

    public String getDetails() {
        return studentId + " - " + name;
    }

    public static int getTotalStudents() {
        return totalStudents;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

class Mentor5 {
    private final String mentorId;
    private String name;
    private final String specialization;
    private final double hourlyRate;
    private double rating;

    private static int totalMentors = 0;
    private static int mentorCounter = 1;

    public Mentor5(String name, String specialization, double hourlyRate, double rating) {
        this.mentorId = String.format("MEN-%03d", mentorCounter);
        this.name = name;
        this.specialization = specialization;
        this.hourlyRate = hourlyRate;
        this.rating = rating;
        mentorCounter++;
        totalMentors++;
    }

    public String getDetails() {
        return mentorId + " - " + name + " (" + specialization + ", LKR " + hourlyRate + "/hr, ⭐" + rating + ")";
    }

    public static int getTotalMentors() {
        return totalMentors;
    }

    public String getMentorId() {
        return mentorId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getRating() {
        return rating;
    }
}

final class Subject5 {
    private final String subjectCode;
    private final String title;
    private final int creditHours;
    private final String category;

    public Subject5(String subjectCode, String title, int creditHours, String category) {
        this.subjectCode = subjectCode;
        this.title = title;
        this.creditHours = creditHours;
        this.category = category;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getTitle() {
        return title;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject5 subject = (Subject5) o;
        return Objects.equals(subjectCode, subject.subjectCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectCode);
    }

    @Override
    public String toString() {
        return subjectCode + " - " + title + " (" + creditHours + " hrs)";
    }
}

class Booking5 {
    private final String bookingId;
    private final String studentId;
    private final String mentorId;
    private final String subject;
    private final LocalDateTime scheduledTime;
    private final int durationHours;
    private final LocalDateTime createdAt;
    private final String status;

    private static int bookingCounter = 1;

    public Booking5(String studentId, String mentorId, String subject,
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
}

// ==================== UTILITY CLASSES ====================

class IdGenerator5 {
    private static int studentCounter = 1;
    private static int mentorCounter = 1;
    private static int bookingCounter = 1;

    private IdGenerator5() {
        throw new AssertionError("Cannot instantiate IdGenerator utility class");
    }

    public static String generateStudentId() {
        return String.format("STU-%03d", studentCounter++);
    }

    public static String generateMentorId() {
        return String.format("MEN-%03d", mentorCounter++);
    }

    public static String generateBookingId() {
        return String.format("BK-%03d", bookingCounter++);
    }
}

class BookingValidator5 {
    private BookingValidator5() {}

    public static boolean isValidBookingTime(LocalDateTime bookingTime) {
        return bookingTime.isAfter(LocalDateTime.now());
    }

    public static boolean isValidDuration(int hours) {
        return hours >= PlatformConstants5.MIN_BOOKING_HOURS &&
                hours <= PlatformConstants5.MAX_BOOKING_HOURS;
    }

    public static boolean isSlotAvailable(
            LocalDateTime newStart,
            LocalDateTime newEnd,
            LocalDateTime existingStart,
            LocalDateTime existingEnd
    ) {
        return !(newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart));
    }
}

class FeeCalculator5 {
    private static final double STANDARD_RATE = 1500.0;
    private static final double URGENT_RATE = 2500.0;
    private static final double GROUP_RATE = 1200.0;
    private static final double PLATFORM_FEE_PERCENTAGE = 0.15;

    private FeeCalculator5() {}

    public static double calculateBaseFee(String bookingType, int hours) {
        double rate;
        switch (bookingType.toUpperCase()) {
            case "STANDARD":
                rate = STANDARD_RATE;
                break;
            case "URGENT":
                rate = URGENT_RATE;
                break;
            case "GROUP":
                rate = GROUP_RATE;
                break;
            default:
                rate = STANDARD_RATE;
        }
        return rate * hours;
    }

    public static double calculatePlatformFee(double baseFee) {
        return baseFee * PLATFORM_FEE_PERCENTAGE;
    }

    public static double calculateTotal(String bookingType, int hours) {
        double baseFee = calculateBaseFee(bookingType, hours);
        double platformFee = calculatePlatformFee(baseFee);
        return baseFee + platformFee;
    }
}

// ==================== CONSTANTS ====================

class PlatformConstants5 {
    public static final int MIN_BOOKING_HOURS = 1;
    public static final int MAX_BOOKING_HOURS = 4;
    public static final int MAX_ADVANCE_BOOKING_DAYS = 30;

    public static final double STANDARD_HOURLY_RATE = 1500.0;
    public static final double URGENT_MULTIPLIER = 1.5;
    public static final double GROUP_DISCOUNT = 0.8;
    public static final double PLATFORM_FEE_PERCENTAGE = 0.15;

    public static final double MIN_MENTOR_RATING = 1.0;
    public static final double MAX_MENTOR_RATING = 5.0;
    public static final double MINIMUM_ACCEPTABLE_RATING = 3.0;

    private PlatformConstants5() {
        throw new AssertionError("Cannot instantiate constants class");
    }
}