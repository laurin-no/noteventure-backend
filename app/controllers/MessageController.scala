package controllers

import java.time.LocalDateTime
import java.util.UUID

import controllers.MessageController.{MessageRequest, MessageResponse, MessagesResponse}
import javax.inject.Inject
import model.{Location, Message, MessageJsonFormats}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import service.MessageService

import scala.concurrent.{ExecutionContext, Future}

object MessageController {

  case class MessageRequest(from: String, text: String, location: Location)

  case class MessageResponse(id: UUID, from: String, text: String, location: Location, created: LocalDateTime)

  case class MessagesResponse(messages: Seq[MessageResponse])

}

class MessageController @Inject()(
                                   val controllerComponents: ControllerComponents,
                                   service: MessageService
                                 )(implicit ec: ExecutionContext)
    extends BaseController
    with MessageJsonFormats {

  def getMessages: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val res = service.getMessages
      .map(msgs => MessagesResponse(msgs.messages.map(messageResponseMapper)))
      .map(msgs => Ok(Json.toJson(msgs)))

    res
  }

  def saveMessage(): Action[JsValue] = Action.async(parse.json) { implicit request: Request[JsValue] =>
    request.body
      .validate[MessageRequest]
      .asOpt
      .map { msg =>
        for {
          _ <- service.saveMessage(msg)
          res <- Future.successful(Created("Message created"))
        } yield res
      }
      .getOrElse(Future.successful(BadRequest("Invalid message")))
  }

  private def messageResponseMapper(message: Message): MessageResponse =
    MessageResponse(
      message.id,
      message.from,
      message.text,
      message.location,
      message.created
    )

}
