package com.rijaldev.kuiskocakseru.ui.model

object DataUmum {
    private val pertanyaan = arrayOf("Indonesia disebut sebagai negara maritim karena...",
        "Apa nama tumbuhan langka yang terdapat di Provinsi Bengkulu?",
        "Apa ibu kota Portugal?",
        "Gudeg merupakan makanan khas Indonesia yang terkenal yang berasal dari daerah mana?",
        "Rice cooker merupakan alat yang bisa mengubah energi listrik menjadi ...",
        "Pusat tata surya kita adalah apa?",
        "Pempek merupakan makanan asli dari ...",
        "Yang bukan merupakan kegunaan dari komputer",
        "Apa dasar negara Indonesia?",
        "Siapa presiden indonesia ke-3?"
    )

    private val pilihanA = arrayOf("Terletak di asia tenggara",
        "Kantong semar",
        "Madrid",
        "Yogyakarta",
        "Penggerak",
        "Bintang",
        "Surabaya",
        "Membuat aplikasi",
        "UUD 1945",
        "BJ. Habibie"
    )

    private val pilihanB = arrayOf("Terdapat berbagai budaya",
        "Rafflesia arnoldi",
        "New York",
        "Medan",
        "Dorong",
        "Jupiter",
        "Padang",
        "Pemanas makanan",
        "Perpres",
        "Ir. Joko Widodo"
    )

    private val pilihanC = arrayOf("Kawasan laut yang luas",
        "Singkong",
        "Lisbon",
        "Palembang",
        "Panas",
        "Bulan",
        "Palembang",
        "Membuat laporan keuangan",
        "Pergub",
        "Ir. Soekarno"
    )

    private val pilihanD = arrayOf("Banyak hewan punah",
        "Kayu ulin",
        "Roma",
        "Semarang",
        "Cahaya",
        "Matahari",
        "Samarinda",
        "Mencari informasi",
        "Pancasila",
        "KH. Abdurrahman Wahid"
    )

    private val jawaban = intArrayOf(3,
        2,
        3,
        1,
        3,
        4,
        3,
        2,
        4,
        1
    )

    val listData: ArrayList<Pertanyaan>
        get() {
            val list = arrayListOf<Pertanyaan>()
            for (position in pertanyaan.indices) {
                val quest = Pertanyaan()
                quest.pertanyaan = pertanyaan[position]
                quest.pilihanA= pilihanA[position]
                quest.pilihanB = pilihanB[position]
                quest.pilihanC = pilihanC[position]
                quest.pilihanD = pilihanD[position]
                quest.jawaban = jawaban[position]
                list.add(quest)
            }
            list.shuffle()
            return list
        }
}