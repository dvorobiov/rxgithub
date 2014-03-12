package api

import net.liftweb.json._
import core._

case class UserJson(
  login: String,
  id: Int,
  avatar_url: String,
  gravatar_id: String,
  url: String,
  html_url: String
)
object UserJson {
  def toCore(u: UserJson) = {
    User(u.login, u.id)
  }
}