package com.mobilesolutionworks.android.gradle.configurator.util

import java.io.File
import java.nio.file.Paths

fun File.paths(vararg paths: String) = Paths.get(this.absolutePath, *paths).toFile()