package api

import client.GitHubClient
import core.User
import scala.util.Try

trait UserClient {
  self: GitHubClient =>
  lazy val user: UserApi = new UserApi(self)
}

protected class UserApi(client: GitHubClient) {
  def get(id: Int): Try[Option[User]] = client.request(s"/users/$id")
}