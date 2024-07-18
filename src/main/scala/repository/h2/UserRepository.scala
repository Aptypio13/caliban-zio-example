package repository.h2

import model.User
import repository.DAO
import slick.interop.zio.DatabaseProvider
import zio._

trait UserRepository extends DAO[User, Long]{
  override def create(entity: User): Task[User]

  override def read(id: Long): Task[Option[User]]

  override def update(id: Long, entity: User): Task[Option[User]]

  override def delete(id: Long): Task[Unit]

}

object UserRepository {
  val live :ZLayer[DatabaseProvider, Throwable, UserRepository] = ZLayer.fromFunction{
    new UserRepositoryImpl(_)
  }
}
