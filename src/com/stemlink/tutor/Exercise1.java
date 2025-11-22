package com.stemlink.tutor;

public class Exercise1 {
    public static void main(String[] args) {
        System.out.println("Creating students...");

        // Create 3 students
        Student student1 = new Student("Alice Johnson", "alice@email.com");
        System.out.println("Student created: " + student1.getDetails());

        Student student2 = new Student("Bob Smith", "bob@email.com");
        System.out.println("Student created: " + student2.getDetails());

        Student student3 = new Student("Carol White", "carol@email.com");
        System.out.println("Student created: " + student3.getDetails());

        // Print total students using static method
        System.out.println("\nTotal students registered: " + Student.getTotalStudents());
    }
}

class Student {
    // Instance variables
    private final String studentId;
    private String name;
    private String email;

    // Static variables
    private static int totalStudents = 0;
    private static int studentCounter = 1;

    // Constructor
    public Student(String name, String email) {
        this.studentId = String.format("STU-%03d", studentCounter);
        this.name = name;
        this.email = email;
        studentCounter++;
        totalStudents++;
    }

    // Instance method
    public String getDetails() {
        return studentId + ", " + name + ", " + email;
    }

    // Static method
    public static int getTotalStudents() {
        return totalStudents;
    }

    // Getters
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