package com.yta.myanimelist.presentation.animeDetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yta.myanimelist.presentation.theme.MyAnimeListTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class AnimeDetailActivity : ComponentActivity() {
    companion object {
        const val ANIME_ID_KEY = "animeId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAnimeListTheme {
                AnimeDetailScreen(
                    viewModel = koinViewModel(
                        parameters = { parametersOf(intent.getLongExtra(ANIME_ID_KEY, 0L)) }
                    ),
                    showToast = { msg ->
                        Toast.makeText(this@AnimeDetailActivity, msg, Toast.LENGTH_SHORT).show()
                    },
                    onBackClick = { finish() }
                )
            }
        }
    }
}
