package service

import javax.inject.Inject
import model.Messages
import persistence.MessageRepository

import scala.concurrent.Future

class MessageService @Inject()(messageRepository: MessageRepository) {

  def getMessages: Future[Messages] = {
    messageRepository.getMessages
  }
}
