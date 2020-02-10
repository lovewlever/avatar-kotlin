package com.project.avatar.common

object PatternCommon {
    val PATTERN_IMG = Regex("^(bmp|jpg|png|tif|gif|pcx|tga|exif|fpx|svg|psd|cdr|pcd|dxf|ufo|eps|ai|raw|WMF|webp)$")
    val PATTERN_PHONE = Regex("^1[0-9]{10}\$")
    val PATTERN_EMAIL = Regex("^[a-z0-9A-Z]+[- |a-z0-9A-Z._]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}\$")
}