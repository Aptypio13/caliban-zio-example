package repository

import com.typesafe.config.ConfigFactory
import slick.interop.zio.DatabaseProvider
import slick.jdbc.PostgresProfile
import zio.{RLayer, Task, ZLayer}

trait DAO[E, I] {
  def create(entity: E): Task[E]
  def read(id: I): Task[Option[E]]
  def update(id: I, entity: E): Task[Option[E]]
  def delete(id: I):Task[Unit]
}

object DAO {
  private val config = ConfigFactory.load()

  val databaseProviderLayer: RLayer[Any, DatabaseProvider] = {
    (ZLayer.succeed(config) ++ ZLayer.succeed[slick.jdbc.PostgresProfile](PostgresProfile)) >>> DatabaseProvider.fromConfig("pgs")
  }
}
