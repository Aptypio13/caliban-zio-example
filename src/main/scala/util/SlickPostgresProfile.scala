package util

import com.github.tminglei.slickpg._
import com.github.tminglei.slickpg.ExPostgresProfile

trait SlickPostgresProfile extends ExPostgresProfile with PgArraySupport {
  override val api = MyAPI

  object MyAPI extends API with ArrayImplicits
}

object SlickPostgresProfile extends SlickPostgresProfile
