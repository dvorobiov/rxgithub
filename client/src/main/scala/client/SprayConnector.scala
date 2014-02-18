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


trait SprayConnector {
  self: { val actorSystem: ActorSystem } =>
  implicit val system = actorSystem
  implicit val timeout = 2.seconds
  import system.dispatcher
  private val log = Logging(system, getClass)
  private val gitHubRootUrl = "https://api.github.com"

//  implicit val sslEngineProvider = ClientSSLEngineProvider { engine =>
//    engine.setEnabledCipherSuites(Array("TLS_RSA_WITH_AES_256_CBC_SHA"))
//    engine.setEnabledProtocols(Array("SSLv3", "TLSv1"))
//    engine
//  }

  private val pipeline: HttpRequest => Future[HttpResponse] = sendReceive ~> unmarshal[HttpResponse]


  def connectorRequest(path: String): Future[String] = {
    val responseFuture = pipeline { Get(s"$gitHubRootUrl$path") }
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

  private def shutdown(): Unit = {
    IO(Http).ask(Http.CloseAll)(1.second).await
    system.shutdown()
  }
  private val sprayHttpMethod = Map(
    GET -> HttpMethods.GET,
    POST -> HttpMethods.POST,
    PUT -> HttpMethods.PUT,
    PATCH -> HttpMethods.PATCH,
    DELETE -> HttpMethods.DELETE
  )
}


