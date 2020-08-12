package com.example.githubusers.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    @Json(name = "login")
    val username: String?,
    @Json(name = "avatar_url")
    val avatarUrl: String?
): Parcelable


@Parcelize
data class User(
    @Json(name = "name")
    val name: String?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "followers")
    val followers: Int?,
    @Json(name = "following")
    val following: Int?,
    @Json(name = "html_url")
    val htmlUrl: String?,
    @Json(name = "avatar_url")
    var avatarUrl: String?,
    @Json(name = "login")
    var username: String?
): Parcelable