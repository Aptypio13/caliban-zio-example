package repository.h2.tables

import model._
import slick.lifted._
import util.SlickPostgresProfile.api._

import java.time.LocalDate

case class UserTable(tag: Tag) extends Table[User](tag, Some("public"), "users") {

  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def email = column[String]("email")
  def name = column[String]("name")
  def birthDay = column[LocalDate]("birthday")
  def filmsId = column[List[Long]]("films_id")
  def role = column[Role]("role")


  override def * : ProvenShape[User] = (id, email, name, birthDay, filmsId, role) <> ((User.apply _).tupled, User.unapply)
}
