package windymelt.timeline

import javax.servlet.http.HttpServletRequest
import windymelt.timeline.domain.model.UserModelComponent
import windymelt.timeline.domain.repository.UserRepositoryComponent
import windymelt.timeline.domain.service.UserServiceComponent

trait ContextComponent {
  self: UserModelComponent
    with UserRepositoryComponent
    with UserServiceComponent
    with application.dto.TimelineDTOFactoryComponent =>

  def ctxBuilder(
      app: windymelt.timeline.application.App,
      router: Timeline
  )(implicit req: HttpServletRequest): Context = {
    val visitor = self.userRepository.find("guest") match {
      case Some(u) => u
      case None    => self.userService.create("guest").right.get
    }

    Context(app, router, req, self.timelineDTOFactory.toDTO(visitor))
  }

}

// because of twirl aren't part of the cake
final case class Context(
    app: windymelt.timeline.application.App,
    router: Timeline,
    req: HttpServletRequest,
    visitor: application.dto.User
)
