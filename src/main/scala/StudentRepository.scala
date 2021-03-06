import scala.collection.mutable.ArrayBuffer

object StudentRepository {

  var students=scala.collection.mutable.ArrayBuffer[Student](Student("Name1","userName1"),
    Student("Name2","userName2"),Student("Name3","userName3"),Student("Name4","userName4")
  )
  val courses=List(Course("Computer Science",students.toList),Course("Entrepreneurship",students.toList),Course("Computer Science",students.toList),
    Course("Physics",students.toList),Course("Mathematics",students.toList))
  def save(student: Student): ArrayBuffer[Student] =students.addOne(student)
  def delete(student: Student): ArrayBuffer[Student] =if(students.isEmpty)  throw new NoSuchElementException("Student does not exist in database") else
    students-=student
  def all: ArrayBuffer[Student] =students
  def deleteAll(): ArrayBuffer[Student] =if(students.isEmpty)  throw new NoSuchElementException("Student does not exist in database") else
    students.drop(students.length)
  def getCourses=courses

}
