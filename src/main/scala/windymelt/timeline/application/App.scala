package windymelt.application

class App extends windymelt.timeline.domain.model.UserModelComponent
with windymelt.timeline.domain.service.ConcreteUserServiceComponent
with windymelt.timeline.infrastructure.InmemoryIdComponent
with windymelt.timeline.infrastructure.InmemoryUserRepositoryComponent
with windymelt.timeline.infrastructure.InmemoryEventRepositoryComponent {
    val UserService: ConcreteUserService = new ConcreteUserService()
    
    val ID: InMemoryIDFactory = new InMemoryIDFactory()
    
    val userRepository: InmemoryUserRepository = new InmemoryUserRepository()
    val eventRepository: InmemoryEventRepository = new InmemoryEventRepository()
}