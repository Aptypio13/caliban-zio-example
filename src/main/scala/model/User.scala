package model

import java.time.LocalDate

case class User(id: Option[Long],
                email: String,
                name: String,
                birthDate: LocalDate,
                filmsId: List[Long],
                role: Role)
