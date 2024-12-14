package dev.alvr.katana.features.explore.ui.viewmodel

import dev.alvr.katana.core.ui.viewmodel.EmptyEffect
import dev.alvr.katana.core.ui.viewmodel.EmptyIntent
import dev.alvr.katana.core.ui.viewmodel.EmptyState
import dev.alvr.katana.core.ui.viewmodel.KatanaBaseViewModel

internal class ExploreViewModel : KatanaBaseViewModel<EmptyState, EmptyEffect, EmptyIntent>(EmptyState)
