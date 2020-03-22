package persistence

import java.time.LocalDateTime
import java.util.UUID

import model.{Coordinates, Location, Message, Messages}

import scala.concurrent.Future

class MessageRepository {

  private var msgStoreStub =
    Seq(
      Message(
        UUID.randomUUID(),
        "anon",
        "hello world",
        Location("Point", Coordinates(42.0, 42.0)),
        LocalDateTime.now()
      )
    )

  def getMessages: Future[Messages] = {
    Future.successful(Messages(msgStoreStub))
  }

  def saveMessage(message: Message): Future[Unit] = {
    msgStoreStub = msgStoreStub :+ message
    Future.successful(())
  }

}
