package api

import client.{GithubResponse, GetRequest, GitHubClient}
import core.User
import scala.util.Try
import spray.json._
import DefaultJsonProtocol._
import spray.httpx.SprayJsonSupport._
import client.GetRequest
import core.User

trait UserClient {
  self: GitHubClient =>
  lazy val user: UserApi = new UserApi(self)
}

protected class UserApi(client: GitHubClient) {

  def get(id: Int): GithubResponse[User] = client.request[User](GetRequest(s"/users/$id"))


  implicit object UserJsonFormat extends RootJsonFormat[User] {
    def write(u: User) = JsObject(
      "login" -> JsString(u.login),
      "id" -> JsNumber(u.id),
      "avatar_url" -> JsString(u.avatarUrl),
      "html_url" -> JsNumber(u.htmlUrl),
      "followers_url" -> JsString(u.followersUrl),
      "following_url" -> JsString(u.followingUrl),
      "gistsUrl" -> JsString(u.gistsUrl)

    )
    def read(value: JsValue) = {
      value.asJsObject.getFields("name", "red", "green", "blue") match {
        case Seq(JsString(name), JsNumber(red), JsNumber(green), JsNumber(blue)) =>
          new User(name, red.toInt, green.toInt, blue.toInt)
        case _ => throw new DeserializationException("Color expected")
      }
    }
  }
}