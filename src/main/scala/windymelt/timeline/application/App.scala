package windymelt.timeline.application

import windymelt.timeline.domain.model._
import windymelt.timeline.domain.service._
import windymelt.timeline.infrastructure._

class App
    extends UserModelComponent
    with EventModelComponent
    with TimelineModelComponent
    with ConcreteUserServiceComponent
    with ConcreteEventServiceComponent
    with ConcreteTimelineServiceComponent
    with InmemoryIdComponent
    with InmemoryUserRepositoryComponent
    with InmemoryEventRepositoryComponent
    with InmemoryTimelineRepositoryComponent
    with windymelt.timeline.application.dto.TimelineDTOFactoryComponent {
  val userService: ConcreteUserService = new ConcreteUserService()
  val eventService: ConcreteEventService = new ConcreteEventService()
  val timelineService: TimelineService = new ConcreteTimelineService()

  val ID: InMemoryIDFactory = new InMemoryIDFactory()

  val userRepository: InmemoryUserRepository = new InmemoryUserRepository()
  val eventRepository: InmemoryEventRepository = new InmemoryEventRepository()
  val timelineRepository: InmemoryTimelineRepository =
    new InmemoryTimelineRepository()
  val timelineDTOFactory: TimelineDTOFactory = new TimelineDTOFactory()
}
