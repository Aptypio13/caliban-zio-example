package graphql.scheme

case class FilmGetQuery(
                         id: Option[Long],
                         name: Option[String],
                         rating: Option[Int],
                         description: Option[String],
                       )
