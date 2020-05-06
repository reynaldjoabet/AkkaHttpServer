

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class Student(name:String,userName:String)
case class Course(student:List[Student])
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val studentFormat=jsonFormat2(Student)//Format2 because Student has 2 constructor parameters
  implicit  val courseFormat=jsonFormat1(Course)// it has one constructor parameter
}

