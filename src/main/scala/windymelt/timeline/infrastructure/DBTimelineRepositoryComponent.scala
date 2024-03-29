package windymelt.timeline.infrastructure

import windymelt.timeline.domain.repository.EventRepositoryComponent
import windymelt.timeline.domain.model.EventModelComponent
import windymelt.timeline.domain.model.UserModelComponent
import windymelt.timeline.Types
import windymelt.timeline.domain.repository.TimelineRepositoryComponent
import windymelt.timeline.domain.model.TimelineModelComponent

trait DBTimelineRepositoryComponent extends TimelineRepositoryComponent {
  self: Database
    with EventRepositoryComponent
    with EventModelComponent
    with UserModelComponent
    with TimelineModelComponent =>

  import scalikejdbc._
  import scalikejdbc.jodatime.JodaParameterBinderFactory._
  import scalikejdbc.jodatime.JodaTypeBinder._
  import com.github.nscala_time.time.Imports._

  val timelineRepository: TimelineRepository

  class DBTimelineRepository extends TimelineRepository {
    private def getTimelineFromRs(
        rs: WrappedResultSet
    )(implicit session: DBSession) = {
      val u =
        sql"SELECT id, name FROM user WHERE id=${rs.bigInt("editor_user_id")}"
          .map(r => User(r.bigInt("id"), r.string("name")))
          .single()
          .get
      Timeline(rs.get("id"), rs.get("title"), u, Set()) // TODO
    }

    def find(id: BigInt): Option[Timeline] = DB readOnly { implicit session =>
      sql"SELECT * FROM `timeline` WHERE id=${id}"
        .map(getTimelineFromRs)
        .single()
    }

    def findByEditor(user: User): Set[Timeline] = DB readOnly {
      implicit session =>
        sql"SELECT * FROM `timeline` WHERE editor_user_id=${user.id}"
          .map(getTimelineFromRs)
          .list()
          .toSet
    }

    def save(timeline: Timeline): Timeline = {
      DB autoCommit { implicit session =>
        sql"""
      INSERT INTO `timeline`
      SET id=${timeline.id},
        title=${timeline.title},
        editor_user_id=${timeline.editor.id}
      """.update()
      }
      find(timeline.id).get
    }

    def delete(timeline: Timeline): Unit = ???

  }
}
