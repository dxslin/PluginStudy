package com.slin.study.buildsrc.inject;

import com.sun.tools.javac.util.List;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.Nested;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import groovy.lang.GString;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description: 嵌套对象和对象集合
 *
 *
 *
 */

public interface DownloadExtension {

    ConfigurableFileCollection getInputFiles();

    NamedDomainObjectContainer<ResourceUrl> getResources();

    @Nested
    HostPath getHostPath();

    default void hostPath(Action<HostPath> action){
        action.execute(getHostPath());
    }

    default void inputFiles(Object... files){
        getInputFiles().from(files);
    }

}

// 这样写也是可以的
//public abstract class DownloadExtension {
//    // A nested instance
//    private final HostPath hostPath;
//
//    public abstract NamedDomainObjectContainer<ResourceUrl> getResources();
//
//    public ArrayList<File> inputFiles = new ArrayList<>();
//
//    @Inject
//    public DownloadExtension(ObjectFactory objectFactory) {
//        // Use an injected ObjectFactory to create a HostPath object
//        hostPath = objectFactory.newInstance(HostPath.class);
//    }
//
//
//    public void hostPath(Action<HostPath> action){
//        action.execute(hostPath);
//    }
//
//    public HostPath getHostPath() {
//        return hostPath;
//    }
//
//    public ArrayList<File> getInputFiles() {
//        return inputFiles;
//    }
//
//    public void inputFiles(Object... files){
//        for (Object obj : files) {
//            if (obj instanceof File) {
//                inputFiles.add((File) obj);
//            } else if (obj instanceof String) {
//                inputFiles.add(new File((String) obj));
//            } else if(obj != null) {
//                inputFiles.add(new File(obj.toString()));
//            }
//        }
//    }
//
//}
