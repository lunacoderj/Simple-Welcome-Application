package com.mad.app.data.model

data class AndroidVersion(
    val id: Int,
    val name: String,
    val codename: String,
    val apiLevel: Int,
    val releaseDate: String,
    val emoji: String
)

object AndroidVersionRepository {
    fun getVersions(): List<AndroidVersion> = listOf(
        AndroidVersion(1, "Android 1.5", "Cupcake", 3, "Apr 2009", "🧁"),
        AndroidVersion(2, "Android 1.6", "Donut", 4, "Sep 2009", "🍩"),
        AndroidVersion(3, "Android 2.0", "Eclair", 5, "Oct 2009", "🍨"),
        AndroidVersion(4, "Android 2.2", "Froyo", 8, "May 2010", "🍦"),
        AndroidVersion(5, "Android 2.3", "Gingerbread", 9, "Dec 2010", "🍪"),
        AndroidVersion(6, "Android 3.0", "Honeycomb", 11, "Feb 2011", "🍯"),
        AndroidVersion(7, "Android 4.0", "Ice Cream Sandwich", 14, "Oct 2011", "🍨"),
        AndroidVersion(8, "Android 4.1", "Jelly Bean", 16, "Jul 2012", "🫘"),
        AndroidVersion(9, "Android 4.4", "KitKat", 19, "Oct 2013", "🍫"),
        AndroidVersion(10, "Android 5.0", "Lollipop", 21, "Nov 2014", "🍭"),
        AndroidVersion(11, "Android 6.0", "Marshmallow", 23, "Oct 2015", "🍬"),
        AndroidVersion(12, "Android 7.0", "Nougat", 24, "Aug 2016", "🍫"),
        AndroidVersion(13, "Android 8.0", "Oreo", 26, "Aug 2017", "🍪"),
        AndroidVersion(14, "Android 9", "Pie", 28, "Aug 2018", "🥧"),
        AndroidVersion(15, "Android 10", "Queen Cake", 29, "Sep 2019", "🎂"),
        AndroidVersion(16, "Android 11", "Red Velvet Cake", 30, "Sep 2020", "🍰"),
        AndroidVersion(17, "Android 12", "Snow Cone", 31, "Oct 2021", "🍧"),
        AndroidVersion(18, "Android 12L", "Snow Cone v2", 32, "Mar 2022", "❄️"),
        AndroidVersion(19, "Android 13", "Tiramisu", 33, "Aug 2022", "☕"),
        AndroidVersion(20, "Android 14", "Upside Down Cake", 34, "Oct 2023", "🎂"),
        AndroidVersion(21, "Android 15", "Vanilla Ice Cream", 35, "2024", "🍦"),
    )
}
