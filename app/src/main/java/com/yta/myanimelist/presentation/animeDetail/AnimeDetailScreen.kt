package com.yta.myanimelist.presentation.animeDetail

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.yta.myanimelist.domain.models.AnimeData

@Composable
fun AnimeDetailScreen(
    viewModel: AnimeDetailViewModel,
    showToast: (String) -> Unit,
    onBackClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.errors.collect {
            showToast(it ?: return@collect)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchAnime()
    }

    AnimeDetailLayout(
        animeData = viewModel.animeData.collectAsStateWithLifecycle(),
        isLoading = viewModel.isLoading.collectAsStateWithLifecycle(),
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailLayout(
    animeData: State<AnimeData?>,
    isLoading: State<Boolean>,
    onBackClick: () -> Unit
) {
    animeData.value?.let { data ->
        Box(
            modifier = Modifier
                .systemBarsPadding()
                .safeContentPadding()
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                data.title?.let { title ->
                    TopAppBar(
                        title = {
                            Text(title)
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = onBackClick
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }

                Column(Modifier.padding(16.dp)) {
                    if (!data.trailerLink.isNullOrBlank()) {
                        val context = LocalContext.current

                        val webView = remember {
                            getYoutubeIFrameWebView(context, data.trailerLink)
                        }

                        DisposableEffect(Unit) {
                            onDispose {
                                webView.destroy()
                            }
                        }

                        AndroidView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f),
                            factory = { webView }
                        )

                        Spacer(Modifier.height(8.dp))
                    } else if (!data.posterImageUrl.isNullOrBlank()) {
                        AsyncImage(
                            data.posterImageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Fit
                        )
                        Spacer(Modifier.height(8.dp))
                    }

                    data.plot?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    data.genres?.let {
                        Text(
                            text = "Genres: $it",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    data.episodes?.let {
                        Text(
                            text = "No. of episodes: $it",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    data.rating?.let {
                        Text(
                            text = "Rating: $it",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            if (isLoading.value) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
private fun getYoutubeIFrameWebView(
    context: Context,
    trailerLink: String
): WebView = WebView(context).apply {
    layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    settings.javaScriptEnabled = true
    settings.domStorageEnabled = true
    settings.mediaPlaybackRequiresUserGesture = false
    settings.loadsImagesAutomatically = true
    settings.cacheMode = WebSettings.LOAD_DEFAULT
    settings.useWideViewPort = true
    settings.loadWithOverviewMode = true
    settings.allowFileAccess = false
    settings.allowContentAccess = false
    setLayerType(WebView.LAYER_TYPE_HARDWARE, null)

    webChromeClient = WebChromeClient()

    val safeUrl = run {
        val baseUrl = trailerLink.substringBefore("?")
        "$baseUrl?autoplay=1&mute=1&playsinline=1&controls=1"
    }

    val html = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                html, body {
                    margin: 0;
                    padding: 0;
                    width: 100%;
                    height: 100%;
                    background-color: black;
                    overflow: hidden;
                }
                iframe {
                    position: absolute;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100%;
                    border: none;
                }
            </style>
        </head>
        <body>
            <iframe
                src="$safeUrl"
                allow="autoplay; encrypted-media; picture-in-picture"
                allowfullscreen>
            </iframe>
        </body>
        </html>
    """.trimIndent()

    loadDataWithBaseURL(
        "https://www.youtube-nocookie.com",
        html,
        "text/html",
        "utf-8",
        null
    )
}
