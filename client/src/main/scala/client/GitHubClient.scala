package client

import api.UserClient
import akka.actor.ActorSystem
import core.User
import spray.httpx.unmarshalling._
import scala.Some

class GitHubClient private (credentials: Option[AuthData] = None, actorSystemOpt: Option[ActorSystem] = None) extends
  UserClient with
  SprayConnector {

  lazy val actorSystem = actorSystemOpt getOrElse ActorSystem("github-client")
  def request[T: FromResponseUnmarshaller: Manifest](req: GithubRequest) = requestViaSpray[T](req)
}
case class Fail(code: Int, description: Option[String] = None)

object GitHubClient {
  def init = new GitHubClient(None, None)
  def init(actorSystem: Option[ActorSystem] = None) = new GitHubClient(None, actorSystem)
  def initWithCredenitals(login: String, password: String, actorSystem: Option[ActorSystem] = None) = new GitHubClient(Some(AuthCrendenials(login, password)), actorSystem)
  def initWithToken(token: String, actorSystem: Option[ActorSystem] = None) = new GitHubClient(Some(AuthToken(token)), actorSystem)
}

protected[client] sealed class AuthData
protected[client] case class AuthCrendenials(login: String, password: String) extends AuthData
protected[client] case class AuthToken(token: String) extends AuthData
