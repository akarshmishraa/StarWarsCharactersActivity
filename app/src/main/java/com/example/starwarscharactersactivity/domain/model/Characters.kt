package com.example.starwarscharactersactivity.domain.model

data class Characters(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<Results>
)
