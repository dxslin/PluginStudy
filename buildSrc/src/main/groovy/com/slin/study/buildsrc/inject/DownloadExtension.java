package com.slin.study.buildsrc.inject;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.Nested;

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
    private final Resource downloadResource;

    private final NamedDomainObjectContainer<Resource> downloadResources;

    @Inject
    public DownloadExtension(ObjectFactory objectFactory) {
        // Use an injected ObjectFactory to create a Resource object
        downloadResource = objectFactory.newInstance(Resource.class);
        downloadResources = objectFactory.domainObjectContainer(Resource.class);


    }

    public void downloadResource(Action<? super Resource> action){
        action.execute(getDownloadResource());
    }

    @Nested
    public Resource getDownloadResource() {
        return downloadResource;
    }

    @Nested
    public NamedDomainObjectContainer<Resource> getDownloadResources() {
        return downloadResources;
    }
}