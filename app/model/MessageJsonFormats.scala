package model

import controllers.MessageController.{MessageRequest, MessageResponse, MessagesResponse}
import play.api.libs.functional.syntax._
import play.api.libs.json._

trait MessageJsonFormats {

  private implicit val coordinatesReads: Reads[Coordinates] =
    __.read[Seq[Double]].map { case Seq(x, y) => Coordinates(x, y) }

  private implicit val locationReads: Reads[Location] = (
    (__ \ "type").read[String] ~
      (__ \ "coordinates").read[Coordinates]
  )(Location)

//  implicit val messageRequestReads: Reads[MessageRequest] = (
//    (__ \ "from").read[String] ~
//      (__ \ "text").read[String] ~
//      (__ \ "location").read[Location]
//  )(MessageRequest)

  implicit val messageRequestReads: Reads[MessageRequest] =
    Json.reads[MessageRequest]

  private implicit val coordinatesWrites: Writes[Coordinates] =
    (coordinates: Coordinates) => Json.arr(coordinates.x, coordinates.y)

  private implicit val locationWrites: Writes[Location] = (
    (__ \ "type").write[String] ~
      (__ \ "coordinates").write[Coordinates]
  )(unlift(Location.unapply))

  implicit val messageResponseWrites: OWrites[MessageResponse] =
    Json.writes[MessageResponse]

  implicit val messagesResponseWrites: OWrites[MessagesResponse] =
    Json.writes[MessagesResponse]
}
