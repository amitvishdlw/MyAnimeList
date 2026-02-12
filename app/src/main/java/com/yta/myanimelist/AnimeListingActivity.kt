package com.yta.myanimelist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yta.myanimelist.presentation.animeListing.AnimeListingScreen
import com.yta.myanimelist.presentation.theme.MyAnimeListTheme
import org.koin.core.component.KoinComponent

class AnimeListingActivity : ComponentActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAnimeListTheme {
                AnimeListingScreen(
                    showToast = { msg ->
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}


