package com.usagecsv.renderfeed;

import groovy.lang.Binding;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mvel2.MVEL;
import org.nuxeo.common.utils.FileUtils;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.OperationException;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.scripting.DateWrapper;
import org.nuxeo.ecm.automation.core.scripting.DocumentWrapper;
import org.nuxeo.ecm.automation.core.scripting.Functions;
import org.nuxeo.ecm.automation.core.scripting.PrincipalWrapper;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.platform.audit.api.LogEntry;
import org.nuxeo.ecm.platform.audit.api.LogEntryList;
import org.nuxeo.runtime.api.Framework;


public class MyScripting {

    private static final Logger log = LogManager.getLogger(MyScripting.class);

    public static Map<String, Object> initBindings(OperationContext ctx) {
        log.error("Got to initBindings method");
        Object input = ctx.getInput(); // get last output
        Map<String, Object> map = new HashMap<String, Object>(ctx);
        map.put("CurrentDate", new DateWrapper());
        map.put("Context", ctx);
        if (ctx.get(Constants.VAR_WORKFLOW) != null) {
            map.put(Constants.VAR_WORKFLOW, ctx.get(Constants.VAR_WORKFLOW));
        }
        if (ctx.get(Constants.VAR_WORKFLOW_NODE) != null) {
            map.put(Constants.VAR_WORKFLOW_NODE,
                    ctx.get(Constants.VAR_WORKFLOW_NODE));
        }
        map.put("This", input);
        log.error("input in initBindings methos is defined as " + map.get("This"));
        map.put("Session", ctx.getCoreSession());
        PrincipalWrapper principalWrapper = new PrincipalWrapper(
                (NuxeoPrincipal) ctx.getPrincipal());
        map.put("CurrentUser", principalWrapper);
        // Alias
        map.put("currentUser", principalWrapper);
        map.put("Env", Framework.getProperties());
        map.put("Fn", Functions.getInstance());
        if (input instanceof LogEntry) {
            log.error("Got into if stmt (if instance of LogEntry");
            DocumentWrapper documentWrapper = new DocumentWrapper(
                    ctx.getCoreSession(), (DocumentModel) input);
            map.put("Document", (LogEntry) input);
            // Alias
            map.put("currentDocument", (LogEntry) input);
        }
        if (input instanceof LogEntryList) {
            log.error("Got into if stmt (if instace of LogEntryList)");
            List<LogEntry> logs = new ArrayList<LogEntry>();
            for (LogEntry log : (LogEntryList) input) {
                logs.add(log);
            }
            map.put("Documents", logs);
            log.error("map[Documents] = " + map.get("Documents"));
            if (logs.size() >= 1) {
                map.put("Document", logs.get(0));
            }
        }
        return map;
    }

}
