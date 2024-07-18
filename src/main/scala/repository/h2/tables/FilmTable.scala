package repository.h2.tables

import model.Film
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class FilmTable (tag: Tag) extends Table[Film](tag, Some("public"), "films") {

  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def rating = column[Option[Int]]("rating")

  def description = column[Option[String]]("description")

  override def * = (id, name, rating, description) <> ((Film.apply _).tupled, Film.unapply)
}
