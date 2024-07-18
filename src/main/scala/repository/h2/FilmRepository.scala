package repository.h2

import model.Film
import repository.DAO
import slick.interop.zio.DatabaseProvider
import zio._

trait FilmRepository extends DAO[Film, Long] {

  override def create(entity: Film): Task[Film]

  override def read(id: Long): Task[Option[Film]]

  override def update(id: Long, entity: Film): Task[Option[Film]]

  override def delete(id: Long): Task[Unit]

  def readBulk(filmsId: List[Long]): Task[List[Film]]

}

object FilmRepository {
  val live :RLayer[DatabaseProvider, FilmRepository] = ZLayer.fromFunction{
    new FilmRepositoryImpl(_)
  }.debug
}
