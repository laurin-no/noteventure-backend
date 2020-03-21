package model

import controllers.MessageController.{MessageResponse, MessagesResponse}
import play.api.libs.functional.syntax._
import play.api.libs.json._

trait MessageJsonFormats {

  private implicit val coordinatesWrites: Writes[Coordinates] =
    (coordinates: Coordinates) => Json.arr(coordinates.x, coordinates.y)

  private implicit val locationWrites: Writes[Location] = (
    (__ \ "type").write[String] ~
      (__ \ "coordinates").write[Coordinates]
    ) (unlift(Location.unapply))

  implicit val messageResponseWrites: OWrites[MessageResponse] =
    Json.writes[MessageResponse]

  implicit val messagesResponseWriter: OWrites[MessagesResponse] =
    Json.writes[MessagesResponse]
}
