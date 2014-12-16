package com.appautocreate.utils;

import java.io.File;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class AntUtil {

	private static String projectBasePath = ""; 
	
	
	private Project project;
	public void init(String _buildFile,String _baseDir){
		project = new Project();
		DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(System.out);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
		project.addBuildListener(consoleLogger);
		if(_baseDir == null){
			_baseDir = new String(".");
		}
		
		project.setBasedir(_baseDir);
		if (_buildFile == null)  
            _buildFile = new String(projectBasePath + File.separator  
                    + "build.xml");  
  
        ProjectHelper.configureProject(project, new File(_buildFile));  
		
	}
	
	 public void runTarget(String _target){  
	        if (project == null) 
	        	return;
	        if (_target == null)  
	            _target = project.getDefaultTarget();  
	        project.executeTarget(_target);  
	  
	    }  
}
