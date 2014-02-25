import scala.concurrent.Future

package object client {
  type RequestResult[T] = Future[Either[Fail, T]]
}
