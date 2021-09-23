package graphql

import org.eclipse.jetty.server.Authentication.User

object Types {
  import sangria.macros.derive._
  import sangria.schema._
  import org.joda.time.DateTime

  type Ctx = windymelt.timeline.application.App

  import sangria.validation.ValueCoercionViolation
  case object DateTimeCoercionViolation
      extends ValueCoercionViolation("DateTime value expected")
  implicit val DateTimeType = ScalarType[org.joda.time.DateTime](
    "DateTime",
    coerceOutput = (dt: DateTime, _) => dt.toString(),
    coerceUserInput = {
      case s: String => Right(DateTime.parse(s)) // fix use error handling
      case _         => Left(DateTimeCoercionViolation)
    },
    coerceInput = {
      case sangria.ast.StringValue(s, _, _, _, _) =>
        Right(DateTime.parse(s)) // fix use error handling
      case _ => Left(DateTimeCoercionViolation)
    }
  )
  implicit val DateTimeCategoryType = EnumType(
    "DateTimeCategory",
    Some("DateTime Type"),
    List(
      EnumValue("HOUR", value = windymelt.timeline.application.dto.HourLevel),
      EnumValue("DAY", value = windymelt.timeline.application.dto.DayLevel),
      EnumValue("MONTH", value = windymelt.timeline.application.dto.MonthLevel),
      EnumValue("YEAR", value = windymelt.timeline.application.dto.YearLevel)
    )
  )
  implicit lazy val UserType =
    deriveObjectType[Ctx, windymelt.timeline.application.dto.User](
      AddFields(
        Field(
          "eventsCreated",
          ListType(EventType),
          arguments = Nil,
          resolve = ctx => {
            val u = ctx.ctx.userRepository.find(BigInt(ctx.value.id)).get
            val events =
              ctx.ctx.eventService.findByEditor(u)
            val eventsDTO: Set[windymelt.timeline.application.dto.Event] =
              events.map((e: ctx.ctx.Event) => // 省略すると型検査を通らないので型を明示する
                ctx.ctx.timelineDTOFactory.toDTO(e)
              )
            eventsDTO.toList
          }
        )
      )
    )
  implicit lazy val EventType: ObjectType[
    windymelt.timeline.application.App,
    windymelt.timeline.application.dto.Event
  ] =
    deriveObjectType[Ctx, windymelt.timeline.application.dto.Event](
      AddFields(
        Field(
          "userCreatedBy",
          UserType,
          arguments = Nil,
          resolve = ctx => {
            val domainUser =
              ctx.ctx.userRepository.find(BigInt(ctx.value.editor.id)).get
            ctx.ctx.timelineDTOFactory.toDTO(domainUser)
          }
        )
      )
    )
}
