// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc.proxetta;

import jodd.madvoc.component.ActionsManager;
import jodd.madvoc.ActionConfig;
import jodd.proxetta.impl.ProxyProxetta;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.HashMap;

/**
 * Madvoc {@link jodd.madvoc.component.ActionsManager actions manager} that works with Proxetta.
 */
public class ProxettaAwareActionsManager extends ActionsManager {

	protected final ProxyProxetta proxetta;
	protected final Map<Class, Class> proxyActionClasses;

	public ProxettaAwareActionsManager() {
		this(null);
	}
	public ProxettaAwareActionsManager(ProxyProxetta proxetta) {
		this.proxetta = proxetta;
		this.proxyActionClasses = new HashMap<Class, Class>();
	}

	/**
	 * Registers actions and applies proxetta on actions that are not already registered.
	 */
	@Override
	protected synchronized ActionConfig registerAction(Class actionClass, Method actionMethod, String actionPath) {
		if (proxetta != null) {
			// create action path from existing class (if not already exist)
			if (actionPath == null) {
				ActionConfig cfg = actionMethodParser.parse(actionClass, actionMethod, actionPath);
				actionPath = cfg.actionPath;
			}
			// create proxy for action class if not already created
			Class existing = proxyActionClasses.get(actionClass);
			if (existing == null) {
				existing = proxetta.builder(actionClass).define();
				proxyActionClasses.put(actionClass, existing);
			}
			actionClass = existing;
		}
		return super.registerAction(actionClass, actionMethod, actionPath);
	}
}
