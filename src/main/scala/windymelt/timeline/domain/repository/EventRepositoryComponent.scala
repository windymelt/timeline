package windymelt.timeline.domain.repository
        
import windymelt.timeline.domain.model.Event

trait EventRepositoryComponent {
    val eventRepository: EventRepository

    trait EventRepository {
        def save(event: Event): Event
        def delete(event: Event): Unit
    }
}