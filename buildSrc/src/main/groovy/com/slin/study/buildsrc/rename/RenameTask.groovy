package com.slin.study.buildsrc.rename

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import org.gradle.work.Incremental

import java.text.SimpleDateFormat
import java.util.function.Consumer


/**
 * author: slin
 * <p>
 * date: 2021/11/22
 * <p>
 * description: 重命名任务
 *
 * 将输入文件重命名/移动
 *
 */
abstract class RenameTask extends DefaultTask {

    private SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd")

    @Input
    abstract Property<String> getRule()

    @InputFile
    abstract RegularFileProperty getInputFile()

    @InputDirectory
    abstract DirectoryProperty getOutDir()

    @OutputFile
    File getOutputFile(){
        return project.file(getNewFilePath(inputFileValue))
    }

    @TaskAction
    void rename(){
        if(!outDir.get().asFile.exists()){
            outDir.get().asFile.mkdirs()
        }

        println "input: file = ${inputFileValue.name}"
        println "rule: ${getRule().get()}"
        println "output: ${getOutDir().get()}"

        renameFile(inputFileValue)

    }

    private void renameFile(File file){
        File newFile = new File(getNewFilePath(file))
        file.renameTo(newFile)
        println "output file: $newFile"
    }

    private String getNewFilePath(File file){
        String originName = file.name.substring(0, file.name.lastIndexOf('.'))
        String newName = rule.get()
                .replace("\$DATE", format.format(new Date()))
                .replace("\$NAME", originName)
                .replace("\$TYPE", file.name.substring(originName.length()+1, file.name.length()))
        return outDir.asFile.get().getPath() + File.separator + newName
    }

    @Internal
    File getInputFileValue(){
        return inputFile.asFile.get()
    }

}
