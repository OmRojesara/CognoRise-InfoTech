import java.util.Scanner;

public class CourseRegistrationSystem {
    private CourseDatabase courseDB;
    private StudentDatabase studentDB;
    private Scanner scanner;

    public CourseRegistrationSystem() {
        this.courseDB = new CourseDatabase();
        this.studentDB = new StudentDatabase();
        this.scanner = new Scanner(System.in);
    }

    public void displayCourseListing() {
        System.out.println("\n==================================");
        System.out.println("         Available Courses        ");
        System.out.println("==================================");
        for (Course course : courseDB.getAllCourses()) {
            System.out.println("Course Code : " + course.getCourseCode());
            System.out.println("Title       : " + course.getTitle());
            System.out.println("Description : " + course.getDescription());
            System.out.println("Capacity    : " + course.getCapacity());
            System.out.println("Schedule    : " + course.getSchedule());
            System.out.println("----------------------------------");
        }
    }

    public void registerCourse(String studentID, String courseCode) {
        Student student = studentDB.getStudentByID(studentID);
        if (student == null) {
            System.out.println("\nError: Student with ID " + studentID + " not found in the database.");
            System.out.println("Available students:");
            for (Student s : studentDB.getAllStudents()) {
                System.out.println("ID: " + s.getStudentID() + " | Name: " + s.getName());
            }
            return;
        }

        Course course = courseDB.getCourseByCode(courseCode);
        if (course == null) {
            System.out.println("\nError: Course not found.");
            return;
        }

        if (course.getCapacity() <= 0) {
            System.out.println("\nError: Course is full.");
            return;
        }

        student.registerCourse(course);
        course.setCapacity(course.getCapacity() - 1);
        System.out.println("\nSuccess: Course successfully registered for Student ID: " + studentID);
    }

    public void removeCourse(String studentID, String courseCode) {
        Student student = studentDB.getStudentByID(studentID);
        if (student == null) {
            System.out.println("\nError: Student with ID " + studentID + " not found in the database.");
            System.out.println("Available students:");
            for (Student s : studentDB.getAllStudents()) {
                System.out.println("ID: " + s.getStudentID() + " | Name: " + s.getName());
            }
            return;
        }

        Course course = courseDB.getCourseByCode(courseCode);
        if (course == null) {
            System.out.println("\nError: Course not found.");
            return;
        }

        if (!student.getRegisteredCourses().contains(course)) {
            System.out.println("\nError: Student is not registered in this course.");
            return;
        }

        student.dropCourse(course);
        course.setCapacity(course.getCapacity() + 1);
        System.out.println("\nSuccess: Course successfully removed for Student ID: " + studentID);
    }

    public void addStudent() {
        System.out.print("\nEnter student ID: ");
        String studentID = scanner.nextLine().trim();
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine().trim();
        studentDB.addStudent(new Student(studentID, studentName));
        System.out.println("\nSuccess: Student added successfully.");
    }

    public void displayStudents() {
        System.out.println("\n==================================");
        System.out.println("         Registered Students       ");
        System.out.println("==================================");
        for (Student student : studentDB.getAllStudents()) {
            System.out.println("Student ID : " + student.getStudentID());
            System.out.println("Name       : " + student.getName());
            System.out.println("Registered Courses: ");
            if (student.getRegisteredCourses().isEmpty()) {
                System.out.println("  None");
            } else {
                for (Course course : student.getRegisteredCourses()) {
                    System.out.println("  - " + course.getCourseCode() + ": " + course.getTitle());
                }
            }
            System.out.println("----------------------------------");
        }
    }

    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();
        system.courseDB.addCourse(new Course("CSE101", "Introduction to Programming", "Introduction to programming concepts", 30, "Mon/Wed/Fri 10:00-11:30"));
        system.courseDB.addCourse(new Course("MAT201", "Linear Algebra", "Introduction to linear algebra", 25, "Tue/Thu 14:00-15:30"));
        system.courseDB.addCourse(new Course("PHY301", "Classical Mechanics", "Introduction to classical mechanics", 20, "Mon/Wed/Fri 13:00-14:30"));

        system.studentDB.addStudent(new Student("S001", "Alice"));
        system.studentDB.addStudent(new Student("S002", "Bob"));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==================================");
            System.out.println("           Main Menu              ");
            System.out.println("==================================");
            System.out.println("1. Display Course Listing");
            System.out.println("2. Register Course");
            System.out.println("3. Remove Course");
            System.out.println("4. Add Student");
            System.out.println("5. Display Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    system.displayCourseListing();
                    break;
                case 2:
                    System.out.print("\nEnter student ID: ");
                    String studentID = scanner.nextLine().trim(); // Trim to remove extra spaces
                    System.out.print("Enter course code: ");
                    String courseCode = scanner.nextLine().trim(); // Trim to remove extra spaces
                    system.registerCourse(studentID, courseCode);
                    break;
                case 3:
                    System.out.print("\nEnter student ID: ");
                    studentID = scanner.nextLine().trim(); // Trim to remove extra spaces
                    System.out.print("Enter course code: ");
                    courseCode = scanner.nextLine().trim(); // Trim to remove extra spaces
                    system.removeCourse(studentID, courseCode);
                    break;
                case 4:
                    system.addStudent();
                    break;
                case 5:
                    system.displayStudents();
                    break;
                case 6:
                    System.out.println("\nExiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("\nInvalid choice. Please enter a valid option.");
            }
        }
    }
}
