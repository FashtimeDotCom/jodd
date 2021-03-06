// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc;

import jodd.JoddLog;
import jodd.log.Logger;
import jodd.log.LoggerFactory;
import jodd.log.impl.SimpleLoggerFactory;
import jodd.madvoc.action.HelloAction;
import jodd.madvoc.component.InterceptorsManager;
import jodd.madvoc.component.MadvocConfig;
import jodd.madvoc.petite.PetiteWebApplication;

import javax.servlet.ServletContext;

public class MyWebApplication extends PetiteWebApplication {

//	public MyWebApplication() {
//		LoggerFactory.setLoggerFactory(new SimpleLoggerFactory(Logger.Level.DEBUG));
//	}

	@Override
	public void registerMadvocComponents() {
		super.registerMadvocComponents();

		registerComponent(MyRewriter.class);
	}

	@Override
	protected void initInterceptors(InterceptorsManager interceptorsManager) {
		interceptorsManager.register("ServletConfigAltInterceptor", new ServletConfigAltInterceptor());
	}

	@Override
	protected void init(MadvocConfig madvocConfig, ServletContext servletContext) {
		super.init(madvocConfig, servletContext);

		madvocConfig.getRootPackages().addRootPackageOf(HelloAction.class);
	}
}