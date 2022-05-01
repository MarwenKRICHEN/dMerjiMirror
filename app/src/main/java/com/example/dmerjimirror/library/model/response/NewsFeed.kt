package com.example.dmerjimirror.library.model.response

class NewsFeed(
    id: Int,
    name: String,
    position: String,
    active: Boolean,
    userid: Int,
    val showdescription: Boolean,
    val list: ArrayList<Feed>
) : Component(id, name, position, active, userid) {
    constructor() : this(0, "", "", false, 0, true, arrayListOf())

    companion object {
        val listOfCategories = listOf(
            "Business",
            "Entertainment",
            "General",
            "Health",
            "Science",
            "Sports",
            "Technology"
        )
        val listOfCountries = listOf(
            "United Stated",
            "France",
            "United Arab Emirates",
        )

        fun getCountryCode(country: String): String {
            return when (country) {
                "United Stated" -> "us"
                "France" -> "fr"
                else -> "ae"
            }
        }
    }
}
