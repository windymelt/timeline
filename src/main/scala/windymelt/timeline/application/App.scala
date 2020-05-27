package windymelt.application

import windymelt.timeline.domain.model._
import windymelt.timeline.domain.service._
import windymelt.timeline.infrastructure._

class App
    extends UserModelComponent
    with EventModelComponent
    with TimelineModelComponent
    with ConcreteUserServiceComponent
    with InmemoryIdComponent
    with InmemoryUserRepositoryComponent
    with InmemoryEventRepositoryComponent
    with InmemoryTimelineRepositoryComponent {
  val userService: ConcreteUserService = new ConcreteUserService()

  val ID: InMemoryIDFactory = new InMemoryIDFactory()

  val userRepository: InmemoryUserRepository = new InmemoryUserRepository()
  val eventRepository: InmemoryEventRepository = new InmemoryEventRepository()
  val timelineRepository: InmemoryTimelineRepository =
    new InmemoryTimelineRepository()
}
