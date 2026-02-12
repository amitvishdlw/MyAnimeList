package com.yta.myanimelist.di

import com.yta.myanimelist.presentation.animeDetail.AnimeDetailViewModel
import com.yta.myanimelist.presentation.animeListing.AnimeListingViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        AnimeListingViewModel(
            get(),
            Dispatchers.IO
        )
    }

    viewModel { (animeId: Long) ->
        AnimeDetailViewModel(
            animeId = animeId,
            repo = get(),
            ioDispatcher = Dispatchers.IO
        )
    }
}