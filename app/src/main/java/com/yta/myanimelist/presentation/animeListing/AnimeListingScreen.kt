package com.yta.myanimelist.presentation.animeListing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.yta.myanimelist.domain.models.AnimeData
import com.yta.myanimelist.presentation.theme.MyAnimeListTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AnimeListingScreen(
    viewModel: AnimeListingViewModel = koinViewModel(),
    showToast: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.errors.collect {
            showToast(it ?: return@collect)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchTopAnimes()
    }

    AnimeListingLayout(
        animeList = viewModel.animeList.collectAsStateWithLifecycle(),
        isLoading = viewModel.isLoading.collectAsStateWithLifecycle()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListingLayout(
    animeList: State<List<AnimeData>>,
    isLoading: State<Boolean>
) {
    Surface(
        modifier = Modifier
            .systemBarsPadding()
            .safeContentPadding()
            .fillMaxSize()
    ) {
        Column {
            TopAppBar(
                title = {
                    Text(
                        text = "MyAnimeList",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            )
            LazyColumn(
                modifier = Modifier,
                contentPadding = PaddingValues(8.dp)
            ) {
                animeList.value.forEach { animeData ->
                    item {
                        AnimeDataLayout(animeData)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
        if (isLoading.value) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun AnimeDataLayout(animeData: AnimeData) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = animeData.posterImageUrl,
                contentDescription = null,
                modifier = Modifier.wrapContentSize(),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                animeData.title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 2
                    )
                }

                animeData.episodes?.let {
                    Text(
                        text = "No. of episodes: $it",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1
                    )
                }

                animeData.rating?.let {
                    Text(
                        text = "Rating: $it",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeListingLayoutPreview() {
    MyAnimeListTheme {
        val fakeAnimeList = listOf(
            AnimeData(
                id = 52991,
                title = "Sousou no Frieren",
                posterImageUrl = "https://cdn.myanimelist.net/images/anime/1015/138006.jpg",
                episodes = 28,
                rating = 9.12
            ),
            AnimeData(
                id = 57555,
                title = "Chainsaw Man Movie: Reze-hen",
                posterImageUrl = "https://cdn.myanimelist.net/images/anime/1763/150638.jpg",
                episodes = 1,
                rating = 6.72
            ),
            AnimeData(
                id = 5114,
                title = "Fullmetal Alchemist: Brotherhood",
                posterImageUrl = "https://cdn.myanimelist.net/images/anime/1208/94745.jpg",
                episodes = 64,
                rating = 8.23
            )
        )
        AnimeListingLayout(
            animeList = remember { mutableStateOf(fakeAnimeList) },
            isLoading = remember { mutableStateOf(false) }
        )
    }
}