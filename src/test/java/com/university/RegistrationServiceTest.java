package com.university;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

    @Test
    void shouldRegisterStudentForValidCourse() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS301"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectInactiveCourse() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                false,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
    }

    @Test
    void shouldRejectMissingPrerequisite() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS401",
                "Advanced Algorithms",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS401"));
    }

    @Test
    void shouldAllowRegistrationWhenPrerequisiteIsCompleted() {

        Student student = new Student("S101", "Anita");
        student.addCompletedPrerequisite("CS301");

        Course course = new Course(
                "CS401",
                "Advanced Algorithms",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS401"));
    }

    @Test
    void shouldRejectDuplicateRegistration() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        assertTrue(service.register(student, course));

        boolean secondAttempt = service.register(student, course);

        assertFalse(secondAttempt);
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectRegistrationWhenCreditLimitIsExceeded() {

        Student student = new Student("S101", "Anita");

        // Simulate 20 existing credits
        student.addRegistration("CS101", 4);
        student.addRegistration("CS102", 4);
        student.addRegistration("CS201", 4);
        student.addRegistration("CS202", 4);
        student.addRegistration("CS203", 4);

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
    }
    
    @Test
    void shouldRejectRegistrationWhenCourseIsFull() {
        Student student = new Student("S201", "Ravi");

        Course course = new Course(
                "CS501", "Operating Systems", 4, 1, true, null
        );

        RegistrationService service = new RegistrationService();

        Student firstStudent = new Student("S202", "Meena");
        assertTrue(service.register(firstStudent, course));

        int originalCredits = student.getTotalCredits();
        int originalEnrollment = course.getCurrentEnrollment();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS501"));
        assertEquals(originalCredits, student.getTotalCredits());
        assertEquals(originalEnrollment, course.getCurrentEnrollment());
    }

    @Test
    void shouldAllowRegistrationForLastAvailableSeat() {
        Student student = new Student("S203", "Priya");

        Course course = new Course(
                "CS502", "Computer Networks", 4, 2, true, null
        );

        RegistrationService service = new RegistrationService();

        Student firstStudent = new Student("S204", "Arun");
        assertTrue(service.register(firstStudent, course));

        assertEquals(1, course.getCurrentEnrollment());

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS502"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(2, course.getCurrentEnrollment());
    }
    
    @Test
    void shouldAllowRegistrationForFifthCourse() {
        Student student = new Student("S301", "Ravi");

        for (int i = 1; i <= 4; i++) {
            student.addRegistration("CS" + i, 2);
        }

        Course course = new Course(
                "CS505", "Operating Systems", 2, 30, true, null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertEquals(5, student.getRegisteredCourses().size());
        assertEquals(10, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectRegistrationForSixthCourse() {
        Student student = new Student("S302", "Meena");

        for (int i = 1; i <= 5; i++) {
            student.addRegistration("CS" + i, 2);
        }

        Course course = new Course(
                "CS506", "Computer Networks", 2, 30, true, null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertEquals(5, student.getRegisteredCourses().size());
        assertEquals(10, student.getTotalCredits());
        assertFalse(student.isRegistered("CS506"));
        assertEquals(0, course.getCurrentEnrollment());
    }


    
}