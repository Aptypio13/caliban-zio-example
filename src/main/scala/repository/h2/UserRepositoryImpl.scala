package repository.h2

import model.User
import repository.h2.tables.UserTable
import slick.interop.zio.DatabaseProvider
import slick.interop.zio.syntax.ZIOObjOps
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery
import zio._

import scala.language.{implicitConversions, postfixOps}


class UserRepositoryImpl(db: DatabaseProvider) extends UserRepository {

  private val collection = TableQuery[UserTable]

  override def create(entity: User): Task[User] = {
    ZIO.fromDBIO((collection returning collection.map(_.id) into ((entity, id) => entity.copy(id = id))) += entity)
      .provide(ZLayer.succeed(db)).debug
  }

  override def read(id: Long): Task[Option[User]] = {
    ZIO.fromDBIO(collection.filter(_.id === id)
      .result)
      .provide(ZLayer.succeed(db))
      .map(_.headOption).debug
  }

  override def update(id: Long, entity: User): Task[Option[User]] =
    ZIO.fromDBIO(collection.filter(_.id === id)
        .update(entity))
      .provide(ZLayer.succeed(db))
      .map(result => if (result > 0) Some(entity) else None).debug

  override def delete(id: Long): Task[Unit] = ZIO.fromDBIO(collection.filter(_.id === id).delete)
    .provide(ZLayer.succeed(db))
    .map(result => if (result > 0) () else ZIO.fail(new Throwable("Invalid id, can't delete entity")))
}


