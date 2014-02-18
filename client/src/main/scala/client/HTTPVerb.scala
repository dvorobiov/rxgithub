package client

sealed abstract class HTTPVerb
case object GET extends HTTPVerb
case object POST extends HTTPVerb
case object PUT extends HTTPVerb
case object PATCH extends HTTPVerb
case object DELETE extends HTTPVerb
