package repository.h2

import model.Film
import repository.h2.tables.FilmTable
import slick.interop.zio.DatabaseProvider
import slick.interop.zio.syntax.ZIOObjOps
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery
import zio._


class FilmRepositoryImpl(db: DatabaseProvider) extends FilmRepository {

  private val collection = TableQuery[FilmTable]

  override def create(entity: Film): Task[Film] =
    ZIO.fromDBIO((collection returning collection.map(_.id) into ((entity, id) => entity.copy(id = id))) += entity)
      .provide(ZLayer.succeed(db)).debug


  override def read(id: Long): Task[Option[Film]] =
    ZIO.fromDBIO(collection.filter(_.id === id)
      .result)
      .provide(ZLayer.succeed(db))
      .map(_.headOption).debug

  override def update(id: Long, entity: Film): Task[Option[Film]] =
    ZIO.fromDBIO(collection.filter(_.id === id)
        .update(entity))
      .provide(ZLayer.succeed(db))
      .map(result => if (result > 0) Some(entity) else None).debug

  override def delete(id: Long): Task[Unit] = ZIO.fromDBIO(collection.filter(_.id === id).delete)
    .provide(ZLayer.succeed(db))
    .map(result => if (result > 0) () else ZIO.fail(new Throwable("Invalid id, can't delete entity")))

  override def readBulk(filmsId: List[Long]): Task[List[Film]] =
    ZIO.fromDBIO(collection.filter(_.id inSet filmsId).result)
      .provide(ZLayer.succeed(db)).map(_.toList).debug
}

object FilmRepositoryImpl {
val live: ZLayer[DatabaseProvider,Throwable, FilmRepository] =
  ZLayer.fromFunction(new FilmRepositoryImpl(_))
}
