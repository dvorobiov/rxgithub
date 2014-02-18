package client

import api.UserClient
import akka.actor.ActorSystem

class GitHubClient private (credentials: Option[AuthData] = None, actorSystemOpt: Option[ActorSystem] = None) extends
  UserClient with
  SprayConnector {

  lazy val actorSystem = actorSystemOpt getOrElse ActorSystem("github-client")
  def request(path: String) = connectorRequest(path: String)
}

object GitHubClient {
  def init = new GitHubClient(None, None)
  def init(actorSystem: Option[ActorSystem] = None) = new GitHubClient(None, actorSystem)
  def initWithCredenitals(login: String, password: String, actorSystem: Option[ActorSystem] = None) = new GitHubClient(Some(AuthCrendenials(login, password)), actorSystem)
  def initWithToken(token: String, actorSystem: Option[ActorSystem] = None) = new GitHubClient(Some(AuthToken(token)), actorSystem)
}

protected[client] sealed class AuthData
protected[client] case class AuthCrendenials(login: String, password: String) extends AuthData
protected[client] case class AuthToken(token: String) extends AuthData
