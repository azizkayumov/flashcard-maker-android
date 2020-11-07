package com.piapps.flashcardpro.features.editor.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.CardDb
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-06 at 17:20.
 */

class ArchiveCard
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(card: CardDb) {
        val set = repository.getSet(card.setId)
        set?.let {
            var twin = repository.getSet(-it.id)
            if (twin == null) {
                twin = it.clone()
                twin.id = -it.id
                twin.count = 0
            }
            twin.isTrash = false
            twin.count += 1
            card.setId = twin.id
            repository.save(card)
            repository.save(twin)
        }
    }
}