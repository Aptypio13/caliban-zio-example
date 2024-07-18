package model

import zio.json._

case class Review(id: Long,
                  filmId: Option[Long],
                  userId: Option[Long],
                  title: String,
                  description: Option[String])

object Review {
  implicit val decoder: JsonDecoder[Review] =
    DeriveJsonDecoder.gen[Review]

  implicit val encoder: JsonEncoder[Review] =
    DeriveJsonEncoder.gen[Review]
}