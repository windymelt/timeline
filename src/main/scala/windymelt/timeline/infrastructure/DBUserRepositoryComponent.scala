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
    private def getUserFromRs(rs: WrappedResultSet)(
        implicit session: DBSession
    ): User = {
      User(rs.get("id"), rs.get("name"))
    }

    def save(user: User): User = {
      DB autoCommit { implicit session =>
        sql"""
        INSERT INTO `user`
        SET id=${user.id},
            name=${user.name}
        ON DUPLICATE KEY UPDATE name=VALUES(name)
        """.update()
      }
      find(user.id).get
    }

    def find(id: Types.ID): Option[User] = DB readOnly { implicit session =>
      sql"SELECT * FROM `user` WHERE id=${id}"
        .map(getUserFromRs)
        .headOption()
    }

    def find(name: String): Option[User] = DB readOnly { implicit session =>
      sql"SELECT * FROM `user` WHERE name=${name}"
        .map(getUserFromRs)
        .headOption()
    }

    def delete(user: User): Unit = DB autoCommit { implicit session =>
      sql"DELETE FROM `user` WHERE id=${user.id}".update()
    }

  }

}
