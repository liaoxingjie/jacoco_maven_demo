package com.example.demo.web;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.tools.ExecDumpClient;
import org.jacoco.core.tools.ExecFileLoader;
import org.jacoco.report.*;
import org.jacoco.report.html.HTMLFormatter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping({"/jacoco"})
public class JacocoDumpAndReport {
    //dump
    private String address = "localhost";
    private int port = 8091;
    private boolean dump = true;
//    private File destFile = new File("C:/Users/57769/Desktop/jacoco/jacoco.exec");
    private File destFile = new File("C:/Users/Administrator.USER-20190219YL/Desktop/jacoco/jacoco.exec");
    private boolean append = true;
    //report
//    private File sourceDirectory = new File("C:/Users/57769/IdeaProjects/jacoco_demo/src/main/java/");
    private File sourceDirectory = new File("C:/Users/57769/IdeaProjects/jacoco_maven_demo/src/main/java/");
//    private File classesDirectory = new File("D:/apache-tomcat-8.5.47/webapps/demo-0.0.1-SNAPSHOT/WEB-INF/classes/");
    private File classesDirectory = new File("D:/tomcat8/b/webapps/demo-0.0.1-SNAPSHOT/WEB-INF/classes/");
//    private File reportDirectory = new File("C:/Users/57769/Desktop/jacoco");
    private File reportDirectory = new File("C:/Users/Administrator.USER-20190219YL/Desktop/jacoco");
    private String title = "demo";

    @RequestMapping(value={"/dump"}, method = RequestMethod.GET )
    public String executeDump() {
        ExecDumpClient cli = new ExecDumpClient();
        cli.setDump(this.dump);
        try {
            ExecFileLoader loader = cli.dump(this.address, this.port);
            loader.save(this.destFile, this.append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "dump done!!!";
    }

    @RequestMapping(value={"/report"}, method= RequestMethod.GET)
    public String executeReport() throws Exception {
        loadexec();
        IBundleCoverage bundleCoverage = analyzeStructure();
        createReport(bundleCoverage);
        return "report done!!!";
    }

    public ExecFileLoader loadexec() throws IOException {
        ExecFileLoader execFileLoader = new ExecFileLoader();
        execFileLoader.load(this.destFile);
        return execFileLoader;
    }

    private IBundleCoverage analyzeStructure() throws IOException {
        CoverageBuilder coverageBuilder = new CoverageBuilder();
        Analyzer analyzer = new Analyzer(loadexec().getExecutionDataStore(), coverageBuilder);
        analyzer.analyzeAll(this.classesDirectory);
        return coverageBuilder.getBundle(this.title);
    }

    public void createReport(IBundleCoverage bundleCoverage) throws IOException {
        HTMLFormatter htmlFormatter = new HTMLFormatter();
        IReportVisitor visitor = htmlFormatter.createVisitor(new FileMultiReportOutput(this.reportDirectory));
        visitor.visitInfo(loadexec().getSessionInfoStore().getInfos(), loadexec().getExecutionDataStore().getContents());
        visitor.visitBundle(bundleCoverage, new DirectorySourceFileLocator(this.sourceDirectory, "utf-8", 4));
        visitor.visitEnd();
    }

//    @RequestMapping(value={"/dr"}, method= RequestMethod.GET)
//    public String executeDumpAndReport() throws Exception{
//        executeDump();
//        executeReport();
//        return "Dump And Report Done !!!!!!";
//    }


}