package graphql

import caliban._
import caliban.schema.Annotations.GQLDescription
import caliban.schema.GenericSchema
import graphql.scheme.{FilmCreationQuery, UserCreationQuery, UserGetQuery}
import model.{Film, User}
import repository.h2.{FilmRepository, UserRepository}
import zio._

object GraphqlApi extends GenericSchema[GraphqlService with UserRepository with FilmRepository] {
  import auto._
  import caliban.schema.ArgBuilder.auto._

  case class Queries(@GQLDescription("Get user by name")
                      getUser: UserGetQuery => RIO[GraphqlService, Option[(User, List[Film])]],
                      getFilm: Long => RIO[GraphqlService, Option[Film]]
                    )

  case class Mutations(@GQLDescription("Add user")
                       addUser: UserCreationQuery => RIO[GraphqlService, Option[(User, List[Film])]],
                       addFilm: FilmCreationQuery => RIO[GraphqlService, Option[Film]]

  )

  val graphqlService = ZIO.serviceWithZIO[GraphqlService]

  def api: GraphQL[GraphqlService with UserRepository with FilmRepository] =
    graphQL(
    RootResolver(
      Queries(
        args => graphqlService(_.getUser(args)),
        args => graphqlService(_.getFilm(args))
      ),
      Mutations(
        user => graphqlService(_.createUser(user)),
        film => graphqlService(_.createFilm(film))
      )
    )
  )

  def live : ZLayer[Any, Throwable, GraphQL[GraphqlService with UserRepository with FilmRepository]] = {
    ZLayer.succeed(api)
  }
}
