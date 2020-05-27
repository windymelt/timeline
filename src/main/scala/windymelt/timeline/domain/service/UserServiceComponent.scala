package windymelt.timeline.domain.service

import windymelt.timeline.domain.model._
import windymelt.timeline.domain.DomainError

trait UserServiceComponent {
  self: windymelt.timeline.domain.model.UserModelComponent =>
  val userService: UserService

  object UserAlreadyExistsError extends DomainError {
    override val message = "User Already Exists"
  }

  trait UserService {
    def create(name: String): Either[UserAlreadyExistsError.type, User]
    def delete(user: User): Unit
  }
}

trait ConcreteUserServiceComponent extends UserServiceComponent {
  self: windymelt.timeline.domain.model.UserModelComponent
    with windymelt.timeline.domain.repository.UserRepositoryComponent
    with windymelt.timeline.domain.repository.IDComponent =>

  override val userService: ConcreteUserService

  class ConcreteUserService extends UserService {
    def create(name: String): Either[UserAlreadyExistsError.type, User] = {
      userRepository.find(name) match {
        case Some(_) => Left(UserAlreadyExistsError)
        case None => {
          val user = User(id = ID.gen(), name = name)
          userRepository.save(user)
          Right(user)
        }
      }
    }

    def delete(user: User): Unit = {
      userRepository.delete(user)
    }
  }
}
