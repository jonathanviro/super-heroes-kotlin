package com.javr.super_heroes

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class Heroe (val strNombreHeroe:String, val strAlterEgo:String, val strBiografia:String, val ftPoder: Float) :
    Parcelable