package com.yta.myanimelist.domain.util

import kotlinx.serialization.json.Json

val appJson = Json {
    isLenient = true
    ignoreUnknownKeys = true
    encodeDefaults = true
}