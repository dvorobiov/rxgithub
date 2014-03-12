package client


import spray.http._
import akka.actor.ActorSystem
import spray.client.pipelining._
import scala.concurrent.Future
import spray.can.Http
import scala.concurrent.duration._
import akka.pattern.ask
import akka.event.Logging
import akka.io.IO
import spray.util._
import akka.util.Timeout
import spray.httpx.{SprayJsonSupport, LiftJsonSupport}
import net.liftweb.json.ext.JodaTimeSerializers
import net.liftweb.json.{Formats, Serialization, NoTypeHints}
import spray.httpx.unmarshalling.{Unmarshaller, FromResponseUnmarshaller}
import core.User
import spray.json._
import client.PostRequest
import client.DeleteRequest
import client.PutRequest
import client.GetRequest
import spray.http.HttpResponse
import core.User
import client.Fail
import client.PatchRequest


trait SprayConnector extends SprayJsonSupport {
  self: { val actorSystem: ActorSystem } =>
  implicit val system = actorSystem
  implicit val timeout = 2.seconds
  implicit val receiveTimeout: Timeout = 2 seconds

  import SprayJsonSupport._
  import system.dispatcher
  private val log = Logging(system, getClass)
  private val gitHubRootPath = "https://api.github.com"


  def requestViaSpray[T: FromResponseUnmarshaller: Manifest](req: GithubRequest): GithubResponse[T] = {
    
    val pipeline = sendReceive ~> unmarshal[T]

    val fullpath = gitHubRootPath + req.url
    val Request = new RequestBuilder(sprayHttpMethod(req))

    val responseFuture = pipeline { Request(fullpath) }
    responseFuture. map {
      //    case Success(GoogleApiResult(_, Elevation(_, elevation) :: _)) =>
      //      log.info("The elevation of Mt. Everest is: {} m", elevation)
      //      shutdown()
      case item: T  =>
        Left(Fail(1))
      case somethingUnexpected: HttpResponse =>
        Left(Fail(1))
      case _ =>
        Left(Fail(1))
    }
  }

  private def sprayHttpMethod(req: GithubRequest) = req match {
    case GetRequest(_) => HttpMethods.GET
    case PostRequest(_) => HttpMethods.POST
    case PutRequest(_) => HttpMethods.PUT
    case PatchRequest(_) => HttpMethods.PATCH
    case DeleteRequest(_) => HttpMethods.DELETE
  }

  private def shutdown(): Unit = {
    IO(Http).ask(Http.CloseAll)(1.second).await
    system.shutdown()
  }
}


