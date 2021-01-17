package windymelt.timeline.application

import windymelt.timeline.domain.model._
import windymelt.timeline.domain.service._
import windymelt.timeline.infrastructure._

class App
    extends Database
    with UserModelComponent
    with EventModelComponent
    with TimelineModelComponent
    with ConcreteUserServiceComponent
    with ConcreteEventServiceComponent
    with ConcreteTimelineServiceComponent
    with DBIDComponent
    with DBUserRepositoryComponent
    with DBEventRepositoryComponent
    with DBTimelineRepositoryComponent
    with windymelt.timeline.application.dto.TimelineDTOFactoryComponent {
  val userService: ConcreteUserService = new ConcreteUserService()
  val eventService: ConcreteEventService = new ConcreteEventService()
  val timelineService: TimelineService = new ConcreteTimelineService()
  val ID: IDFactory = new DBIDFactory()
  val userRepository: UserRepository = new DBUserRepository()
  val eventRepository: EventRepository = new DBEventRepository()
  val timelineRepository: TimelineRepository =
    new DBTimelineRepository()
  val timelineDTOFactory: TimelineDTOFactory = new TimelineDTOFactory()
}
