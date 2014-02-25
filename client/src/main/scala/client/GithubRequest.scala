package client

sealed abstract class GithubRequest(val url: String)
case class GetRequest(override val url: String) extends GithubRequest(_)
case class PostRequest(override val url: String) extends GithubRequest(_)
case class PutRequest(override val url: String) extends GithubRequest(_)
case class PatchRequest(override val url: String) extends GithubRequest(_)
case class DeleteRequest(override val url: String) extends GithubRequest(_)
