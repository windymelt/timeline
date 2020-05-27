package windymelt.timeline.infrastructure

import windymelt.timeline.domain.model.TimelineModelComponent
import windymelt.timeline.domain.repository.TimelineRepositoryComponent

trait InmemoryTimelineRepositoryComponent extends TimelineRepositoryComponent {
  self: TimelineModelComponent =>
  val timelineRepository: InmemoryTimelineRepository

  class InmemoryTimelineRepository extends TimelineRepository {
    var db = collection.mutable.Seq[Timeline]()
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
  }
}
