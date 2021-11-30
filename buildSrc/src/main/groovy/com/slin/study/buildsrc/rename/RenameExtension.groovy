package com.slin.study.buildsrc.rename

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

/**
 * author: slin
 * <p>
 * date: 2021/11/22
 * <p>
 * description:
 */
abstract class RenameExtension {

    @Input
    abstract Property<String> getRule()

    @Input
    abstract DirectoryProperty getOutDir()

}
