package bg.sofia.uni.fmi.mjt.maythecoursebewithyou.impl;

import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Course;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.exception.AlreadyEnrolledException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

class CourseEnrollment {

    private final Course course;
    private final Set<String> seated;
    private final LinkedHashSet<String> waitlist;

    CourseEnrollment(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        this.course = course;
        this.seated = new HashSet<>();
        this.waitlist = new LinkedHashSet<>();
    }

    boolean enroll(String facultyNumber) {
        if (seated.contains(facultyNumber) || waitlist.contains(facultyNumber)) {
            throw new AlreadyEnrolledException(
                    "Student " + facultyNumber + " is already enrolled in or waitlisted for " + course.code());
        }

        if (seated.size() < course.capacity()) {
            seated.add(facultyNumber);
            return true;
        }

        waitlist.add(facultyNumber);
        return false;
    }

    boolean drop(String facultyNumber) {
        if (seated.remove(facultyNumber)) {
            if (!waitlist.isEmpty()) {
                Iterator<String> head = waitlist.iterator();
                String next = head.next();
                head.remove();
                seated.add(next);
            }
            return true;
        }

        return waitlist.remove(facultyNumber);
    }

    Course course() {
        return course;
    }

    int seatedCount() {
        return seated.size();
    }

    int waitlistCount() {
        return waitlist.size();
    }

    Set<String> seatedIds() {
        return Collections.unmodifiableSet(new HashSet<>(seated));
    }

    List<String> waitlistIds() {
        return Collections.unmodifiableList(new ArrayList<>(waitlist));
    }

}
