import scala.concurrent.Future

package object client {
  type GithubResponse[T] = Future[Either[Fail, T]]
}
