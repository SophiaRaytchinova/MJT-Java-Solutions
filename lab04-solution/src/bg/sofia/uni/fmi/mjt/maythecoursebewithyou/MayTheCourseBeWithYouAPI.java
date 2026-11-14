package bg.sofia.uni.fmi.mjt.maythecoursebewithyou;

import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Course;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Student;

public interface MayTheCourseBeWithYouAPI {

    void enlistCourse(Course course);

    Course delistCourse(String courseCode);

    boolean enroll(Student student, String courseCode);

    boolean drop(Student student, String courseCode);

    StatisticsDashboard getStatisticsDashboard();

}
