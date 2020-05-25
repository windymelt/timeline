package windymelt.timeline.domain.service

import windymelt.timeline.domain.model.Event
import windymelt.timeline.domain.DomainError

trait EventServiceComponent {
    val eventService:EventService

    trait EventService {
        def delete(event: Event): Either[DomainError, Unit]
    }
}