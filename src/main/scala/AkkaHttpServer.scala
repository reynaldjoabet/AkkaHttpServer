import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer

import scala.io.StdIn
import scala.util.{Failure, Success}

object AkkaHttpServer extends App with Directives with JsonSupport {
  implicit  val system: ActorSystem = ActorSystem("akka-http")
  implicit  val mat: ActorMaterializer =ActorMaterializer()
  import system.dispatcher
  val port=8080
  val host="0.0.0.0"//localhost
  val routes:Route=
    concat(// using concat to supply many different routes
    path("register"){// register a user in in-memory store
    post {
      entity(as[Student]){student=>
        StudentRepository.save(student)
        complete(s"You have been registered and here is your userName :${student.userName} and name is ${student.name}")

      }
    }
  },
  get{
    path("all"){//all students as JSON
      complete(StatusCodes.OK,{StudentRepository.all})
    }
  },
      get{
        path("getOne"){//return one student in JSON format
          complete(StatusCodes.OK,Student("Good Student","student1"))
        }
      },
      get{
        path("getCourses"){//return course list
          complete(StatusCodes.OK,StudentRepository.getCourses)
        }
      },
      get{
        path("deleteAll"){// delete all students from in-memory store
          complete(StatusCodes.OK,s"${StudentRepository.deleteAll()}")
        }
      },
  post{
    path("delete")
    entity(as[Student]){student=>
      StudentRepository.delete(student)
      complete(StatusCodes.OK,s"Your details have been removed from our database")
    }
  }
    )

  val bindingFuture=Http().bindAndHandle(routes,host,port)
  //bind this route to this ip & port
  // or a set of routes to this ip & port
  println("press Enter to stop")
  bindingFuture.onComplete{
    case Success(serverBinding)=>println(s"Listening to ${serverBinding.localAddress}")
    case Failure(exception)=>
      println(s"Error occurred ${exception.getMessage} ")
      system.terminate()
  }
  StdIn.readLine()//Server stops upon pressing ENTER

  bindingFuture .flatMap(_.unbind())//unbind from port
    .onComplete(_ => system.terminate())
}


