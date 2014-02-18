package api

import client.GitHubClient
import core.User

trait UserClient {
  self: GitHubClient =>
  lazy val user: UserApi = new UserApi(self)
}

protected class UserApi(client: GitHubClient) {
  def get(id: Int) = client.request(s"/users/$id")
}