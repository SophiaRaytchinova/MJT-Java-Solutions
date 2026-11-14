package bg.sofia.uni.fmi.mjt.maythecoursebewithyou.impl;

import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.MayTheCourseBeWithYouAPI;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.StatisticsDashboard;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Course;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Student;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.exception.CourseAlreadyEnrolledException;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.exception.CourseNotEnlistedException;

import java.util.HashMap;
import java.util.Map;

public class MayTheCourseBeWithYou implements MayTheCourseBeWithYouAPI {

    private final Map<String, CourseEnrollment> enrollments;
    private final Map<String, Student> students;
    private final StatisticsDashboard dashboard;

    public MayTheCourseBeWithYou() {
        this.enrollments = new HashMap<>();
        this.students = new HashMap<>();
        this.dashboard = new StatisticsDashboardImpl(enrollments, students);
    }

    @Override
    public void enlistCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (enrollments.containsKey(course.code())) {
            throw new CourseAlreadyEnrolledException("Course " + course.code() + " is already enlisted");
        }
        enrollments.put(course.code(), new CourseEnrollment(course));
    }

    @Override
    public Course delistCourse(String courseCode) {
        validateCode(courseCode);
        CourseEnrollment removed = enrollments.remove(courseCode);
        if (removed == null) {
            throw new CourseNotEnlistedException("Course " + courseCode + " is not enlisted");
        }
        return removed.course();
    }

    @Override
    public boolean enroll(Student student, String courseCode) {
        validateStudent(student);
        validateCode(courseCode);
        CourseEnrollment enrollment = getEnrollment(courseCode);

        boolean seated = enrollment.enroll(student.facultyNumber());
        students.putIfAbsent(student.facultyNumber(), student);
        return seated;
    }

    @Override
    public boolean drop(Student student, String courseCode) {
        validateStudent(student);
        validateCode(courseCode);
        return getEnrollment(courseCode).drop(student.facultyNumber());
    }

    @Override
    public StatisticsDashboard getStatisticsDashboard() {
        return dashboard;
    }

    private CourseEnrollment getEnrollment(String courseCode) {
        CourseEnrollment enrollment = enrollments.get(courseCode);
        if (enrollment == null) {
            throw new CourseNotEnlistedException("Course " + courseCode + " is not enlisted");
        }
        return enrollment;
    }

    private void validateStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
    }

    private void validateCode(String courseCode) {
        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("Course code cannot be null or blank");
        }
    }

}
