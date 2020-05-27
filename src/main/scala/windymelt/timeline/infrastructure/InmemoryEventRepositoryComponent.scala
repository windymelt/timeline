package windymelt.timeline.infrastructure

import windymelt.timeline.domain.repository.EventRepositoryComponent
import windymelt.timeline.domain.model.EventModelComponent

trait InmemoryEventRepositoryComponent extends EventRepositoryComponent {
  self: EventModelComponent =>
  val eventRepository: EventRepository

  class InmemoryEventRepository extends EventRepository {
    var db = collection.mutable.Seq[Event]()

    def save(event: Event): Event = db.find(_.id == event.id) match {
      case Some(value) =>
        db = db.filterNot(_.id == event.id)
        db = db :+ event
        event
      case None =>
        db = db :+ event
        event
    }

    def delete(event: Event): Unit = {
      db = db.filterNot(_.id == event.id)
    }

  }
}
