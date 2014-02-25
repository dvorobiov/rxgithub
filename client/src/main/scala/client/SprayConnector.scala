package client


import spray.http._
import akka.actor.ActorSystem
import spray.client.pipelining._
import scala.concurrent.Future
import spray.can.Http
import scala.util.{Failure, Success}
import scala.concurrent.duration._
import akka.pattern.ask
import akka.event.Logging
import akka.io.IO
import spray.util._
import akka.util.Timeout
import spray.httpx.LiftJsonSupport
import net.liftweb.json.ext.JodaTimeSerializers
import net.liftweb.json.{Formats, Serialization, NoTypeHints}


trait SprayConnector extends LiftJsonSupport {
  self: { val actorSystem: ActorSystem } =>
  implicit val system = actorSystem
  implicit val timeout = 2.seconds
  implicit val liftJsonFormats: Formats = Serialization.formats(NoTypeHints) ++ JodaTimeSerializers.all
  implicit val receiveTimeout: Timeout = 2 seconds
  import system.dispatcher
  private val log = Logging(system, getClass)
  private val gitHubRootPath = "https://api.github.com"

//  implicit val sslEngineProvider = ClientSSLEngineProvider { engine =>
//    engine.setEnabledCipherSuites(Array("TLS_RSA_WITH_AES_256_CBC_SHA"))
//    engine.setEnabledProtocols(Array("SSLv3", "TLSv1"))
//    engine
//  }

  private val pipeline: HttpRequest => Future[HttpResponse] = sendReceive ~> unmarshal[HttpResponse]


  def requestWithSpray[T](req: GithubRequest): RequestResult[T] = {
    val fullpath = gitHubRootPath + req.url
    val Request = new RequestBuilder(sprayHttpMethod(req))

    val responseFuture = pipeline { Request(fullpath) }
    responseFuture map {
      //    case Success(GoogleApiResult(_, Elevation(_, elevation) :: _)) =>
      //      log.info("The elevation of Mt. Everest is: {} m", elevation)
      //      shutdown()

      case Success(somethingUnexpected: HttpResponse) =>
        somethingUnexpected.message.toString
      //  shutdown()

      case Failure(error) =>
      //  shutdown()
        "fail"
    }
  }

  private def sprayHttpMethod(req: GithubRequest) = req match {
    case GetRequest => HttpMethods.GET
    case PostRequest => HttpMethods.POST
    case PutRequest => HttpMethods.PUT
    case PatchRequest => HttpMethods.PATCH
    case DeleteRequest => HttpMethods.DELETE
  }

  private def shutdown(): Unit = {
    IO(Http).ask(Http.CloseAll)(1.second).await
    system.shutdown()
  }
}


