package windymelt.timeline.infrastructure

import windymelt.timeline.domain.repository.EventRepositoryComponent
import windymelt.timeline.domain.model.EventModelComponent
import windymelt.timeline.domain.model.UserModelComponent
import windymelt.timeline.Types
import windymelt.timeline.domain.repository.UserRepositoryComponent

trait DBUserRepositoryComponent extends UserRepositoryComponent {
  self: Database with UserModelComponent =>

  import scalikejdbc._
  import scalikejdbc.jodatime.JodaParameterBinderFactory._
  import scalikejdbc.jodatime.JodaTypeBinder._
  import com.github.nscala_time.time.Imports._

  val userRepository: UserRepository

  class DBUserRepository extends UserRepository {
    val table = "user"

    private def getUserFromRs(rs: WrappedResultSet)(
        implicit session: DBSession
    ): User = {
      User(rs.get("id"), rs.get("name"))
    }

    def save(user: User): User = {
      DB autoCommit { implicit session =>
        sql"""
        INSERT INTO $table
        SET id=${user.id},
            name=${user.name}
        ON DUPLICATE KEY UPDATE name=VALUES(name)
        """.update().apply()
        find(user.id).get
      }
    }

    def find(id: Types.ID): Option[User] = DB readOnly { implicit session =>
      sql"SELECT * FROM $table WHERE id=${id}"
        .map(getUserFromRs)
        .headOption()
        .apply()
    }

    def find(name: String): Option[User] = DB readOnly { implicit session =>
      sql"SELECT * FROM $table WHERE name=${name}"
        .map(getUserFromRs)
        .headOption()
        .apply()
    }

    def delete(user: User): Unit = DB autoCommit { implicit session =>
      sql"DELETE FROM $table WHERE id=${user.id}".update().apply()
    }

  }

}
