import client.GitHubClient
import org.scalatest.{ShouldMatchers, FlatSpec}
import spray.util._

class UserClientSpec  extends FlatSpec with ShouldMatchers {
  lazy val client = GitHubClient.init()


  "UserClient" should "return correct User by Id" in {
    val res = client.user.get(1).await
    val user = res.toOption.get
    user
    //res.toOption.get. should be(false)
  }
}
