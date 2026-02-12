package com.yta.myanimelist.presentation.animeListing

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yta.myanimelist.presentation.animeDetail.AnimeDetailActivity
import com.yta.myanimelist.presentation.theme.MyAnimeListTheme

class AnimeListingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAnimeListTheme {
                AnimeListingScreen(
                    showToast = { msg ->
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                    },
                    onAnimeClicked = { animeId ->
                        Intent(this@AnimeListingActivity, AnimeDetailActivity::class.java).also {
                            it.putExtra(AnimeDetailActivity.ANIME_ID_KEY, animeId)
                            startActivity(it)
                        }
                    }
                )
            }
        }
    }
}