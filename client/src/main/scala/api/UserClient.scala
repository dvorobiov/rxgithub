package api

import client.{GithubResponse, GetRequest, GitHubClient}
import core.User
import scala.util.Try
import spray.json._

import client.GetRequest
import core.User
import org.joda.time.DateTime
import spray.httpx.SprayJsonSupport
import spray.httpx.unmarshalling.{FromResponseUnmarshaller, UnmarshallerLifting}

trait UserClient {
  self: GitHubClient =>
  lazy val user: UserApi = new UserApi(self)
}
protected class UserApi(client: GitHubClient) extends SprayJsonSupport {
  implicit val formats = UserJsonFormat
  def get(id: Int): GithubResponse[User] = client.request[User](GetRequest(s"/users/$id"))

}

object UserJsonFormat extends RootJsonFormat[User] {
  def write(u: User) = JsObject(
    "login" -> JsString(u.login),
    "id" -> JsNumber(u.id),
    //      "avatar_url" -> JsString(u.avatarUrl),
    "gravatar_id" -> JsString(u.gravatarId),
    //      "url" -> JsString(u.url),
    //      "html_url" -> JsNumber(u.htmlUrl),
    //      "followers_url" -> JsString(u.followersUrl),
    //      "following_url" -> JsString(u.followingUrl),
    //      "gistsUrl" -> JsString(u.gistsUrl),
    //      "starred_url" -> JsString(u.starredUrl),
    //      "subscriptions_url" -> JsString(u.subscriptionsUrl),
    //      "organizations_url" -> JsString(u.organizationsUrl),
    //      "repos_url" -> JsString(u.reposUrl),
    //      "events_url" -> JsString(u.eventsUrl),
    //      "received_events_url" -> JsString(u.receivedEventsUrl),
    "type" -> JsString(u.`type`),
    "site_admin" -> JsBoolean(u.siteAdmin),
    "name" -> JsString(u.name),
    "company" -> JsString(u.company),
    "blog" -> JsString(u.blog),
    "location" -> JsString(u.location),
    "email" -> JsString(u.email),
    "hireable" -> JsBoolean(u.hireable),
    "bio" -> JsString(u.bio),
    "public_repos" -> JsNumber(u.publicRepos),
    "public_gists" -> JsNumber(u.publicGists),
    "followers" -> JsNumber(u.followers),
    "following" -> JsNumber(u.following)
    //      "created_at" -> JsString(u.createdAt.toString),
    //      "updated_at" -> JsString(u.updatedAt.toString)
  )

  def read(value: JsValue) = {
    val jsObj = value.asJsObject.getFields(
      "login",
      "id",
      //   "avatar_url",
      "gravatar_id",
      //        "url",
      //        "html_url",
      //        "followers_url",
      //        "following_url",
      //        "gistsUrl",
      //        "starred_url",
      //        "subscriptions_url",
      //        "organizations_url",
      //        "repos_url",
      //        "events_url",
      //        "received_events_url",
      "type",
      "site_admin",
      "name",
      "company",
      "blog",
      "location",
      "email",
      "hireable",
      "bio",
      "public_repos",
      "public_gists",
      "followers",
      "following"
      //        "created_at",
      //        "updated_at"
    )

    jsObj match {
      case Seq(
      JsString(login),
      JsNumber(id),
      //  JsString(avatarUrl),
      JsString(gravatarId),
      //          JsString(url),
      //          JsString(htmlUrl),
      //          JsString(followersUrl),
      //          JsString(followingUrl),
      //          JsString(gistsUrl),
      //          JsString(starredUrl),
      //          JsString(subscriptionsUrl),
      //          JsString(organizationsUrl),
      //          JsString(reposUrl),
      //          JsString(eventsUrl),
      //          JsString(receivedEventsUrl),
      JsString(typeField),
      JsBoolean(siteAdmin),
      JsString(name),
      JsString(company),
      JsString(blog),
      JsString(location),
      JsString(email),
      JsBoolean(hireable),
      JsString(bio),
      JsNumber(publicRepos),
      JsNumber(publicGists),
      JsNumber(followers),
      JsNumber(following)
      //   JsString(createdAt),
      //   JsString(updatedAt)
      ) =>
        new User(
          login,
          id.toInt,
          //  avatarUrl,
          gravatarId,
          //            url,
          //            htmlUrl,
          //            followersUrl,
          //            followingUrl,
          //            gistsUrl,
          //            starredUrl,
          //            subscriptionsUrl,
          //            organizationsUrl,
          //            reposUrl,
          //            eventsUrl,
          //            receivedEventsUrl,
          typeField,
          siteAdmin,
          name,
          company,
          blog,
          location,
          email,
          hireable,
          bio,
          publicRepos.toInt,
          publicGists.toInt,
          followers.toInt,
          following.toInt
          //  new DateTime(createdAt),
          //     new DateTime(updatedAt)
        )
      case _ => throw new DeserializationException("Incorrect deserialization")
    }
  }
}