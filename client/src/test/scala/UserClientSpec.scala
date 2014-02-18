import client.GitHubClient
import org.scalatest.{ShouldMatchers, FlatSpec}
import spray.util._

class UserClientSpec  extends FlatSpec with ShouldMatchers {
  "UserClient" should "return correct User by Id" in {
    val client = GitHubClient.init()
    val res = client.user.get(1).await
    res.isEmpty should be(false)
  }
}
