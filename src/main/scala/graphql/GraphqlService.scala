package graphql

import graphql.scheme._
import model._
import repository.h2.{FilmRepository, UserRepository}
import zio._


trait GraphqlService {
  def getUser(userGetQuery: UserGetQuery): Task[Option[(User, List[Film])]]

  def createUser(userQuery: UserCreationQuery): Task[Option[(User, List[Film])]]

  def getFilm(id: Long): Task[Option[Film]]

  def createFilm(filmCreationQuery: FilmCreationQuery): Task[Option[Film]]

}

object GraphqlService {
  val live: ZLayer[UserRepository with FilmRepository, Throwable, GraphqlService] = {
    ZLayer {
      for {
        userRepo <- ZIO.service[UserRepository]
        filmRepo <- ZIO.service[FilmRepository]
      } yield new GraphqlService {
        override def getUser(userGetQuery: UserGetQuery): Task[Option[(User, List[Film])]] = {
          userGetQuery.id match {
            case Some(value) =>
              for {
                userOpt <- userRepo.read(value).debug
                films <- userOpt match {
                  case Some(user) => filmRepo.readBulk(user.filmsId).debug
                  case None => filmRepo.readBulk(List.empty)
                }
              } yield userOpt.map((_, films))
            case None => ZIO.fail(new Throwable("Can't find user id"))
          }
        }.debug

        override def createUser(userQuery: UserCreationQuery): Task[Option[(User, List[Film])]] = {
          for {
            user <- userRepo.create(
              User(None,
                userQuery.email,
                userQuery.name,
                userQuery.birthDate,
                userQuery.films,
                userQuery.role)
            ).map(Option(_))
            films <- filmRepo.readBulk(userQuery.films)
          } yield user.map(u => (u, films))
        }.debug

        override def getFilm(id: Long): Task[Option[Film]] =
          filmRepo.read(id).debug

        override def createFilm(filmCreationQuery: FilmCreationQuery): Task[Option[Film]] =
          {
            filmRepo.create(
              Film(
                None,
                filmCreationQuery.name,
                filmCreationQuery.rating,
                filmCreationQuery.description
              )
            ).option
          }.debug
      }
    }
  }
}
