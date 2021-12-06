package com.slin.study.buildsrc.inject;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.model.ObjectFactory;

import javax.inject.Inject;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description:
 */
public abstract class DownloadExtension {
    // A nested instance
    private final HostPath hostPath;

    public abstract NamedDomainObjectContainer<ResourceUrl> getResources();

    @Inject
    public DownloadExtension(ObjectFactory objectFactory) {
        // Use an injected ObjectFactory to create a HostPath object
        hostPath = objectFactory.newInstance(HostPath.class);
    }


    public void hostPath(Action<HostPath> action){
        action.execute(hostPath);
    }

    public HostPath getHostPath() {
        return hostPath;
    }
}