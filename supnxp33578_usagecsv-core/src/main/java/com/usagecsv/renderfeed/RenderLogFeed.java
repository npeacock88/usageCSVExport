package com.usagecsv.renderfeed;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.automation.core.rendering.RenderingService;
import org.nuxeo.ecm.automation.core.util.PaginablePageProvider;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.api.impl.blob.StringBlob;
import org.nuxeo.ecm.platform.audit.api.LogEntryList;
import org.nuxeo.ecm.platform.query.api.PageProvider;
import org.nuxeo.runtime.services.resource.ResourceService;

import com.google.errorprone.annotations.Var;

/**
 *
 */
@Operation(id=RenderLogFeed.ID, category=Constants.CAT_DOCUMENT, label="Render Log Feed", description="Describe here what your operation does.")
public class RenderLogFeed {

    public static final String ID = "Render.LogFeed";

    @Context
    protected ResourceService rs;

    @Context
    protected OperationContext ctx;

    @Param(name = "template", widget = Constants.W_TEMPLATE_RESOURCE)
    protected String template;

    @Param(name = "type", widget = Constants.W_OPTION, required = false, values = {"ftl", "mvel"})
    protected String type = "ftl";

    @Param(name = "filename", required = false, values="output.ftl")
    protected String name = "output.ftl";

    @Param(name = "mimetype", required = false, values="text/xml")
    protected String mimeType = "text/xml";

    @Param(name = "charset", required = false)
    protected String charset = "UTF-8";


    @OperationMethod
    public Blob run(LogEntryList docs) throws Exception {
        String content = MyRenderingService.getInstance().render(type, template, ctx);
        StringBlob blob = new StringBlob(content);
        blob.setFilename(name);
        blob.setMimeType(mimeType);
        blob.setEncoding(charset);
        return blob;
    }
}
