package bg.sofia.uni.fmi.mjt.maythecoursebewithyou;

import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Course;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Student;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StatisticsDashboard {

    Set<Course> getMostContestedCourses();

    List<Student> getBusiestStudents();

    Map<String, Integer> countStudentsPerCourse();

    Set<Student> getEnrolledStudents(String courseCode);

    List<Student> getWaitlist(String courseCode);

}
