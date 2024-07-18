package graphql.scheme

import model.Review

case class FilmCreationQuery(
                              name: String,
                              rating: Option[Int],
                              description: Option[String],
                            )
