package com.bangkit.fica.utils.preferences

import com.chibatching.kotpref.KotprefModel

object AuthPrefs: KotprefModel() {
    var hadLogin    by booleanPref()
    var email       by stringPref()
}