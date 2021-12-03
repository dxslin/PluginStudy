package com.slin.study.buildsrc.rename

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFile
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile

/**
 * author: slin
 * <p>
 * date: 2021/11/22
 * <p>
 * description:
 */
abstract class RenameExtension {

    @InputFile
    abstract RegularFileProperty getInputFile()

    @Input
    abstract Property<String> getRule()

    @InputDirectory
    abstract DirectoryProperty getOutDir()

}
