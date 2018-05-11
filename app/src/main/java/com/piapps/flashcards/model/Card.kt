package com.piapps.flashcards.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by abduaziz on 4/18/18.
 */

@Entity
class Card(@Id(assignable = true)
           var id: Long,
           var setId: Long,
           var front: String = "", // front text
           var back: String = "", // back text
           var frontColor: String = "", // hex value
           var backColor: String = "", // hex value
           var frontTextColor: String = "", // hex value
           var backTextColor: String = "", // hex value
           var frontImage: String = "", // path to front image
           var backImage: String = "", // path to back image
           var frontAudio: String = "", // path to audio file
           var backAudio: String = "", // path to audio file
           var trueAnswers: Int = 0, // true answers reached in Quiz mode
           var totalSeen: Int = 0 // total seen by user
)