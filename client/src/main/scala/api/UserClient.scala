package api

import client.{GithubResponse, GetRequest, GitHubClient}
import core.User
import scala.util.Try

trait UserClient {
  self: GitHubClient =>
  lazy val user: UserApi = new UserApi(self)
}

protected class UserApi(client: GitHubClient) {
  def get(id: Int): GithubResponse[User] = client.request[User](GetRequest(s"/users/$id"))
}