package graphql.scheme

import model.Role

import java.time.LocalDate

case class UserCreationQuery(email: String,
                             name: String,
                             birthDate: LocalDate,
                             films: List[Long],
                             role: Role)


