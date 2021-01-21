package windymelt.timeline.web

import windymelt.timeline.Types

object Top {
  def index(implicit ctx: windymelt.timeline.Context) = {
    val user = ctx.app.userRepository.find("guest").get // TODO
    val tls = ctx.app.timelineRepository.findByEditor(user)
    val eventsByTimelineId: Map[Types.ID, Seq[ctx.app.Event]] =
      tls
        .map(tl => tl.id -> ctx.app.eventService.findByTimeline(tl).toSeq)
        .toMap

    val tlvas =
      tls
        .map(tl =>
          ctx.app.timelineDTOFactory.toDTO(tl, eventsByTimelineId(tl.id))
        )
        .toSeq
    val triageEvent =
      ctx.app.eventService
        .findByEditor(user)
        .map(ctx.app.timelineDTOFactory.toDTO(_))
        .toSeq

    views.html.hello(
      tlvas,
      triageEvent
    )(ctx)
  }
}
