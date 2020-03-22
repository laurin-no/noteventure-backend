package service

import java.time.LocalDateTime
import java.util.UUID

import controllers.MessageController.MessageRequest
import javax.inject.Inject
import model.{Message, Messages}
import persistence.MessageRepository

import scala.concurrent.Future

class MessageService @Inject()(messageRepository: MessageRepository) {

  def getMessages: Future[Messages] = {
    messageRepository.getMessages
  }

  def saveMessage(message: MessageRequest): Future[Unit] = {
    val msg = Message(
      UUID.randomUUID(),
      message.from,
      message.text,
      message.location,
      LocalDateTime.now()
    )

    messageRepository.saveMessage(msg)
  }
}
