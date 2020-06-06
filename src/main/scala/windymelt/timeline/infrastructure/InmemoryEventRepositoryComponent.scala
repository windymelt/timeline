package windymelt.timeline.infrastructure

import windymelt.timeline.domain.repository.EventRepositoryComponent
import windymelt.timeline.domain.model.EventModelComponent
import windymelt.timeline.domain.model.UserModelComponent

trait InmemoryEventRepositoryComponent extends EventRepositoryComponent {
  self: EventModelComponent with UserModelComponent =>
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

    def find(editor: User): Set[Event] = {
      db.filter(_.editor.id == editor.id).toSet
    }

    def find(eventIds: Seq[BigInt]): Seq[Event] = {
      db.filter { ev =>
        println(s"check ${ev.id} in $eventIds"); eventIds contains ev.id
      }.toSeq
    }
  }
}
