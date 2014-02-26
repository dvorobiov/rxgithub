package client

sealed abstract class GithubRequest(val url: String)
case class GetRequest(override val url: String) extends GithubRequest(url)
case class PostRequest(override val url: String) extends GithubRequest(url)
case class PutRequest(override val url: String) extends GithubRequest(url)
case class PatchRequest(override val url: String) extends GithubRequest(url)
case class DeleteRequest(override val url: String) extends GithubRequest(url)
