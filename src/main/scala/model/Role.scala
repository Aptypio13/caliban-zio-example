package model

import slick.jdbc.H2Profile.api._

sealed trait Role

object Role {

  case object Admin extends Role

  case object User extends Role

  /**
   *  mapper for slick driver
   */
  implicit val roleMapper = MappedColumnType.base[Role, String](
    {
      case Admin => "admin"
      case User => "user"
    },
    {
      case "admin" => Admin
      case "user" => User
    }
  )
}