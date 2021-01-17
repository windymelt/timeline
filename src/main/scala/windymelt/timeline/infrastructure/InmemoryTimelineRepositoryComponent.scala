package windymelt.timeline.infrastructure

import windymelt.timeline.domain.model.TimelineModelComponent
import windymelt.timeline.domain.repository.TimelineRepositoryComponent
import windymelt.timeline.domain.model.UserModelComponent

trait InmemoryTimelineRepositoryComponent extends TimelineRepositoryComponent {
  self: TimelineModelComponent with UserModelComponent =>
  val timelineRepository: InmemoryTimelineRepository

  class InmemoryTimelineRepository extends TimelineRepository {
    var db = collection.mutable.Seq[Timeline]()
    def find(id: BigInt): Option[Timeline] = {
      db.find(_.id == id)
    }
    def save(timeline: Timeline): Timeline =
      db.find(_.id == timeline.id) match {
        case Some(value) =>
          db = db.filterNot(_.id == timeline.id)
          db = db :+ timeline
          timeline
        case None =>
          db = db :+ timeline
          timeline
      }
    def delete(timeline: Timeline): Unit = {
      db = db.filterNot(_.id == timeline.id)
    }
    def findByEditor(user: User): Set[Timeline] = ???
  }
}
