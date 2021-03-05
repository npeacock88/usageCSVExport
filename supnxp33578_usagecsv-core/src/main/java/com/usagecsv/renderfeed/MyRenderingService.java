package com.usagecsv.renderfeed;

import java.util.Map;

import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.core.rendering.FreemarkerRender;
import org.nuxeo.ecm.automation.core.scripting.Scripting;


public class MyRenderingService {
    //TODO use a runtime service
    private static MyRenderingService instance = new MyRenderingService();

    public static MyRenderingService getInstance() {
        return instance;
    }

    //protected MvelRender mvel = new MvelRender();

    protected FreemarkerRender ftl = new FreemarkerRender();


    public String render(String type, String uriOrContent, OperationContext ctx) throws Exception {
        Map<String, Object> map = MyScripting.initBindings(ctx);
        //map.put("DocUrl", MailTemplateHelper.getDocumentUrl(doc, viewId));
        return ftl.render(uriOrContent, map);
    }

    /*public MyRenderer getRenderer(String type) {
        if ("mvel".equals(type)) {
            return mvel;
        } else {
            return ftl;
        }
    }*/


}
