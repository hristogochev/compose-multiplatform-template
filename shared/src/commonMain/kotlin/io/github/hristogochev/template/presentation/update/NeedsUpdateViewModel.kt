package io.github.hristogochev.template.presentation.update

import io.github.hristogochev.template.domain.repository.SkippedVersionRepository
import io.github.hristogochev.template.util.StoreOpener
import io.github.hristogochev.template.util.riptide.ViewModel
import io.github.hristogochev.template.util.updateNavEffect

class NeedsUpdateViewModel(
    private val storeOpener: StoreOpener,
    private val skippedVersionRepository: SkippedVersionRepository,
    initialState: NeedsUpdateState
) :
    ViewModel<NeedsUpdateState, Nothing, Nothing, NeedsUpdateNavEvent, NeedsUpdateNavEffect>(
        initialState
    ) {

    override suspend fun onNavEventProtected(event: NeedsUpdateNavEvent) {
        when (event) {
            is NeedsUpdateNavEvent.Update -> {
                storeOpener.openStore(event.iOSAppId)
            }

            is NeedsUpdateNavEvent.Skip -> {
                onSkip(event.skippedVersion)
            }
        }
    }


    private suspend fun onSkip(skippedVersion: ULong) {
        skippedVersionRepository.save(skippedVersion)

        _navEffect.updateNavEffect(NeedsUpdateNavEffect.Skip)
    }
}