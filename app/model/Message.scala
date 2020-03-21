package model

import java.time.LocalDateTime
import java.util.UUID

case class Message(id: UUID,
                   from: String,
                   text: String,
                   location: Location,
                   created: LocalDateTime)
