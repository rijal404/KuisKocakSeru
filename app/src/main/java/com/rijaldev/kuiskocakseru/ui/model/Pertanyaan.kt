package com.rijaldev.kuiskocakseru.ui.model

data class Pertanyaan (
    var pertanyaan: String? = "",
    var pilihanA: String? = "",
    var pilihanB: String? = "",
    var pilihanC: String? = "",
    var pilihanD: String? = "",
    var jawaban: Int? = 0,
    var gambar: Int? = 0
)