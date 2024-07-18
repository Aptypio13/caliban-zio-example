package model

import zio.json._

case class Film(id: Option[Long],
                name: String,
                rating: Option[Int],
                description: Option[String])

object Film {
  implicit val decoder: JsonDecoder[Film] =
    DeriveJsonDecoder.gen[Film]

  implicit val encoder: JsonEncoder[Film] =
    DeriveJsonEncoder.gen[Film]
}
