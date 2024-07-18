import caliban._
import caliban.quick._
import graphql.{GraphqlApi, GraphqlService}
import repository.DAO
import repository.h2.{FilmRepository, UserRepository}
import zio._
import zio.http._

object Main extends ZIOAppDefault {

  override def run = {
    (for {
      api       <- ZIO.service[GraphQL[GraphqlService with UserRepository with FilmRepository]].debug
      handlers  <- api.handlers.map(_.api).debug
      graphiql  = GraphiQLHandler.handler(apiPath = "/api/graphql", graphiqlPath = "/graphiql")
      _         <- ZIO.logInfo(s"Server started on : ${Server.Config.default}")
      _      <- Server.serve(
        Routes(
          Method.ANY / "api" / "graphql" -> handlers,
          Method.GET / "graphql"        -> graphiql,
      )).debug

    } yield ())
      .provide(
        Server.default,
        DAO.databaseProviderLayer,
        FilmRepository.live,
        UserRepository.live,
        GraphqlService.live,
        GraphqlApi.live
      ).forever
  }
}
