package com.slin.study.buildsrc.android;

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension;
import com.android.build.gradle.tasks.ManifestProcessorTask;
import com.android.build.gradle.tasks.ProcessAndroidResources;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import groovy.util.Node;
import groovy.util.NodeList;
import groovy.util.XmlParser;
import groovy.xml.QName;
import groovy.xml.XmlUtil;

/**
 * author: slin
 * <p>
 * date: 2021/12/10
 * <p>
 * description: 模拟处理AndroidManifest.xml插件
 *
 * 下面demo演示了我们删除activity中的icon属性，如果你想处理其他属性都是可以的。
 *
 *
 */
public class ProcessManifestActivityPlugin implements Plugin<Project> {



    @Override
    public void apply(Project project) {
        if(!project.getPlugins().hasPlugin("com.android.application")){
            throw new GradleException("'com.android.application' plugin must be applied");
        }

        project.getTasks().create("processManifestActivity", task -> {
            task.setGroup("v_android");
            task.doLast(task1 -> System.out.println("Hello Android"));

            task.dependsOn("processMainappDebugResources");

        });


        project.afterEvaluate(project1 -> {
            BaseAppModuleExtension appModuleExtension = (BaseAppModuleExtension) project1.getExtensions().getByName("android");
            appModuleExtension.getApplicationVariants().forEach(variant -> {
                variant.getOutputs().forEach(output -> {
                    ManifestProcessorTask processManifest = output.getProcessManifestProvider().get();

                    processManifest.doFirst(task -> {
                        System.out.println("MergeBlameFile: " + processManifest.getMergeBlameFile().getOrNull());
                        System.out.println("ReportFile: " + processManifest.getReportFile().getOrNull());
                    });


                    ProcessAndroidResources resources = output.getProcessResourcesProvider().get();

                    resources.doFirst(task -> {
                        System.out.println("ManifestFiles: " + resources.getManifestFiles().getOrNull());
                        System.out.println("AaptFriendlyManifestFiles: " + resources.getAaptFriendlyManifestFiles().getOrNull());

                        File manifestFile = resources.getManifestFiles().file("AndroidManifest.xml").get().getAsFile();
                        File[] xmlFiles = resources.getManifestFiles().get().getAsFile().listFiles(new FileFilter() {
                            @Override
                            public boolean accept(File file) {
                                return file.getName().endsWith(".xml");
                            }
                        });
                        if (xmlFiles != null) {
                            for (File xmlFile : xmlFiles) {
                                processManifestFile(xmlFile);
                            }
                        }

                    });
                });

            });

        });
    }

    private void processManifestFile(File manifestFile){
        try {
            Node root = new XmlParser(false, true).parse(manifestFile);
            NodeList applicationNodeList = root.getAt(QName.valueOf("application"));
            if(applicationNodeList.isEmpty()){
                return;
            }
            Node applicationNode = (Node) applicationNodeList.get(0);
            NodeList children = (NodeList) applicationNode.children();
            for (int i = 0; i < children.size(); i++) {
                Object object = children.get(i);
                if(object instanceof Node) {
                    Node node = (Node) object;
                    processNode(node);
                }
            }
            PrintWriter writer = new PrintWriter(manifestFile);
            writer.write(XmlUtil.serialize(root));
            writer.flush();
            writer.close();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void processNode(Node node){
        String name = (String) node.name();
        Map attrs = node.attributes();
        QName iconKey = new QName("http://schemas.android.com/apk/res/android", "icon", "android");
        if ("activity".equals(name) && attrs.containsKey(iconKey)) {
            System.out.println("remove: icon = " + attrs.get(iconKey));
            attrs.remove(iconKey);
        }
    }

}
