package com.omerfpekgoz.spaceflightnewsapp.data.model

data class ArticleListDTO(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)