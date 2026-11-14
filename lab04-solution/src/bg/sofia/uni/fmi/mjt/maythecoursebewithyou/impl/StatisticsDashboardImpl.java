package bg.sofia.uni.fmi.mjt.maythecoursebewithyou.impl;

import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.StatisticsDashboard;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Course;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Student;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.exception.CourseNotEnlistedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StatisticsDashboardImpl implements StatisticsDashboard {

    private final Map<String, CourseEnrollment> enrollments;
    private final Map<String, Student> students;

    // Shares (not copies) the maps of the system, so the dashboard always sees the current state.
    StatisticsDashboardImpl(Map<String, CourseEnrollment> enrollments, Map<String, Student> students) {
        this.enrollments = enrollments;
        this.students = students;
    }

    @Override
    public Set<Course> getMostContestedCourses() {
        List<CourseEnrollment> sorted = new ArrayList<>(enrollments.values());
        Collections.sort(sorted, new ContentionComparator());

        Set<Course> result = new LinkedHashSet<>();
        for (CourseEnrollment enrollment : sorted) {
            result.add(enrollment.course());
        }
        return Collections.unmodifiableSet(result);
    }

    @Override
    public List<Student> getBusiestStudents() {
        Map<String, Integer> load = new HashMap<>();
        for (CourseEnrollment enrollment : enrollments.values()) {
            for (String id : enrollment.seatedIds()) {
                Integer current = load.get(id);
                load.put(id, current == null ? 1 : current + 1);
            }
        }

        List<Student> sorted = new ArrayList<>(students.values());
        Collections.sort(sorted, new LoadComparator(load));
        return Collections.unmodifiableList(sorted);
    }

    @Override
    public Map<String, Integer> countStudentsPerCourse() {
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, CourseEnrollment> entry : enrollments.entrySet()) {
            result.put(entry.getKey(), entry.getValue().seatedCount());
        }
        return Collections.unmodifiableMap(result);
    }

    @Override
    public Set<Student> getEnrolledStudents(String courseCode) {
        CourseEnrollment enrollment = getEnrollment(courseCode);

        Set<Student> result = new HashSet<>();
        for (String id : enrollment.seatedIds()) {
            result.add(students.get(id));
        }
        return Collections.unmodifiableSet(result);
    }

    @Override
    public List<Student> getWaitlist(String courseCode) {
        CourseEnrollment enrollment = getEnrollment(courseCode);

        List<Student> result = new ArrayList<>();
        for (String id : enrollment.waitlistIds()) {
            result.add(students.get(id));
        }
        return Collections.unmodifiableList(result);
    }

    private CourseEnrollment getEnrollment(String courseCode) {
        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("Course code cannot be null or blank");
        }
        CourseEnrollment enrollment = enrollments.get(courseCode);
        if (enrollment == null) {
            throw new CourseNotEnlistedException("Course " + courseCode + " is not enlisted");
        }
        return enrollment;
    }

    private static class ContentionComparator implements Comparator<CourseEnrollment> {
        @Override
        public int compare(CourseEnrollment a, CourseEnrollment b) {
            int byWaitlist = Integer.compare(b.waitlistCount(), a.waitlistCount());
            if (byWaitlist != 0) {
                return byWaitlist;
            }
            int bySeated = Integer.compare(b.seatedCount(), a.seatedCount());
            if (bySeated != 0) {
                return bySeated;
            }
            int byTitle = a.course().title().compareTo(b.course().title());
            if (byTitle != 0) {
                return byTitle;
            }
            return a.course().code().compareTo(b.course().code());
        }
    }

    private static class LoadComparator implements Comparator<Student> {
        private final Map<String, Integer> load;

        LoadComparator(Map<String, Integer> load) {
            this.load = load;
        }

        @Override
        public int compare(Student a, Student b) {
            int loadA = load.getOrDefault(a.facultyNumber(), 0);
            int loadB = load.getOrDefault(b.facultyNumber(), 0);
            int byLoad = Integer.compare(loadB, loadA);
            if (byLoad != 0) {
                return byLoad;
            }
            return a.facultyNumber().compareTo(b.facultyNumber());
        }
    }

}
