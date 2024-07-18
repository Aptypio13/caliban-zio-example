package graphql.scheme

import model.{Film, Review, Role}

import java.time.LocalDate

case class UserGetQuery(id: Option[Long],
                        email: Option[String],
                        name: Option[String],
                        birthDate: Option[LocalDate],
                        filmsId: Option[List[Long]],
                        role: Option[Role])
