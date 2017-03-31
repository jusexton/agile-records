package database

import security.Hash
import security.util.HashingUtil
import users.students.Course
import users.students.Grade
import users.students.GradeType
import users.students.Major
import users.students.Student
import util.MathUtil

import java.sql.SQLException
import java.time.LocalTime

/**
 * Script used to generate users with random associated data
 * and insert them into database.
 */

static Student generateStudent() {
    String firstName = getRandomElement(new File(Populator.class
            .getResource("/names/first-names.txt")
            .toURI())
            .readLines())
    String lastName = getRandomElement(new File(Populator.class
            .getResource("/names/last-names.txt")
            .toURI())
            .readLines())
    String username = firstName.substring(0, 1) + lastName
    Hash password = HashingUtil.hash(Constants.PASSWORD, "SHA-512", HashingUtil.generateSalt())
    Student student = new Student(username, password)

    student.setFirstName(firstName)
    student.setLastName(lastName)
    student.setEmail(username + "@email.com")

    Major[] majors = Major.values()
    Major major = majors[MathUtil.getRandomInt(0, majors.length - 1)]
    student.setMajor(major)

    int courseCount = MathUtil.getRandomInt(1, 5)
    courseCount.times{
        student.getCourses().add(generateCourse())
    }
    return student
}

// Returns random element of a given list.
static <T> String getRandomElement(List<T> list){
    return list.get(MathUtil.getRandomInt(0, list.size()))
}

// Note: Does not assign a time a the generated course.
static Course generateCourse() {
    // Credit hours hard coded to 3 because all courses in
    // COURSE_NAMES have 3 credit hours.
    Course course = new Course(
            Constants.COURSE_NAMES[MathUtil.getRandomInt(0, Constants.COURSE_NAMES.length - 1)],
            3,
            MathUtil.getRandomInt(10000, 99999))

    // Randomly adds 0-10 grades to the course
    int gradeCount = MathUtil.getRandomInt(0, 20)
    gradeCount.times {
        course.getGrades().add(generateGrade())
    }
    return course
}

// Note: Does not assign a name the generated grade.
static Grade generateGrade() {
    // Get random GradeType value.
    GradeType[] gradeTypes = GradeType.values()
    GradeType type = gradeTypes[MathUtil.getRandomInt(0, gradeTypes.length - 1)]
    return new Grade(MathUtil.round(MathUtil.getRandomDouble(50, 100), 2), type)
}

// TODO: Possibly create addUsers function to speed process up.
/**
 * @param count The number of users that will be generated and added
 * to the database.
 */
static PopulateStatistics populateDatabase(int count) {
    def stats = new PopulateStatistics()
    def start = System.nanoTime()
    try {
        SQLConnection connection = new SQLConnection()
        // Add count number of students.
        count.times {
            Student student = generateStudent()
            if (!connection.addUser(student)){
                stats.nameAttempt(student.getUserName())
            } else {
                stats.studentsAdded++
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace()
    }
    stats.time = (System.nanoTime() - start) / 1000000000.0
    return stats
}

println("Adding Students...")
// Places n number of randomly generated users in database.
def stats = populateDatabase(1)

// Display statistics gathered while populating database
println("Time Taken: " + stats.time + " seconds")
println("Students Added: " + stats.studentsAdded)
def entries = stats.takenUsernames.entrySet()
if (entries.size() > 0){
    println("Attempted Additions:")
    entries.forEach { entry ->
        String message = String.format("Name: %s, Count: %s", entry.key, entry.value)
        println(message)
    }
}
println("Done.")
