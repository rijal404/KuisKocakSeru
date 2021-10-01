package com.rijaldev.kuiskocakseru.ui.model

object DataKocak {
    private val pertanyaan = arrayOf("Ada 5 angsa di kali dua, total semua angsa ada berapa?",
        "Tamunya sudah masuk, tapi yang punya malah keluar. Apakah itu?",
        "Apa nama sebuah benda yang kalau ditutup berubah jadi tongkat, tapi ketika dibuka malah jadi tenda?",
        "Kenapa pohon didepan rumah harus ditebang?",
        "Orang apa yang berenang tapi rambutnya tidak pernah basah?",
        "Apa huruf ke-5 pada angka?",
        "Sayur, sayur apa yang dingin?",
        "Buah apa yang durhaka?",
        "Aku selalu ada di atas presiden dan menteri, tapi aku tidak punya jabatan apa pun dalam pemerintahan. Siapakah aku?",
        "Apa yang ada di ujung langit?"
    )

    private val pilihanA = arrayOf("Sepuluh",
        "Upil",
        "Tempat kemah",
        "Kalau dicabut berat",
        "Orang sakit",
        "Tidak ada",
        "Kembang cold",
        "Durian montong",
        "Lurah",
        "Bintang"
    )

    private val pilihanB = arrayOf("Tiga",
        "Tukang becak",
        "Kursi",
        "Karena mengganggu",
        "Orang utan",
        "Huruf A",
        "Sayur baru beli",
        "Mangga arumanis",
        "Lembaga",
        "Planet neptunus"
    )

    private val pilihanC = arrayOf("Lima",
        "Asisten rumah tangga",
        "Kayu bakar",
        "Karena bahaya",
        "Orang botak",
        "Lima",
        "Sayur bayam",
        "Pisang ambon",
        "Peci",
        "Galaksi"
    )

    private val pilihanD = arrayOf("Mati semua",
        "Pak Somad",
        "Payung",
        "Karena sudah tinggi",
        "Orang tidur",
        "Kosong",
        "Terong bakar",
        "Melon kundang",
        "Kursi",
        "Huruf T"
    )

    private val jawaban = intArrayOf(3,
        2,
        4,
        1,
        3,
        2,
        1,
        4,
        3,
        4
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