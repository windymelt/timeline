package windymelt.timeline.infrastructure

import windymelt.timeline.domain.repository.EventRepositoryComponent
import windymelt.timeline.domain.model.EventModelComponent
import windymelt.timeline.domain.model.UserModelComponent
import windymelt.timeline.Types
import windymelt.timeline.domain.repository.UserRepositoryComponent
import windymelt.timeline.domain.model.TimelineModelComponent
import windymelt.timeline.domain.repository.IDComponent

trait DBEventRepositoryComponent extends EventRepositoryComponent {
  self: Database
    with EventModelComponent
    with UserModelComponent
    with TimelineModelComponent
    with IDComponent
    with UserRepositoryComponent =>

  import scalikejdbc._
  import scalikejdbc.jodatime.JodaParameterBinderFactory._
  import scalikejdbc.jodatime.JodaTypeBinder._
  import com.github.nscala_time.time.Imports._

  val eventRepository: EventRepository

  class DBEventRepository extends EventRepository {
    private def getEventFromRs(
        rs: WrappedResultSet
    )(implicit session: DBSession) = {
      val u = userRepository.find(rs.bigInt("editor_user_id")).get
      Event(
        rs.get("id"),
        rs.get("name"),
        rs.get("description"),
        rs.get("occurred_at"),
        rs.get("until"),
        u
      )
    }
    def save(event: Event): Event = {
      println(s"saving event id ${event.id}")
      val resultRows = DB autoCommit { implicit session =>
        sql"""
          INSERT INTO `event`
          SET id=${event.id},
              name=${event.name},
              description=${event.description},
              occurred_at=${event.occurredAt},
              until=${event.until},
              editor_user_id=${event.editor.id}
          ON DUPLICATE KEY UPDATE
            name=VALUES(name),
            description=VALUES(description),
            occurred_at=VALUES(occurred_at),
            until=VALUES(until),
            editor_user_id= VALUES(editor_user_id)
          """.update().apply()
      }
      println(s"modified $resultRows")
      find(event.id).get
    }

    def delete(event: Event): Unit = DB autoCommit { implicit session =>
      sql"DELETE FROM `event` WHERE id=${event.id}".update().apply()
    }

    def find(editor: User): Set[Event] = DB readOnly { implicit session =>
      sql"SELECT * FROM `event` WHERE editor_user_id=${editor.id}"
        .map(getEventFromRs)
        .list()
        .apply()
        .toSet
    }

    def find(eventIds: Seq[Types.ID]): Seq[Event] = DB readOnly {
      implicit session =>
        val lis = sql"SELECT * FROM `event` WHERE id IN (${eventIds})"
          .map(getEventFromRs) // TODO: N+1 issue here
          .list()
          .apply()
          .groupMapReduce(_.id)(identity)((left, _) => left) // idで一意なので先頭だけ取る
        eventIds.flatMap(lis.get)
    }

    def findByTimelineId(timelineId: Types.ID): Seq[Event] = DB readOnly {
      implicit session =>
        val eventIds =
          sql"SELECT event_id FROM timeline_event_relation WHERE timeline_id=${timelineId}"
            .map(_.bigInt("event_id"))
            .list()
            .apply()
        sql"SELECT * FROM `event` WHERE id IN (${eventIds})"
          .map(getEventFromRs)
          .list()
          .apply()
          .sortBy(_.occurredAt)
    }

    // TODO: relation専門のcomponentとして切り出してもよい
    def addRelationToTimeline(timeline: Timeline, events: Set[Event]): Unit =
      DB autoCommit { implicit session =>
        // TODO: use scalikeJDBC batch API
        events foreach { e =>
          val id = ID.gen()
          sql"INSERT INTO timeline_event_relation VALUES ($id, ${timeline.id}, ${e.id})"
            .update()
            .apply()
        }
      }
  }
}
