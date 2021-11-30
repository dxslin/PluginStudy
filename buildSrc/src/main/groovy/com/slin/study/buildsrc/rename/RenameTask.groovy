package com.slin.study.buildsrc.rename

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

import java.text.SimpleDateFormat
import java.util.function.Consumer


/**
 * author: slin
 * <p>
 * date: 2021/11/22
 * <p>
 * description:
 */
abstract class RenameTask extends DefaultTask {


    @Input
    abstract Property<String> getRule()

    @InputFile
    abstract RegularFileProperty getInputApk()

    @InputDirectory
    abstract DirectoryProperty getOutDir()

    @OutputDirectory
    DirectoryProperty getOutputFiles(){
        if(!outDir.asFile.get().exists()){
            outDir.asFile.get().mkdirs()
        }
        return outDir
    }


    @TaskAction
    void rename(){
        if(!outDir.get().asFile.exists()){
            outDir.get().asFile.mkdirs()
        }

        println "input: ${getInputApkFile().getName()}"
        println "rule: ${getRule().get()}"
        println "output: ${getOutDir().get()}"


        SimpleDateFormat format = new SimpleDateFormat("YYYY-mm-dd hh_mm_ss")
        File newFile = new File(outDir.asFile.get(), rule.get().replace("\$DATE", format.format(new Date())))
        getInputApkFile().renameTo(newFile)

        outputFiles.get().files().forEach(new Consumer<File>() {
            @Override
            void accept(File file) {
                println "output file: $file.name"
            }
        })
    }

    File getInputApkFile(){
        return inputApk.asFile.get()
    }

}
