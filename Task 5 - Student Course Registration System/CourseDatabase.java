// CourseDatabase.java
import java.util.ArrayList;
import java.util.List;

public class CourseDatabase {
    private List<Course> courses;

    public CourseDatabase() {
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(String courseCode) {
        courses.removeIf(course -> course.getCourseCode().equals(courseCode));
    }

    public Course getCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public List<Course> getAllCourses() {
        return courses;
    }
}
