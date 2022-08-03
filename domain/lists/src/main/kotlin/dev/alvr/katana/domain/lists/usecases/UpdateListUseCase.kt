package dev.alvr.katana.domain.lists.usecases

import dev.alvr.katana.domain.base.usecases.EitherUseCase
import dev.alvr.katana.domain.lists.models.lists.MediaList
import dev.alvr.katana.domain.lists.repositories.ListsRepository
import javax.inject.Inject

class UpdateListUseCase @Inject constructor(
    private val repository: ListsRepository,
) : EitherUseCase<MediaList, Unit> {
    override suspend fun invoke(entry: MediaList) = repository.updateList(entry)
}