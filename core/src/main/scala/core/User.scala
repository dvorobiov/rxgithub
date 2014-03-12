package core

import org.joda.time.DateTime

case class User(
  login: String,
  id: Int,
  avatarUrl: String,
  gravatarId: String,
  url: String,
  htmlUrl: String,
  followersUrl: String,
  followingUrl: String,
  gistsUrl: String,
  starredUrl: String,
  subscriptionsUrl: String,
  organizationsUrl: String,
  reposUrl: String,
  eventsUrl: String,
  receivedEventsUrl: String,
  `type`: String,
  siteAdmin: Boolean,
  name: String,
  company: String,
  blog: String,
  location: String,
  email: String,
  hireable: Boolean,
  bio: String,
  publicRepos: Int,
  publicGists: Int,
  followers: Int,
  following: Int,
  createdAt: DateTime,
  updatedAt: DateTime
)
