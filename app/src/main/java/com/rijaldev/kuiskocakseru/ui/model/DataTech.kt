package com.rijaldev.kuiskocakseru.ui.model

import com.rijaldev.kuiskocakseru.R

object DataTech {
    private val pertanyaan = arrayOf("RAM merupakan kependekan dari...",
        "Berikut merupakan sistem operasi, kecuali...",
        "Sebuah perangkat lunak pengolah kata adalah...",
        "CPU = ...",
        "Ilmuwan muslim yang menemukan konsep Algoritma, digunakan sebagai dasar berjalannya komputer adalah...",
        "Peralatan teknologi yang digunakan untuk mengetik adalah ...",
        "Apa kepanjangan dari Wifi?",
        "Apa bahasa pemrograman yang digunakan untuk membuat aplikasi android?",
        "Teknologi informasi dan komunikasi dengan menggunakan media suara dan gambar adalah ....",
        "Kegunaan dari komputer adalah ..."
    )

    private val pilihanA = arrayOf("Read Access Memory",
        "Linux",
        "Ms. Excel",
        "Central Processing Unit",
        "Alexander Graham Bell",
        "Mouse",
        "Wireless Fine",
        "Java",
        "Radio",
        "Mencetak, mengolah, menghitung"
    )

    private val pilihanB = arrayOf("Random Access Manager",
        "Adobe Photoshop",
        "Adobe Flash",
        "Central Processor Unit",
        "Abu Muslim Al-Khawarizmi",
        "Monitor",
        "World Fine",
        "HTML",
        "Telepon",
        "Mengolah, mengelola, menyimpan data"
    )

    private val pilihanC = arrayOf("Read Access Manager",
        "Windows",
        "Ms. Word",
        "Common Prosessor Unit",
        "Ibnu Khaldun",
        "Processor",
        "Wireless Fidelity",
        "Java Script",
        "Surat Kabar",
        "Main game, mengelola, menelpon"
    )

    private val pilihanD = arrayOf("Random Access Memory",
        "Android",
        "Google Chrome",
        "Common Processing Unit",
        "Abbas ibn Firnas",
        "Keyboard",
        "World First",
        "Ruby",
        "Televisi",
        "Mengetik, mencetak, main game, menghitung"
    )

    private val jawaban = intArrayOf(4,
        2,
        3,
        1,
        2,
        4,
        3,
        1,
        4,
        2
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
            return list
        }
}