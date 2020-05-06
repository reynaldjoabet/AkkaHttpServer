

import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class Student(name:String,userName:String)
case class Course(name:String,student:List[Student],courseId:String=UUID.randomUUID().toString)

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol{
  import spray.json._
  implicit val printer=PrettyPrinter
  implicit val studentFormat=jsonFormat2(Student)//Format2 because Student has 2 constructor parameters
  implicit  val courseFormat=jsonFormat3(Course)// it has 3 constructor parameters
}

